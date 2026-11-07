package com.cesde.sidcudb.service;

import com.cesde.sidcudb.dto.request.HabilidadRequestDTO;
import com.cesde.sidcudb.dto.response.HabilidadResponseDTO;
import com.cesde.sidcudb.exception.RecursoNoEncontradoException;
import com.cesde.sidcudb.exception.ReglaDeNegocioException;
import com.cesde.sidcudb.mapper.HabilidadMapper;
import com.cesde.sidcudb.model.entity.Habilidad;
import com.cesde.sidcudb.repository.CarreraRepository;
import com.cesde.sidcudb.repository.HabilidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HabilidadService {

    private final HabilidadRepository habilidadRepository;
    private final CarreraRepository carreraRepository;

    public HabilidadService(HabilidadRepository habilidadRepository, CarreraRepository carreraRepository) {
        this.habilidadRepository = habilidadRepository;
        this.carreraRepository = carreraRepository;
    }

    public List<HabilidadResponseDTO> listar() {
        return habilidadRepository.findAll().stream().map(HabilidadMapper::toResponse).toList();
    }

    public HabilidadResponseDTO buscarPorId(Long id) {
        return HabilidadMapper.toResponse(obtener(id));
    }

    @Transactional
    public HabilidadResponseDTO crear(HabilidadRequestDTO dto) {
        // Regla de negocio: el catálogo de habilidades no admite nombres repetidos.
        habilidadRepository.findByNombre(dto.nombre().trim()).ifPresent(existente -> {
            throw new ReglaDeNegocioException("Ya existe una habilidad con el nombre '" + existente.getNombre() + "'");
        });
        return HabilidadMapper.toResponse(habilidadRepository.save(HabilidadMapper.toEntity(dto)));
    }

    @Transactional
    public HabilidadResponseDTO actualizar(Long id, HabilidadRequestDTO dto) {
        Habilidad habilidad = obtener(id);
        habilidadRepository.findByNombre(dto.nombre().trim())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new ReglaDeNegocioException("El nombre ya pertenece a otra habilidad");
                });
        HabilidadMapper.aplicar(habilidad, dto);
        return HabilidadMapper.toResponse(habilidadRepository.save(habilidad));
    }

    @Transactional
    public void eliminar(Long id) {
        Habilidad habilidad = obtener(id);
        // Regla de negocio: no se elimina una habilidad que aún está asociada a carreras.
        if (carreraRepository.contarPorHabilidad(id) > 0) {
            throw new ReglaDeNegocioException("No se puede eliminar la habilidad: está asociada a una o más carreras");
        }
        habilidadRepository.delete(habilidad);
    }

    private Habilidad obtener(Long id) {
        return habilidadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Habilidad no encontrada con id " + id));
    }
}
