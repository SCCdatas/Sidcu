package com.cesde.sidcudb.repository;

import com.cesde.sidcudb.model.entity.Carrera;
import com.cesde.sidcudb.model.enums.NivelAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    Optional<Carrera> findByNombre(String nombre);

    List<Carrera> findByAreaIgnoreCase(String area);

    @Query("SELECT c FROM Carrera c WHERE c.nivel = :nivel")
    List<Carrera> buscarPorNivel(@Param("nivel") NivelAcademico nivel);

    @Query("SELECT COUNT(c) FROM Carrera c JOIN c.habilidades h WHERE h.id = :habilidadId")
    long contarPorHabilidad(@Param("habilidadId") Long habilidadId);
}
