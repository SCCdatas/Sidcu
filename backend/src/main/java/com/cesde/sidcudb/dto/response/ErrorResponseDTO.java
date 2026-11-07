package com.cesde.sidcudb.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/** Estructura uniforme de error devuelta por la API. */
public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensaje,
        String ruta,
        List<String> detalles
) {
}
