package com.cesde.sidcudb.dto.request;

import jakarta.validation.constraints.NotNull;

/** Registra el interés de un estudiante en una universidad. */
public record InteresUniversidadRequestDTO(

        @NotNull(message = "El estudiante es obligatorio")
        Long estudianteId,

        @NotNull(message = "La universidad es obligatoria")
        Long universidadId
) {
}
