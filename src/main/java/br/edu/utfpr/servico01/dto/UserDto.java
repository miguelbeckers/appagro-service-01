package br.edu.utfpr.servico01.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.edu.utfpr.servico01.model.UserType;
import lombok.Data;

@Data
public class UserDto {
  @NotEmpty
  private String nome;
  @NotEmpty
  private String username;
  @Email
  private String email;
  @NotEmpty
  private String password;
  @NotEmpty
  private UserType userType;
}
