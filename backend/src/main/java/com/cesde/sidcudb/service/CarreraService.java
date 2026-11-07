package com.cesde.sidcudb.service;

import com.cesde.sidcudb.dto.request.CarreraRequestDTO;
import com.cesde.sidcudb.dto.response.CarreraResponseDTO;
import com.cesde.sidcudb.exception.RecursoNoEncontradoException;
import com.cesde.sidcudb.exception.ReglaDeNegocioException;
import com.cesde.sidcudb.mapper.CarreraMapper;
import com.cesde.sidcudb.model.entity.Carrera;
import com.cesde.sidcudb.model.entity.Habilidad;
import com.cesde.sidcudb.model.enums.NivelAcademico;
import com.cesde.sidcudb.repository.CarreraRepository;
import com.cesde.sidcudb.repository.HabilidadRepository;
import com.cesde.sidcudb.repository.InteresRepository;
import com.cesde.sidcudb.repository.UniversidadCarreraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class CarreraService {

    private final CarreraRepository carreraRepository;
    private final HabilidadRepository habilidadRepository;
    private final UniversidadCarreraRepository universidadCarreraRepository;
    private final InteresRepository interesRepository;

    public CarreraService(CarreraRepository carreraRepository,
                          HabilidadRepository habilidadRepository,
                          UniversidadCarreraRepository universidadCarreraRepository,
                          InteresRepository interesRepository) {
        this.carreraRepository = carreraRepository;
        this.habilidadRepository = habilidadRepository;
        this.universidadCarreraRepository = universidadCarreraRepository;
        this.interesRepository = interesRepository;
    }

    /** Lista carreras, con filtros opcionales por área y/o nivel académico. */
    public List<CarreraResponseDTO> listar(String area, NivelAcademico nivel) {
        List<Carrera> carreras;
        if (area != null && !area.isBlank()) {
            carreras = carreraRepository.findByAreaIgnoreCase(area.trim());
            if (nivel != null) {
                carreras = carreras.stream().filter(c -> c.getNivel() == nivel).toList();
            }
        } else if (nivel != null) {
            carreras = carreraRepository.buscarPorNivel(nivel);
        } else {
            carreras = carreraRepository.findAll();
        }
        return carreras.stream().map(CarreraMapper::toResponse).toList();
    }

    public CarreraResponseDTO buscarPorId(Long id) {
        return CarreraMapper.toResponse(obtener(id));
    }

    @Transactional
    public CarreraResponseDTO crear(CarreraRequestDTO dto) {
        // Regla de negocio: el nombre de una carrera es único en el catálogo.
        carreraRepository.findByNombre(dto.nombre().trim()).ifPresent(existente -> {
            throw new ReglaDeNegocioException("Ya existe una carrera con el nombre '" + existente.getNombre() + "'");
        });

        Carrera carrera = new Carrera();
        aplicar(carrera, dto);
        return CarreraMapper.toResponse(carreraRepository.save(carrera));
    }

    @Transactional
    public CarreraResponseDTO actualizar(Long id, CarreraRequestDTO dto) {
        Carrera carrera = obtener(id);

        carreraRepository.findByNombre(dto.nombre().trim())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new ReglaDeNegocioException("El nombre ya pertenece a otra carrera");
                });

        aplicar(carrera, dto);
        return CarreraMapper.toResponse(carreraRepository.save(carrera));
    }

    @Transactional
    public void eliminar(Long id) {
        Carrera carrera = obtener(id);

        // Regla de negocio: no se elimina una carrera que está siendo ofertada o que interesa a estudiantes.
        if (universidadCarreraRepository.contarPorCarrera(id) > 0) {
            throw new ReglaDeNegocioException("No se puede eliminar la carrera: está ofertada por una o más universidades");
        }
        if (interesRepository.contarPorCarrera(id) > 0) {
            throw new ReglaDeNegocioException("No se puede eliminar la carrera: hay estudiantes interesados en ella");
        }
        carreraRepository.delete(carrera);
    }

    private Carrera obtener(Long id) {
        return carreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con id " + id));
    }

    private void aplicar(Carrera carrera, CarreraRequestDTO dto) {
        carrera.setNombre(dto.nombre().trim());
        carrera.setArea(dto.area().trim());
        carrera.setNivel(dto.nivel());
        carrera.setHabilidades(resolverHabilidades(dto.habilidadIds()));
    }

    private Set<Habilidad> resolverHabilidades(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }
        List<Habilidad> encontradas = habilidadRepository.findAllById(ids);
        if (encontradas.size() != ids.size()) {
            throw new RecursoNoEncontradoException("Una o más habilidades indicadas no existen");
        }
        return new HashSet<>(encontradas);
    }
}
