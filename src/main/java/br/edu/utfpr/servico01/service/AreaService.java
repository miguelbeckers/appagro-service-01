package br.edu.utfpr.servico01.service;

import br.edu.utfpr.servico01.model.Area;
import br.edu.utfpr.servico01.model.User;
import br.edu.utfpr.servico01.repository.AreaRepository;
import br.edu.utfpr.servico01.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AreaService {
  @Autowired
  private AreaRepository areaRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Area> findAll(){
    return areaRepository.findAll();
  }

  public Optional<Area> findById(UUID id){
    return areaRepository.findById(id);
  }

  public List<Area> findByUser(UUID id){
    User user = userRepository.getById(id);
    return user.getAreas();
  }

  public Area create(Area area, User user){
    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    area.setCreatedAt(now);
    area.setUpdatedAt(now);
    Area created = areaRepository.save(area);

    user.insertArea(area);
    userRepository.save(user);
    return created;
  }

  public Area update(Area area){  
    LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
    area.setUpdatedAt(now);
    return areaRepository.save(area);
  }

  public void delete(Area area, User user){
    user.removeArea(area);
    userRepository.save(user);
    areaRepository.delete(area);
  }
}
