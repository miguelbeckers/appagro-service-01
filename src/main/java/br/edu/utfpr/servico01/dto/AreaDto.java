package br.edu.utfpr.servico01.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import br.edu.utfpr.servico01.model.AreaType;
import lombok.Data;

@Data
public class AreaDto {
  @NotEmpty(message = "nome inválido")
  private String name;
  private AreaType areaType;
  private List<String> coordinates;
}