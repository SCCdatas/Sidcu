package com.cesde.sidcudb.mapper;

import com.cesde.sidcudb.dto.request.EstudianteRequestDTO;
import com.cesde.sidcudb.dto.response.EstudianteResponseDTO;
import com.cesde.sidcudb.model.entity.Estudiante;

public final class EstudianteMapper {

    private EstudianteMapper() {
    }

    public static Estudiante toEntity(EstudianteRequestDTO dto) {
        Estudiante estudiante = new Estudiante();
        aplicar(estudiante, dto);
        return estudiante;
    }

    public static void aplicar(Estudiante estudiante, EstudianteRequestDTO dto) {
        estudiante.setDocumento(dto.documento().trim());
        estudiante.setNombre(dto.nombre().trim());
        estudiante.setCiudad(dto.ciudad().trim());
    }

    public static EstudianteResponseDTO toResponse(Estudiante estudiante) {
        return new EstudianteResponseDTO(
                estudiante.getId(),
                estudiante.getDocumento(),
                estudiante.getNombre(),
                estudiante.getCiudad()
        );
    }
}
