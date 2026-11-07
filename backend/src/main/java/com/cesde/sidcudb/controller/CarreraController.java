package com.cesde.sidcudb.controller;

import com.cesde.sidcudb.dto.request.CarreraRequestDTO;
import com.cesde.sidcudb.dto.response.CarreraResponseDTO;
import com.cesde.sidcudb.model.enums.NivelAcademico;
import com.cesde.sidcudb.service.CarreraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
@Tag(name = "Carreras", description = "Catálogo de carreras universitarias, técnicas y tecnológicas")
public class CarreraController {

    private final CarreraService carreraService;

    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @Operation(summary = "Listar carreras", description = "Devuelve el catálogo de carreras. Admite filtros opcionales por área y nivel académico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro de filtro inválido")
    })
    @GetMapping
    public ResponseEntity<List<CarreraResponseDTO>> listar(
            @Parameter(description = "Área de conocimiento (sin distinguir mayúsculas)") @RequestParam(required = false) String area,
            @Parameter(description = "Nivel académico") @RequestParam(required = false) NivelAcademico nivel) {
        return ResponseEntity.ok(carreraService.listar(area, nivel));
    }

    @Operation(summary = "Obtener una carrera por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrera encontrada"),
            @ApiResponse(responseCode = "404", description = "La carrera no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carreraService.buscarPorId(id));
    }

    @Operation(summary = "Crear una carrera", description = "El nombre debe ser único. Opcionalmente se asocian habilidades existentes.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Carrera creada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Alguna habilidad indicada no existe"),
            @ApiResponse(responseCode = "409", description = "Ya existe una carrera con ese nombre")
    })
    @PostMapping
    public ResponseEntity<CarreraResponseDTO> crear(@Valid @RequestBody CarreraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraService.crear(dto));
    }

    @Operation(summary = "Actualizar una carrera")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carrera actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "La carrera o alguna habilidad no existe"),
            @ApiResponse(responseCode = "409", description = "El nombre ya pertenece a otra carrera")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody CarreraRequestDTO dto) {
        return ResponseEntity.ok(carreraService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar una carrera", description = "No se puede eliminar si está ofertada por universidades o tiene estudiantes interesados.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Carrera eliminada"),
            @ApiResponse(responseCode = "404", description = "La carrera no existe"),
            @ApiResponse(responseCode = "409", description = "La carrera tiene dependencias activas")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carreraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
