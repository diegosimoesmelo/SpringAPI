package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//para dizer a ele que deve ser carregada como parte do spring
@Service //o spring security agora precisa saber que ESSA é a classe de detectar na segurança, implementando uma interface do próprio security/*implements UserDetailsService */
public class AutenticacaoService implements UserDetailsService{


    @Autowired
    private UsuarioRepository repository;
    /*toda vez que implementa interface somos obrigados a implementar os métodos dela esse aqui: */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username); // aqui precisa acesar o db, então injeta o repository @autowired e vai precisar do método do repository para achar
        
    }
    
}
