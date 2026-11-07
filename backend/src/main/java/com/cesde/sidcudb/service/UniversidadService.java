package com.cesde.sidcudb.service;

import com.cesde.sidcudb.dto.request.UniversidadRequestDTO;
import com.cesde.sidcudb.dto.response.UniversidadResponseDTO;
import com.cesde.sidcudb.exception.RecursoNoEncontradoException;
import com.cesde.sidcudb.exception.ReglaDeNegocioException;
import com.cesde.sidcudb.mapper.UniversidadMapper;
import com.cesde.sidcudb.model.entity.Universidad;
import com.cesde.sidcudb.repository.InteresUniversidadRepository;
import com.cesde.sidcudb.repository.UniversidadCarreraRepository;
import com.cesde.sidcudb.repository.UniversidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UniversidadService {

    private final UniversidadRepository universidadRepository;
    private final UniversidadCarreraRepository universidadCarreraRepository;
    private final InteresUniversidadRepository interesUniversidadRepository;

    public UniversidadService(UniversidadRepository universidadRepository,
                              UniversidadCarreraRepository universidadCarreraRepository,
                              InteresUniversidadRepository interesUniversidadRepository) {
        this.universidadRepository = universidadRepository;
        this.universidadCarreraRepository = universidadCarreraRepository;
        this.interesUniversidadRepository = interesUniversidadRepository;
    }

    /** Lista universidades, con filtros opcionales por ciudad y/o texto contenido en el nombre. */
    public List<UniversidadResponseDTO> listar(String ciudad, String nombre) {
        List<Universidad> universidades;
        if (ciudad != null && !ciudad.isBlank()) {
            universidades = universidadRepository.findByCiudadIgnoreCase(ciudad.trim());
            if (nombre != null && !nombre.isBlank()) {
                String texto = nombre.trim().toLowerCase();
                universidades = universidades.stream()
                        .filter(u -> u.getNombre().toLowerCase().contains(texto))
                        .toList();
            }
        } else if (nombre != null && !nombre.isBlank()) {
            universidades = universidadRepository.buscarPorNombre(nombre.trim());
        } else {
            universidades = universidadRepository.findAll();
        }
        return universidades.stream().map(UniversidadMapper::toResponse).toList();
    }

    public UniversidadResponseDTO buscarPorId(Long id) {
        return UniversidadMapper.toResponse(obtener(id));
    }

    @Transactional
    public UniversidadResponseDTO crear(UniversidadRequestDTO dto) {
        // Regla de negocio: no pueden existir dos universidades con el mismo nombre.
        universidadRepository.findByNombre(dto.nombre().trim()).ifPresent(existente -> {
            throw new ReglaDeNegocioException("Ya existe una universidad con el nombre '" + existente.getNombre() + "'");
        });
        return UniversidadMapper.toResponse(universidadRepository.save(UniversidadMapper.toEntity(dto)));
    }

    @Transactional
    public UniversidadResponseDTO actualizar(Long id, UniversidadRequestDTO dto) {
        Universidad universidad = obtener(id);

        universidadRepository.findByNombre(dto.nombre().trim())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new ReglaDeNegocioException("El nombre ya pertenece a otra universidad");
                });

        UniversidadMapper.aplicar(universidad, dto);
        return UniversidadMapper.toResponse(universidadRepository.save(universidad));
    }

    @Transactional
    public void eliminar(Long id) {
        Universidad universidad = obtener(id);

        // Regla de negocio: no se elimina una universidad con oferta académica o con estudiantes interesados.
        if (universidadCarreraRepository.contarPorUniversidad(id) > 0) {
            throw new ReglaDeNegocioException("No se puede eliminar la universidad: tiene carreras ofertadas");
        }
        if (interesUniversidadRepository.contarPorUniversidad(id) > 0) {
            throw new ReglaDeNegocioException("No se puede eliminar la universidad: hay estudiantes interesados en ella");
        }
        universidadRepository.delete(universidad);
    }

    private Universidad obtener(Long id) {
        return universidadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Universidad no encontrada con id " + id));
    }
}
