package com.cesde.sidcudb.dto.request;

import jakarta.validation.constraints.NotNull;

/** Registra el interés de un estudiante en una carrera. */
public record InteresRequestDTO(

        @NotNull(message = "El estudiante es obligatorio")
        Long estudianteId,

        @NotNull(message = "La carrera es obligatoria")
        Long carreraId
) {
}
