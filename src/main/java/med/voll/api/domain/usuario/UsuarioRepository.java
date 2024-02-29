package med.voll.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;



public interface UsuarioRepository extends JpaRepository<Usuario, Long>{ //os parametros passados são a entidade da tabela e o tipo do atributo principal (tipo do id)
    
    UserDetails findByLogin(String login); /*método que vai consultar o usuário na tabela do db */
}
/*é preciso criar uma classe padrão que o spring pede para criar, pois ela acessar o banco, fazer consulta */
/*consiga gerar as queries SQL de maneira correta. devemos utilizar nos nomes dos métodos, como o findBy e o existsBy, para indicar ao Spring Data como ele deve montar a consulta que desejamos. Esse recurso é bastante flexível,*/