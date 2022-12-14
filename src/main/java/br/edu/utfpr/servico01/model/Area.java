package br.edu.utfpr.servico01.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Area {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;
  private String name;
  private AreaType areaType;
  private LocalDateTime createdAt;
  private LocalDateTime UpdatedAt;
  
  @ElementCollection
  private List<String> coordinates;

  public Area() {
    this.id = UUID.randomUUID();
  }
}
