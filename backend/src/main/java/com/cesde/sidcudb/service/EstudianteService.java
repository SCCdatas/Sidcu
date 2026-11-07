package com.cesde.sidcudb.service;

import com.cesde.sidcudb.dto.request.EstudianteRequestDTO;
import com.cesde.sidcudb.dto.response.EstudianteResponseDTO;
import com.cesde.sidcudb.dto.response.UniversidadResponseDTO;
import com.cesde.sidcudb.exception.RecursoNoEncontradoException;
import com.cesde.sidcudb.exception.ReglaDeNegocioException;
import com.cesde.sidcudb.mapper.EstudianteMapper;
import com.cesde.sidcudb.mapper.UniversidadMapper;
import com.cesde.sidcudb.model.entity.Estudiante;
import com.cesde.sidcudb.model.entity.Universidad;
import com.cesde.sidcudb.repository.EstudianteRepository;
import com.cesde.sidcudb.repository.UniversidadCarreraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final UniversidadCarreraRepository universidadCarreraRepository;

    public EstudianteService(EstudianteRepository estudianteRepository,
                             UniversidadCarreraRepository universidadCarreraRepository) {
        this.estudianteRepository = estudianteRepository;
        this.universidadCarreraRepository = universidadCarreraRepository;
    }

    /** Lista estudiantes, con filtros opcionales por documento y/o ciudad. */
    public List<EstudianteResponseDTO> listar(String documento, String ciudad) {
        List<Estudiante> estudiantes;
        if (documento != null && !documento.isBlank()) {
            estudiantes = estudianteRepository.findByDocumento(documento.trim()).stream().toList();
            if (ciudad != null && !ciudad.isBlank()) {
                estudiantes = estudiantes.stream()
                        .filter(e -> e.getCiudad().equalsIgnoreCase(ciudad.trim()))
                        .toList();
            }
        } else if (ciudad != null && !ciudad.isBlank()) {
            estudiantes = estudianteRepository.findByCiudadIgnoreCase(ciudad.trim());
        } else {
            estudiantes = estudianteRepository.findAll();
        }
        return estudiantes.stream().map(EstudianteMapper::toResponse).toList();
    }

    public EstudianteResponseDTO buscarPorId(Long id) {
        return EstudianteMapper.toResponse(obtener(id));
    }

    @Transactional
    public EstudianteResponseDTO crear(EstudianteRequestDTO dto) {
        // Regla de negocio: el documento identifica de forma única a cada estudiante.
        if (estudianteRepository.existsByDocumento(dto.documento().trim())) {
            throw new ReglaDeNegocioException("Ya existe un estudiante con el documento " + dto.documento().trim());
        }
        return EstudianteMapper.toResponse(estudianteRepository.save(EstudianteMapper.toEntity(dto)));
    }

    @Transactional
    public EstudianteResponseDTO actualizar(Long id, EstudianteRequestDTO dto) {
        Estudiante estudiante = obtener(id);

        estudianteRepository.findByDocumento(dto.documento().trim())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new ReglaDeNegocioException("El documento ya pertenece a otro estudiante");
                });

        EstudianteMapper.aplicar(estudiante, dto);
        return EstudianteMapper.toResponse(estudianteRepository.save(estudiante));
    }

    @Transactional
    public void eliminar(Long id) {
        // Sus intereses y su perfil se eliminan en cascada (ver entidad Estudiante).
        estudianteRepository.delete(obtener(id));
    }

    /**
     * Regla de negocio central del dominio: recomienda las universidades que ofrecen al menos una de las
     * carreras de interés del estudiante, mostrando primero las de su propia ciudad.
     */
    public List<UniversidadResponseDTO> recomendarUniversidades(Long estudianteId) {
        Estudiante estudiante = obtener(estudianteId);
        List<Universidad> candidatas =
                universidadCarreraRepository.buscarUniversidadesPorInteresesDeEstudiante(estudianteId);

        return candidatas.stream()
                .sorted(Comparator
                        .comparing((Universidad u) -> !u.getCiudad().equalsIgnoreCase(estudiante.getCiudad()))
                        .thenComparing(Universidad::getNombre))
                .map(UniversidadMapper::toResponse)
                .toList();
    }

    private Estudiante obtener(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con id " + id));
    }
}
