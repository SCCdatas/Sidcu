package com.cesde.sidcudb.dto.request;

import jakarta.validation.constraints.NotNull;

/** Registra que una universidad ofrece una carrera. */
public record UniversidadCarreraRequestDTO(

        @NotNull(message = "La universidad es obligatoria")
        Long universidadId,

        @NotNull(message = "La carrera es obligatoria")
        Long carreraId
) {
}
