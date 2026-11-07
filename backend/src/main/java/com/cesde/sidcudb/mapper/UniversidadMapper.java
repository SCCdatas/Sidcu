package com.cesde.sidcudb.mapper;

import com.cesde.sidcudb.dto.request.UbicacionRequestDTO;
import com.cesde.sidcudb.dto.request.UniversidadRequestDTO;
import com.cesde.sidcudb.dto.response.UbicacionResponseDTO;
import com.cesde.sidcudb.dto.response.UniversidadResponseDTO;
import com.cesde.sidcudb.model.embeddable.Ubicacion;
import com.cesde.sidcudb.model.entity.Universidad;

public final class UniversidadMapper {

    private UniversidadMapper() {
    }

    public static Universidad toEntity(UniversidadRequestDTO dto) {
        Universidad universidad = new Universidad();
        aplicar(universidad, dto);
        return universidad;
    }

    public static void aplicar(Universidad universidad, UniversidadRequestDTO dto) {
        universidad.setNombre(dto.nombre().trim());
        universidad.setCiudad(dto.ciudad().trim());
        universidad.setTipo(dto.tipo().trim());
        universidad.setUbicacion(toUbicacion(dto.ubicacion()));
    }

    public static UniversidadResponseDTO toResponse(Universidad universidad) {
        Ubicacion ubicacion = universidad.getUbicacion();
        UbicacionResponseDTO ubicacionDto = ubicacion == null
                ? null
                : new UbicacionResponseDTO(ubicacion.getDireccion(), ubicacion.getCodigoPostal());
        return new UniversidadResponseDTO(
                universidad.getId(),
                universidad.getNombre(),
                universidad.getCiudad(),
                universidad.getTipo(),
                ubicacionDto
        );
    }

    private static Ubicacion toUbicacion(UbicacionRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setDireccion(dto.direccion());
        ubicacion.setCodigoPostal(dto.codigoPostal());
        return ubicacion;
    }
}
