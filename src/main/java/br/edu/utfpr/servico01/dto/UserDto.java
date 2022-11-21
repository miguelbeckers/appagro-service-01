package br.edu.utfpr.servico01.dto;

import lombok.Data;

@Data
public class UserDto {
  private String nome;
  private String username;
  private String email;
  private String password;
  private String type;
}
