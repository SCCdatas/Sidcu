package com.cesde.sidcudb.dto.request;

import jakarta.validation.constraints.Size;

/** Datos editables del perfil (el estudiante dueño del perfil no puede cambiarse). */
public record PerfilEstudianteActualizacionRequestDTO(

        @Size(max = 255, message = "La biografía no puede superar 255 caracteres")
        String biografia,

        @Size(max = 100, message = "El sitio web no puede superar 100 caracteres")
        String sitioWeb
) {
}
