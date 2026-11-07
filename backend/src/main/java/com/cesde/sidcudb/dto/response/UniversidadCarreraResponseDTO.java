package com.cesde.sidcudb.dto.response;

public record UniversidadCarreraResponseDTO(
        Long universidadId,
        String nombreUniversidad,
        Long carreraId,
        String nombreCarrera
) {
}
