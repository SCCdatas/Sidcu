package com.cesde.sidcudb.repository;

import com.cesde.sidcudb.model.entity.Habilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabilidadRepository extends JpaRepository<Habilidad, Long> {

    Optional<Habilidad> findByNombre(String nombre);
}
