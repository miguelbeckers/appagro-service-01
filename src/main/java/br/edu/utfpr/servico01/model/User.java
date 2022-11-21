package br.edu.utfpr.servico01.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;
  private String nome;
  private String username;
  private String email;
  private String password;
  private UserType userType;
  private LocalDateTime createdAt;
  private LocalDateTime UpdatedAt;
  
  @OneToMany(cascade = CascadeType.ALL)
  private List<Area> areas;

  public User() {
    this.id = UUID.randomUUID();
  }

  public void insertArea(Area area){
    areas.add(area);
  }

  public void removeArea(Area area){
    areas.remove(area);
  }
}
