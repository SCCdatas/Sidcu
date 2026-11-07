package com.cesde.sidcudb.service;

import com.cesde.sidcudb.dto.request.InteresRequestDTO;
import com.cesde.sidcudb.dto.response.InteresResponseDTO;
import com.cesde.sidcudb.exception.RecursoNoEncontradoException;
import com.cesde.sidcudb.exception.ReglaDeNegocioException;
import com.cesde.sidcudb.mapper.InteresMapper;
import com.cesde.sidcudb.model.embeddable.InteresId;
import com.cesde.sidcudb.model.entity.Carrera;
import com.cesde.sidcudb.model.entity.Estudiante;
import com.cesde.sidcudb.model.entity.Interes;
import com.cesde.sidcudb.repository.CarreraRepository;
import com.cesde.sidcudb.repository.EstudianteRepository;
import com.cesde.sidcudb.repository.InteresRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class InteresService {

    /** Máximo de carreras de interés que un estudiante puede registrar. */
    public static final int MAXIMO_INTERESES_POR_ESTUDIANTE = 5;

    private final InteresRepository interesRepository;
    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;

    public InteresService(InteresRepository interesRepository,
                          EstudianteRepository estudianteRepository,
                          CarreraRepository carreraRepository) {
        this.interesRepository = interesRepository;
        this.estudianteRepository = estudianteRepository;
        this.carreraRepository = carreraRepository;
    }

    /** Lista intereses, con filtros opcionales por estudiante y/o carrera. */
    public List<InteresResponseDTO> listar(Long estudianteId, Long carreraId) {
        List<Interes> intereses;
        if (estudianteId != null) {
            intereses = interesRepository.buscarPorEstudiante(estudianteId);
            if (carreraId != null) {
                intereses = intereses.stream()
                        .filter(i -> i.getId().getCarreraId().equals(carreraId))
                        .toList();
            }
        } else if (carreraId != null) {
            intereses = interesRepository.buscarPorCarrera(carreraId);
        } else {
            intereses = interesRepository.findAll();
        }
        return intereses.stream().map(InteresMapper::toResponse).toList();
    }

    public InteresResponseDTO buscarPorId(Long estudianteId, Long carreraId) {
        return InteresMapper.toResponse(obtener(new InteresId(estudianteId, carreraId)));
    }

    @Transactional
    public InteresResponseDTO crear(InteresRequestDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.estudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Estudiante no encontrado con id " + dto.estudianteId()));
        Carrera carrera = carreraRepository.findById(dto.carreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Carrera no encontrada con id " + dto.carreraId()));

        InteresId id = new InteresId(estudiante.getId(), carrera.getId());

        // Regla de negocio 1: no se puede registrar dos veces el mismo interés.
        if (interesRepository.existsById(id)) {
            throw new ReglaDeNegocioException("El estudiante ya registró interés en la carrera '" + carrera.getNombre() + "'");
        }

        // Regla de negocio 2: límite de carreras de interés por estudiante, para orientar con criterio.
        if (interesRepository.contarPorEstudiante(estudiante.getId()) >= MAXIMO_INTERESES_POR_ESTUDIANTE) {
            throw new ReglaDeNegocioException(
                    "Un estudiante no puede registrar más de " + MAXIMO_INTERESES_POR_ESTUDIANTE + " carreras de interés");
        }

        return InteresMapper.toResponse(interesRepository.save(new Interes(id, estudiante, carrera)));
    }

    @Transactional
    public void eliminar(Long estudianteId, Long carreraId) {
        interesRepository.delete(obtener(new InteresId(estudianteId, carreraId)));
    }

    private Interes obtener(InteresId id) {
        return interesRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Registro de interés no encontrado para el estudiante "
                        + id.getEstudianteId() + " y la carrera " + id.getCarreraId()));
    }
}
