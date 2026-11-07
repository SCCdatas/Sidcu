package com.cesde.sidcudb.dto.response;

public record InteresResponseDTO(
        Long estudianteId,
        String nombreEstudiante,
        Long carreraId,
        String nombreCarrera
) {
}
