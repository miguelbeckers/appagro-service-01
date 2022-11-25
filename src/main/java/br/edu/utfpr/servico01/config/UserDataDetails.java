package br.edu.utfpr.servico01.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.utfpr.servico01.model.User;

public class UserDataDetails implements UserDetails{

  private final Optional<User> optional;

  public UserDataDetails(Optional<User> optional){
    this.optional = optional;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public String getPassword() {
    return optional.orElse(new User()).getPassword();
  }

  @Override
  public String getUsername() {
    return optional.orElse(new User()).getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
