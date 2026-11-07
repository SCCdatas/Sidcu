package com.cesde.sidcudb.dto.request;

import jakarta.validation.constraints.Size;

public record UbicacionRequestDTO(

        @Size(max = 150, message = "La dirección no puede superar 150 caracteres")
        String direccion,

        @Size(max = 20, message = "El código postal no puede superar 20 caracteres")
        String codigoPostal
) {
}
