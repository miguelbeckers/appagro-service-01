package br.edu.utfpr.servico01.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.utfpr.servico01.dto.UserDto;
import br.edu.utfpr.servico01.model.User;
import br.edu.utfpr.servico01.service.UserService;

@Repository
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok().body("hello");
  }

  @GetMapping
  public ResponseEntity<Object> findAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> findById(@PathVariable String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("id inválido");
    }

    Optional<User> optional = userService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("usuário não encontrado");
    }

    return ResponseEntity.ok(optional.get());
  }

  @PostMapping
  public ResponseEntity<User> save(@RequestBody UserDto userDto) {
    User user = new User();
    BeanUtils.copyProperties(userDto, user);
    return ResponseEntity.ok().body(userService.save(user));
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> update(@PathVariable String id, @RequestBody UserDto userDto) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("id inválido");
    }

    Optional<User> optional = userService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("usuário não encontrado");
    }

    User user = optional.get();
    BeanUtils.copyProperties(userDto, user);
    return ResponseEntity.ok().body(userService.update(user));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> delete(@PathVariable String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("id inválido");
    }

    Optional<User> optional = userService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body("usuário não encontrado");
    }

    userService.deleteById(uuid);
    return ResponseEntity.ok().build();
  }
}