package com.cesde.sidcudb.dto.response;

public record EstudianteResponseDTO(
        Long id,
        String documento,
        String nombre,
        String ciudad
) {
}
