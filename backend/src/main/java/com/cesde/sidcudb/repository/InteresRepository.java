package com.cesde.sidcudb.repository;

import com.cesde.sidcudb.model.embeddable.InteresId;
import com.cesde.sidcudb.model.entity.Interes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteresRepository extends JpaRepository<Interes, InteresId> {

    @Query("SELECT i FROM Interes i WHERE i.id.estudianteId = :estudianteId")
    List<Interes> buscarPorEstudiante(@Param("estudianteId") Long estudianteId);

    @Query("SELECT i FROM Interes i WHERE i.id.carreraId = :carreraId")
    List<Interes> buscarPorCarrera(@Param("carreraId") Long carreraId);

    @Query("SELECT COUNT(i) FROM Interes i WHERE i.id.estudianteId = :estudianteId")
    long contarPorEstudiante(@Param("estudianteId") Long estudianteId);

    @Query("SELECT COUNT(i) FROM Interes i WHERE i.id.carreraId = :carreraId")
    long contarPorCarrera(@Param("carreraId") Long carreraId);
}
