/*Não é recomendado devolver e receber entidades JPA no controller */

package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;


    //HTTP 201 - Requisição processada e novo recurso criado  - regras do código. tem que devolver o cód 201, 
    //no retorno passar os parametros URI(endereço da API) e DTO 
    //tem que devolver no corpo os dados do registro que acabou e ser cadastrado e 
    //tem que  devolver o cabeçalho LOCATION - que mostra o endereço que o front consiga acessar o recurso que acabou de ser cadastrado
    //A classe do Spring que sabe esconder a rota da uri
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        //a variável serviu para utilizar os dados que foram passados depois, para que o buildAndExpand consiga pegar o id que foi inserido recente
       var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); //buildAndExpand vai pegar o último dado inserido utilizar o toUri() para criar para uri
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));

    }


    //Vai devolver o ok(), mas é necessário colocar o objeto dentro do retorno, para fazer isso, é necessário colocar na var.
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
      var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
      return ResponseEntity.ok(page);
    }

    //Como é para atualizar, seria legal devolver atualizado. ou seja, o DTO. sendo assim, melhor criar outro DTO
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    // MÉTODO 204 (noContent()) - DIZER QUE A REQUISIÇÃO FOI PROCESSADA E NÃO TEM CONTEÚDO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var medico = repository.getReferenceById(id); //carrega o médico do banco de dados


        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); //o Ok() simboliza que você tem que receber um conteúdo e tem que criar um DTO no parametro, dto de quem?
                                                                     //      da var medico que acabei de criar
    }
    

}
