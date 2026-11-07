package com.cesde.sidcudb.controller;

import com.cesde.sidcudb.dto.request.UniversidadRequestDTO;
import com.cesde.sidcudb.dto.response.UniversidadResponseDTO;
import com.cesde.sidcudb.service.UniversidadService;
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
@RequestMapping("/api/universidades")
@Tag(name = "Universidades", description = "Instituciones de educación superior y su ubicación")
public class UniversidadController {

    private final UniversidadService universidadService;

    public UniversidadController(UniversidadService universidadService) {
        this.universidadService = universidadService;
    }

    @Operation(summary = "Listar universidades", description = "Admite filtros opcionales por ciudad y por texto contenido en el nombre.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<UniversidadResponseDTO>> listar(
            @Parameter(description = "Ciudad (sin distinguir mayúsculas)") @RequestParam(required = false) String ciudad,
            @Parameter(description = "Texto contenido en el nombre") @RequestParam(required = false) String nombre) {
        return ResponseEntity.ok(universidadService.listar(ciudad, nombre));
    }

    @Operation(summary = "Obtener una universidad por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Universidad encontrada"),
            @ApiResponse(responseCode = "404", description = "La universidad no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UniversidadResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(universidadService.buscarPorId(id));
    }

    @Operation(summary = "Crear una universidad", description = "El nombre debe ser único.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Universidad creada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe una universidad con ese nombre")
    })
    @PostMapping
    public ResponseEntity<UniversidadResponseDTO> crear(@Valid @RequestBody UniversidadRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(universidadService.crear(dto));
    }

    @Operation(summary = "Actualizar una universidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Universidad actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "La universidad no existe"),
            @ApiResponse(responseCode = "409", description = "El nombre ya pertenece a otra universidad")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UniversidadResponseDTO> actualizar(@PathVariable Long id,
                                                             @Valid @RequestBody UniversidadRequestDTO dto) {
        return ResponseEntity.ok(universidadService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar una universidad", description = "No se puede eliminar si tiene carreras ofertadas o estudiantes interesados.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Universidad eliminada"),
            @ApiResponse(responseCode = "404", description = "La universidad no existe"),
            @ApiResponse(responseCode = "409", description = "La universidad tiene dependencias activas")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        universidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
