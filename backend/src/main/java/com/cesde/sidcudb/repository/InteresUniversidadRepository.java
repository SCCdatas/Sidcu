package com.cesde.sidcudb.repository;

import com.cesde.sidcudb.model.embeddable.InteresUniversidadId;
import com.cesde.sidcudb.model.entity.InteresUniversidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteresUniversidadRepository extends JpaRepository<InteresUniversidad, InteresUniversidadId> {

    @Query("SELECT r FROM InteresUniversidad r WHERE r.id.estudianteId = :estudianteId")
    List<InteresUniversidad> buscarPorEstudiante(@Param("estudianteId") Long estudianteId);

    @Query("SELECT r FROM InteresUniversidad r WHERE r.id.universidadId = :universidadId")
    List<InteresUniversidad> buscarPorUniversidad(@Param("universidadId") Long universidadId);

    @Query("SELECT COUNT(r) FROM InteresUniversidad r WHERE r.id.universidadId = :universidadId")
    long contarPorUniversidad(@Param("universidadId") Long universidadId);
}
