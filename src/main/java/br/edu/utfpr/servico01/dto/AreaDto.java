package br.edu.utfpr.servico01.dto;

import java.util.List;

import lombok.Data;

@Data
public class AreaDto {
  private String name;
  private List<String> coordinates;
}