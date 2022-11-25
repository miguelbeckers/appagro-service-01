package br.edu.utfpr.servico01.repository;

import br.edu.utfpr.servico01.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  public Optional<User> findByUsername(String username);
}
