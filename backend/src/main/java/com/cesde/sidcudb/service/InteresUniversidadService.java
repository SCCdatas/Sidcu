package com.cesde.sidcudb.service;

import com.cesde.sidcudb.dto.request.InteresUniversidadRequestDTO;
import com.cesde.sidcudb.dto.response.InteresUniversidadResponseDTO;
import com.cesde.sidcudb.exception.RecursoNoEncontradoException;
import com.cesde.sidcudb.exception.ReglaDeNegocioException;
import com.cesde.sidcudb.mapper.InteresUniversidadMapper;
import com.cesde.sidcudb.model.embeddable.InteresUniversidadId;
import com.cesde.sidcudb.model.entity.Estudiante;
import com.cesde.sidcudb.model.entity.InteresUniversidad;
import com.cesde.sidcudb.model.entity.Universidad;
import com.cesde.sidcudb.repository.EstudianteRepository;
import com.cesde.sidcudb.repository.InteresUniversidadRepository;
import com.cesde.sidcudb.repository.UniversidadCarreraRepository;
import com.cesde.sidcudb.repository.UniversidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class InteresUniversidadService {

    private final InteresUniversidadRepository interesUniversidadRepository;
    private final EstudianteRepository estudianteRepository;
    private final UniversidadRepository universidadRepository;
    private final UniversidadCarreraRepository universidadCarreraRepository;

    public InteresUniversidadService(InteresUniversidadRepository interesUniversidadRepository,
                                     EstudianteRepository estudianteRepository,
                                     UniversidadRepository universidadRepository,
                                     UniversidadCarreraRepository universidadCarreraRepository) {
        this.interesUniversidadRepository = interesUniversidadRepository;
        this.estudianteRepository = estudianteRepository;
        this.universidadRepository = universidadRepository;
        this.universidadCarreraRepository = universidadCarreraRepository;
    }

    /** Lista relaciones, con filtros opcionales por estudiante y/o universidad. */
    public List<InteresUniversidadResponseDTO> listar(Long estudianteId, Long universidadId) {
        List<InteresUniversidad> relaciones;
        if (estudianteId != null) {
            relaciones = interesUniversidadRepository.buscarPorEstudiante(estudianteId);
            if (universidadId != null) {
                relaciones = relaciones.stream()
                        .filter(r -> r.getId().getUniversidadId().equals(universidadId))
                        .toList();
            }
        } else if (universidadId != null) {
            relaciones = interesUniversidadRepository.buscarPorUniversidad(universidadId);
        } else {
            relaciones = interesUniversidadRepository.findAll();
        }
        return relaciones.stream().map(InteresUniversidadMapper::toResponse).toList();
    }

    public InteresUniversidadResponseDTO buscarPorId(Long estudianteId, Long universidadId) {
        return InteresUniversidadMapper.toResponse(obtener(new InteresUniversidadId(estudianteId, universidadId)));
    }

    @Transactional
    public InteresUniversidadResponseDTO crear(InteresUniversidadRequestDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.estudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Estudiante no encontrado con id " + dto.estudianteId()));
        Universidad universidad = universidadRepository.findById(dto.universidadId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Universidad no encontrada con id " + dto.universidadId()));

        InteresUniversidadId id = new InteresUniversidadId(estudiante.getId(), universidad.getId());

        // Regla de negocio 1: no se puede registrar dos veces el mismo interés.
        if (interesUniversidadRepository.existsById(id)) {
            throw new ReglaDeNegocioException(
                    "El estudiante ya registró interés en la universidad '" + universidad.getNombre() + "'");
        }

        // Regla de negocio 2: el interés en una universidad debe ser coherente con las carreras de interés del
        // estudiante; la universidad debe ofrecer al menos una de ellas.
        if (universidadCarreraRepository.contarCoincidenciasConIntereses(universidad.getId(), estudiante.getId()) == 0) {
            throw new ReglaDeNegocioException("La universidad '" + universidad.getNombre()
                    + "' no ofrece ninguna de las carreras de interés del estudiante");
        }

        return InteresUniversidadMapper.toResponse(
                interesUniversidadRepository.save(new InteresUniversidad(id, estudiante, universidad)));
    }

    @Transactional
    public void eliminar(Long estudianteId, Long universidadId) {
        interesUniversidadRepository.delete(obtener(new InteresUniversidadId(estudianteId, universidadId)));
    }

    private InteresUniversidad obtener(InteresUniversidadId id) {
        return interesUniversidadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Relación interés-universidad no encontrada para el estudiante "
                                + id.getEstudianteId() + " y la universidad " + id.getUniversidadId()));
    }
}
