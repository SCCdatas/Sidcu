package com.cesde.sidcudb.dto.response;

public record UniversidadResponseDTO(
        Long id,
        String nombre,
        String ciudad,
        String tipo,
        UbicacionResponseDTO ubicacion
) {
}
