package com.cesde.sidcudb.dto.response;

import com.cesde.sidcudb.model.enums.NivelAcademico;

import java.util.List;

public record CarreraResponseDTO(
        Long id,
        String nombre,
        String area,
        NivelAcademico nivel,
        List<HabilidadResponseDTO> habilidades
) {
}
