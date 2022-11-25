package br.edu.utfpr.servico01.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.edu.utfpr.servico01.model.User;
import br.edu.utfpr.servico01.repository.UserRepository;

@Component
public class UserDetailServiceImp implements UserDetailsService{

  private final UserRepository userRepository;

  public UserDetailServiceImp(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> optional = userRepository.findByUsername(username);

    if(optional.isEmpty()){
      throw new UsernameNotFoundException("Usuário " + username + " não encontrado");
    }
    return new UserDataDetails(optional);
  }
  
}
