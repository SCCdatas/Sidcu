package com.cesde.sidcudb.repository;

import com.cesde.sidcudb.model.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByDocumento(String documento);

    boolean existsByDocumento(String documento);

    List<Estudiante> findByCiudadIgnoreCase(String ciudad);
}
