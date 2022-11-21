package br.edu.utfpr.servico01.repository;

import br.edu.utfpr.servico01.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AreaRepository extends JpaRepository<Area, UUID>{
  
}
