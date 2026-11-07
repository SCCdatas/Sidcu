package com.cesde.sidcudb.dto.response;

public record InteresUniversidadResponseDTO(
        Long estudianteId,
        String nombreEstudiante,
        Long universidadId,
        String nombreUniversidad
) {
}
