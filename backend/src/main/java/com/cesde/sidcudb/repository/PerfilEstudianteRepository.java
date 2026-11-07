package com.cesde.sidcudb.repository;

import com.cesde.sidcudb.model.entity.PerfilEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilEstudianteRepository extends JpaRepository<PerfilEstudiante, Long> {

    @Query("SELECT p FROM PerfilEstudiante p WHERE p.estudiante.id = :estudianteId")
    Optional<PerfilEstudiante> buscarPorEstudiante(@Param("estudianteId") Long estudianteId);
}
