package br.edu.utfpr.servico01.service;

import br.edu.utfpr.servico01.model.User;
import br.edu.utfpr.servico01.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public List<User> findAll(){
    return userRepository.findAll();
  }

  public Optional<User> findById(UUID id){
    return userRepository.findById(id);
  }

  public User create(User user){
    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    user.setCreatedAt(now);
    return userRepository.save(user);
  }

  public User update(User user){  
    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    user.setUpdatedAt(now);
    return userRepository.save(user);
  }

  public void delete(User user){
    userRepository.delete(user);
  }
}
