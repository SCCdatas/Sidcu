package com.cesde.sidcudb.repository;

import com.cesde.sidcudb.model.embeddable.UniversidadCarreraId;
import com.cesde.sidcudb.model.entity.Universidad;
import com.cesde.sidcudb.model.entity.UniversidadCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UniversidadCarreraRepository extends JpaRepository<UniversidadCarrera, UniversidadCarreraId> {

    @Query("SELECT uc FROM UniversidadCarrera uc WHERE uc.id.universidadId = :universidadId")
    List<UniversidadCarrera> buscarPorUniversidad(@Param("universidadId") Long universidadId);

    @Query("SELECT uc FROM UniversidadCarrera uc WHERE uc.id.carreraId = :carreraId")
    List<UniversidadCarrera> buscarPorCarrera(@Param("carreraId") Long carreraId);

    @Query("SELECT COUNT(uc) FROM UniversidadCarrera uc WHERE uc.id.universidadId = :universidadId")
    long contarPorUniversidad(@Param("universidadId") Long universidadId);

    @Query("SELECT COUNT(uc) FROM UniversidadCarrera uc WHERE uc.id.carreraId = :carreraId")
    long contarPorCarrera(@Param("carreraId") Long carreraId);

    /**
     * Cuántas de las carreras de interés de un estudiante ofrece una universidad.
     */
    @Query("""
            SELECT COUNT(uc) FROM UniversidadCarrera uc
            WHERE uc.id.universidadId = :universidadId
              AND uc.id.carreraId IN (
                  SELECT i.id.carreraId FROM Interes i WHERE i.id.estudianteId = :estudianteId)
            """)
    long contarCoincidenciasConIntereses(@Param("universidadId") Long universidadId,
                                         @Param("estudianteId") Long estudianteId);

    /**
     * Universidades que ofrecen al menos una de las carreras de interés del estudiante.
     */
    @Query("""
            SELECT DISTINCT uc.universidad FROM UniversidadCarrera uc
            WHERE uc.id.carreraId IN (
                SELECT i.id.carreraId FROM Interes i WHERE i.id.estudianteId = :estudianteId)
            """)
    List<Universidad> buscarUniversidadesPorInteresesDeEstudiante(@Param("estudianteId") Long estudianteId);
}
