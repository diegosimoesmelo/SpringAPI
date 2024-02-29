package med.voll.api.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.medico.Especialidade;

//pro spring carregar a classe, precisa das anotações
//para indicar que a classe se trata de tratadora de erros usamos o:
// faz-se essa classe aqui para evitar encher o controller de try/catch
@RestControllerAdvice
public class TratadorDeErros {

    //Vai tratar qual erro? avisa com o:
    @ExceptionHandler(EntityNotFoundException.class)

    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    //quando passa argumentos inválidos para a api. é importante enviar o que foi inválido
    //para conseguir receber o erro como parametro, use como parametro
    //esse próprio method tem seus métodos para trazer os erros, é só colocar na variável
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors(); //devolve uma lista dos erros. em cada um dos campos
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); //tá pegando a var e transformando numa lista de erros e mapei PARA O DTO,
        // por isso o ::new
        //tendo  o new, precisa do objeto no DTO
    }
 //criar um dto aqui para não trazer a lista enorme de erros, não vai precisar usar em outro local
    private record DadosErroValidacao(String campo, String mensagem) {
       
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }

    }
   
}


/* o Security sempre vai dar o token da senha para acessar, o usuário é o user */


/*
Pode personalizar as mensagens diretamente nos campos
@NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato do email é inválido")
    String email,


Outra maneira é isolar as mensagens em um arquivo de propriedades, que deve possuir o nome ValidationMessages.properties e ser criado no diretório src/main/resources:
nome.obrigatorio=Nome é obrigatório
email.obrigatorio=Email é obrigatório
email.invalido=Formato do email é inválido
telefone.obrigatorio=Telefone é obrigatório
crm.obrigatorio=CRM é obrigatório
crm.invalido=Formato do CRM é inválido
especialidade.obrigatoria=Especialidade é obrigatória
endereco.obrigatorio=Dados do endereço são obrigatórios
*/ 