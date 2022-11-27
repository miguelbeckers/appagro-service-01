package br.edu.utfpr.servico01.dto;

import javax.validation.constraints.NotEmpty;

import br.edu.utfpr.servico01.model.UserType;
import lombok.Data;

@Data
public class UserDto {
  @NotEmpty(message = "nome inválido")
  private String nome;
  @NotEmpty(message = "username inválido")
  private String username;
  @NotEmpty(message = "senha inválida")
  private String password;
  private UserType userType;
}