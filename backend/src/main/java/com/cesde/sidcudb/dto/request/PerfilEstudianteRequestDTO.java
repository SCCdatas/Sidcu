package com.cesde.sidcudb.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Datos para crear el perfil de un estudiante existente. */
public record PerfilEstudianteRequestDTO(

        @NotNull(message = "Debe indicar el estudiante al que pertenece el perfil")
        Long estudianteId,

        @Size(max = 255, message = "La biografía no puede superar 255 caracteres")
        String biografia,

        @Size(max = 100, message = "El sitio web no puede superar 100 caracteres")
        String sitioWeb
) {
}
