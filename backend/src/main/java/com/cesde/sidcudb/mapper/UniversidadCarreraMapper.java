package com.cesde.sidcudb.mapper;

import com.cesde.sidcudb.dto.response.UniversidadCarreraResponseDTO;
import com.cesde.sidcudb.model.entity.UniversidadCarrera;

public final class UniversidadCarreraMapper {

    private UniversidadCarreraMapper() {
    }

    public static UniversidadCarreraResponseDTO toResponse(UniversidadCarrera relacion) {
        return new UniversidadCarreraResponseDTO(
                relacion.getUniversidad().getId(),
                relacion.getUniversidad().getNombre(),
                relacion.getCarrera().getId(),
                relacion.getCarrera().getNombre()
        );
    }
}
