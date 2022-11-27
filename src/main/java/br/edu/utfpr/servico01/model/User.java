package br.edu.utfpr.servico01.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class User {

  @Id
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;
  @Column(unique = true)
  private String username;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String nome;
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
