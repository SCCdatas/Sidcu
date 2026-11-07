package com.cesde.sidcudb.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UniversidadRequestDTO(

        @NotBlank(message = "El nombre de la universidad es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
        String nombre,

        @NotBlank(message = "La ciudad es obligatoria")
        @Size(max = 50, message = "La ciudad no puede superar 50 caracteres")
        String ciudad,

        @NotBlank(message = "El tipo de universidad es obligatorio (p. ej. PUBLICA, PRIVADA)")
        @Size(max = 50, message = "El tipo no puede superar 50 caracteres")
        String tipo,

        @Valid
        UbicacionRequestDTO ubicacion
) {
}
