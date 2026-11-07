package com.cesde.sidcudb.mapper;

import com.cesde.sidcudb.dto.response.CarreraResponseDTO;
import com.cesde.sidcudb.dto.response.HabilidadResponseDTO;
import com.cesde.sidcudb.model.entity.Carrera;

import java.util.Comparator;
import java.util.List;

public final class CarreraMapper {

    private CarreraMapper() {
    }

    public static CarreraResponseDTO toResponse(Carrera carrera) {
        List<HabilidadResponseDTO> habilidades = carrera.getHabilidades().stream()
                .map(HabilidadMapper::toResponse)
                .sorted(Comparator.comparing(HabilidadResponseDTO::nombre))
                .toList();
        return new CarreraResponseDTO(
                carrera.getId(),
                carrera.getNombre(),
                carrera.getArea(),
                carrera.getNivel(),
                habilidades
        );
    }
}
