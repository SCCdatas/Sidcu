package com.cesde.sidcudb.mapper;

import com.cesde.sidcudb.dto.response.InteresResponseDTO;
import com.cesde.sidcudb.model.entity.Interes;

public final class InteresMapper {

    private InteresMapper() {
    }

    public static InteresResponseDTO toResponse(Interes interes) {
        return new InteresResponseDTO(
                interes.getEstudiante().getId(),
                interes.getEstudiante().getNombre(),
                interes.getCarrera().getId(),
                interes.getCarrera().getNombre()
        );
    }
}
