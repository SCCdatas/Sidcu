package com.cesde.sidcudb.mapper;

import com.cesde.sidcudb.dto.response.PerfilEstudianteResponseDTO;
import com.cesde.sidcudb.model.entity.PerfilEstudiante;

public final class PerfilEstudianteMapper {

    private PerfilEstudianteMapper() {
    }

    public static PerfilEstudianteResponseDTO toResponse(PerfilEstudiante perfil) {
        return new PerfilEstudianteResponseDTO(
                perfil.getId(),
                perfil.getEstudiante().getId(),
                perfil.getEstudiante().getNombre(),
                perfil.getBiografia(),
                perfil.getSitioWeb()
        );
    }
}
