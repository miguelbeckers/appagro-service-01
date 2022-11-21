package br.edu.utfpr.servico01.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.utfpr.servico01.dto.AreaDto;
import br.edu.utfpr.servico01.model.Area;
import br.edu.utfpr.servico01.model.User;
import br.edu.utfpr.servico01.service.AreaService;
import br.edu.utfpr.servico01.service.UserService;

@Controller
@RequestMapping("/area")
public class AreaController {

  @Autowired
  private AreaService areaService;

  @Autowired
  private UserService userService;

  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok().body("hello");
  }

  @GetMapping
  public ResponseEntity<Object> getAll() {
    return ResponseEntity.ok(areaService.findAll());
  }

  @GetMapping("{id}")
  public ResponseEntity<Object> getById(@PathVariable String id) {
    UUID uuid;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<Area> optional = areaService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(optional.get());
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<Object> getByUser(@PathVariable String id) {
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

    return ResponseEntity.ok(areaService.findByUser(uuid));
  }

  @PostMapping("{id}")
  public ResponseEntity<Object> create(@Valid @RequestBody AreaDto areaDto, @PathVariable String id) {
    UUID uuid = null;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<User> optional = userService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Area area = new Area();
    BeanUtils.copyProperties(areaDto, area);
    return ResponseEntity.ok().body(areaService.create(area, optional.get()));
  }

  @PutMapping("{id}")
  public ResponseEntity<Object> update(@Valid @RequestBody AreaDto areaDto, @PathVariable String id) {
    UUID uuid = null;
    try {
      uuid = UUID.fromString(id);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<Area> optional = areaService.findById(uuid);
    if (optional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Area area = optional.get();
    BeanUtils.copyProperties(areaDto, area);
    return ResponseEntity.ok().body(areaService.update(area));
  }

  @DeleteMapping("{areaId}/user/{userId}")
  public ResponseEntity<Object> delete(@PathVariable String areaId, @PathVariable String userId) {
    UUID areaUuid;
    try {
      areaUuid = UUID.fromString(areaId);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<Area> areaOptional = areaService.findById(areaUuid);
    if (areaOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    UUID userUuid;
    try {
      userUuid = UUID.fromString(userId);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<User> userOptional = userService.findById(userUuid);
    if (userOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    areaService.delete(areaOptional.get(), userOptional.get());
    return ResponseEntity.ok().build();
  }
}