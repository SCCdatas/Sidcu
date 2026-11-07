package com.cesde.sidcudb.dto.request;

import com.cesde.sidcudb.model.enums.NivelAcademico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CarreraRequestDTO(

        @NotBlank(message = "El nombre de la carrera es obligatorio")
        @Size(max = 255, message = "El nombre no puede superar 255 caracteres")
        String nombre,

        @NotBlank(message = "El área de conocimiento es obligatoria")
        @Size(max = 255, message = "El área no puede superar 255 caracteres")
        String area,

        @NotNull(message = "El nivel académico es obligatorio")
        NivelAcademico nivel,

        /** Habilidades asociadas a la carrera (referencias por identificador). Opcional. */
        Set<Long> habilidadIds
) {
}
