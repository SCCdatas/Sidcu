package com.cesde.sidcudb.service;

import com.cesde.sidcudb.dto.request.UniversidadCarreraRequestDTO;
import com.cesde.sidcudb.dto.response.UniversidadCarreraResponseDTO;
import com.cesde.sidcudb.exception.RecursoNoEncontradoException;
import com.cesde.sidcudb.exception.ReglaDeNegocioException;
import com.cesde.sidcudb.mapper.UniversidadCarreraMapper;
import com.cesde.sidcudb.model.embeddable.UniversidadCarreraId;
import com.cesde.sidcudb.model.entity.Carrera;
import com.cesde.sidcudb.model.entity.Universidad;
import com.cesde.sidcudb.model.entity.UniversidadCarrera;
import com.cesde.sidcudb.repository.CarreraRepository;
import com.cesde.sidcudb.repository.UniversidadCarreraRepository;
import com.cesde.sidcudb.repository.UniversidadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UniversidadCarreraService {

    private final UniversidadCarreraRepository universidadCarreraRepository;
    private final UniversidadRepository universidadRepository;
    private final CarreraRepository carreraRepository;

    public UniversidadCarreraService(UniversidadCarreraRepository universidadCarreraRepository,
                                     UniversidadRepository universidadRepository,
                                     CarreraRepository carreraRepository) {
        this.universidadCarreraRepository = universidadCarreraRepository;
        this.universidadRepository = universidadRepository;
        this.carreraRepository = carreraRepository;
    }

    /** Lista la oferta académica, con filtros opcionales por universidad y/o carrera. */
    public List<UniversidadCarreraResponseDTO> listar(Long universidadId, Long carreraId) {
        List<UniversidadCarrera> relaciones;
        if (universidadId != null) {
            relaciones = universidadCarreraRepository.buscarPorUniversidad(universidadId);
            if (carreraId != null) {
                relaciones = relaciones.stream()
                        .filter(r -> r.getId().getCarreraId().equals(carreraId))
                        .toList();
            }
        } else if (carreraId != null) {
            relaciones = universidadCarreraRepository.buscarPorCarrera(carreraId);
        } else {
            relaciones = universidadCarreraRepository.findAll();
        }
        return relaciones.stream().map(UniversidadCarreraMapper::toResponse).toList();
    }

    public UniversidadCarreraResponseDTO buscarPorId(Long universidadId, Long carreraId) {
        return UniversidadCarreraMapper.toResponse(obtener(new UniversidadCarreraId(universidadId, carreraId)));
    }

    @Transactional
    public UniversidadCarreraResponseDTO crear(UniversidadCarreraRequestDTO dto) {
        Universidad universidad = universidadRepository.findById(dto.universidadId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Universidad no encontrada con id " + dto.universidadId()));
        Carrera carrera = carreraRepository.findById(dto.carreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Carrera no encontrada con id " + dto.carreraId()));

        UniversidadCarreraId id = new UniversidadCarreraId(universidad.getId(), carrera.getId());

        // Regla de negocio: una universidad no puede ofertar dos veces la misma carrera.
        if (universidadCarreraRepository.existsById(id)) {
            throw new ReglaDeNegocioException("La universidad '" + universidad.getNombre()
                    + "' ya ofrece la carrera '" + carrera.getNombre() + "'");
        }

        return UniversidadCarreraMapper.toResponse(
                universidadCarreraRepository.save(new UniversidadCarrera(id, universidad, carrera)));
    }

    @Transactional
    public void eliminar(Long universidadId, Long carreraId) {
        universidadCarreraRepository.delete(obtener(new UniversidadCarreraId(universidadId, carreraId)));
    }

    private UniversidadCarrera obtener(UniversidadCarreraId id) {
        return universidadCarreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Relación universidad-carrera no encontrada para la universidad "
                                + id.getUniversidadId() + " y la carrera " + id.getCarreraId()));
    }
}
