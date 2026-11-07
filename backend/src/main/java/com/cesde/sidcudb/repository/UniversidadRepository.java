package com.cesde.sidcudb.repository;

import com.cesde.sidcudb.model.entity.Universidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversidadRepository extends JpaRepository<Universidad, Long> {

    List<Universidad> findByCiudadIgnoreCase(String ciudad);

    Optional<Universidad> findByNombre(String nombre);

    @Query("SELECT u FROM Universidad u WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Universidad> buscarPorNombre(@Param("texto") String texto);
}
