package com.cesde.sidcudb.mapper;

import com.cesde.sidcudb.dto.request.HabilidadRequestDTO;
import com.cesde.sidcudb.dto.response.HabilidadResponseDTO;
import com.cesde.sidcudb.model.entity.Habilidad;

public final class HabilidadMapper {

    private HabilidadMapper() {
    }

    public static Habilidad toEntity(HabilidadRequestDTO dto) {
        Habilidad habilidad = new Habilidad();
        aplicar(habilidad, dto);
        return habilidad;
    }

    public static void aplicar(Habilidad habilidad, HabilidadRequestDTO dto) {
        habilidad.setNombre(dto.nombre().trim());
        habilidad.setDescripcion(dto.descripcion());
    }

    public static HabilidadResponseDTO toResponse(Habilidad habilidad) {
        return new HabilidadResponseDTO(habilidad.getId(), habilidad.getNombre(), habilidad.getDescripcion());
    }
}
