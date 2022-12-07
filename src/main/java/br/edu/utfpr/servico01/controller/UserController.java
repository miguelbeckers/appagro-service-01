package br.edu.utfpr.servico01.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.utfpr.servico01.dto.UserDto;
import br.edu.utfpr.servico01.model.User;
import br.edu.utfpr.servico01.service.UserService;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok().body("hello");
  }

  @GetMapping
  public ResponseEntity<Object> getAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getById(@PathVariable String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<User> optional = userService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(optional.get());
  }

  @GetMapping("/username/{username}")
  public ResponseEntity<Object> getByUsername(@PathVariable String username) {
    try {
      Optional<User> optional = userService.findByUsername(username);
      if (optional.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok(optional.get());
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  @GetMapping("/token")
  public ResponseEntity<Object> getByToken() {
    try {
      Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
      Optional<User> optional = userService.findByUsername(authentication.getName());
      if (optional.isEmpty()) {
        return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok(optional.get());
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  @GetMapping("/validate")
  public ResponseEntity<Boolean> validate(@RequestParam String username, @RequestParam String password) {

    Optional<User> optional = userService.findByUsername(username);
    if (optional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    User user = optional.get();
    boolean valid = passwordEncoder.matches(password, user.getPassword());

    HttpStatus status = valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
    return ResponseEntity.status(status).body(valid);
  }

  @PostMapping
  public ResponseEntity<User> create(@Valid @RequestBody UserDto userDto) {
    User user = new User();
    BeanUtils.copyProperties(userDto, user);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return ResponseEntity.ok().body(userService.create(user));
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> update(@Valid @RequestBody UserDto userDto, @PathVariable String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<User> optional = userService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    User user = optional.get();
    BeanUtils.copyProperties(userDto, user);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return ResponseEntity.ok().body(userService.update(user));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> delete(@PathVariable String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<User> optional = userService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    userService.delete(optional.get());
    return ResponseEntity.ok().build();
  }
}