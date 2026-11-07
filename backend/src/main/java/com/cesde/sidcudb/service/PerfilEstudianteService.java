package com.cesde.sidcudb.service;

import com.cesde.sidcudb.dto.request.PerfilEstudianteActualizacionRequestDTO;
import com.cesde.sidcudb.dto.request.PerfilEstudianteRequestDTO;
import com.cesde.sidcudb.dto.response.PerfilEstudianteResponseDTO;
import com.cesde.sidcudb.exception.RecursoNoEncontradoException;
import com.cesde.sidcudb.exception.ReglaDeNegocioException;
import com.cesde.sidcudb.mapper.PerfilEstudianteMapper;
import com.cesde.sidcudb.model.entity.Estudiante;
import com.cesde.sidcudb.model.entity.PerfilEstudiante;
import com.cesde.sidcudb.repository.EstudianteRepository;
import com.cesde.sidcudb.repository.PerfilEstudianteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PerfilEstudianteService {

    private final PerfilEstudianteRepository perfilEstudianteRepository;
    private final EstudianteRepository estudianteRepository;

    public PerfilEstudianteService(PerfilEstudianteRepository perfilEstudianteRepository,
                                   EstudianteRepository estudianteRepository) {
        this.perfilEstudianteRepository = perfilEstudianteRepository;
        this.estudianteRepository = estudianteRepository;
    }

    /** Lista perfiles; si se indica {@code estudianteId} devuelve solo el perfil de ese estudiante. */
    public List<PerfilEstudianteResponseDTO> listar(Long estudianteId) {
        List<PerfilEstudiante> perfiles = estudianteId == null
                ? perfilEstudianteRepository.findAll()
                : perfilEstudianteRepository.buscarPorEstudiante(estudianteId).stream().toList();
        return perfiles.stream().map(PerfilEstudianteMapper::toResponse).toList();
    }

    public PerfilEstudianteResponseDTO buscarPorId(Long id) {
        return PerfilEstudianteMapper.toResponse(obtener(id));
    }

    @Transactional
    public PerfilEstudianteResponseDTO crear(PerfilEstudianteRequestDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.estudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Estudiante no encontrado con id " + dto.estudianteId()));

        // Regla de negocio: un estudiante solo puede tener un perfil (relación uno a uno).
        if (perfilEstudianteRepository.buscarPorEstudiante(estudiante.getId()).isPresent()) {
            throw new ReglaDeNegocioException("El estudiante ya tiene un perfil registrado");
        }

        PerfilEstudiante perfil = new PerfilEstudiante();
        perfil.setEstudiante(estudiante);
        perfil.setBiografia(dto.biografia());
        perfil.setSitioWeb(dto.sitioWeb());
        return PerfilEstudianteMapper.toResponse(perfilEstudianteRepository.save(perfil));
    }

    @Transactional
    public PerfilEstudianteResponseDTO actualizar(Long id, PerfilEstudianteActualizacionRequestDTO dto) {
        PerfilEstudiante perfil = obtener(id);
        perfil.setBiografia(dto.biografia());
        perfil.setSitioWeb(dto.sitioWeb());
        return PerfilEstudianteMapper.toResponse(perfilEstudianteRepository.save(perfil));
    }

    @Transactional
    public void eliminar(Long id) {
        perfilEstudianteRepository.delete(obtener(id));
    }

    private PerfilEstudiante obtener(Long id) {
        return perfilEstudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Perfil de estudiante no encontrado con id " + id));
    }
}
