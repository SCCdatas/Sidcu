package com.cesde.sidcudb.dto.response;

public record PerfilEstudianteResponseDTO(
        Long id,
        Long estudianteId,
        String nombreEstudiante,
        String biografia,
        String sitioWeb
) {
}
