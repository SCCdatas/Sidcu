package com.cesde.sidcudb.mapper;

import com.cesde.sidcudb.dto.response.InteresUniversidadResponseDTO;
import com.cesde.sidcudb.model.entity.InteresUniversidad;

public final class InteresUniversidadMapper {

    private InteresUniversidadMapper() {
    }

    public static InteresUniversidadResponseDTO toResponse(InteresUniversidad relacion) {
        return new InteresUniversidadResponseDTO(
                relacion.getEstudiante().getId(),
                relacion.getEstudiante().getNombre(),
                relacion.getUniversidad().getId(),
                relacion.getUniversidad().getNombre()
        );
    }
}
