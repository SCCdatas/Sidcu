package com.cesde.sidcudb.controller;

import com.cesde.sidcudb.dto.request.HabilidadRequestDTO;
import com.cesde.sidcudb.dto.response.HabilidadResponseDTO;
import com.cesde.sidcudb.service.HabilidadService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/habilidades")
@Tag(name = "Habilidades", description = "Competencias que se desarrollan en las carreras (relación muchos a muchos)")
public class HabilidadController {

    private final HabilidadService habilidadService;

    public HabilidadController(HabilidadService habilidadService) {
        this.habilidadService = habilidadService;
    }

    @Operation(summary = "Listar habilidades")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<HabilidadResponseDTO>> listar() {
        return ResponseEntity.ok(habilidadService.listar());
    }

    @Operation(summary = "Obtener una habilidad por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habilidad encontrada"),
            @ApiResponse(responseCode = "404", description = "La habilidad no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HabilidadResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(habilidadService.buscarPorId(id));
    }

    @Operation(summary = "Crear una habilidad")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Habilidad creada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe una habilidad con ese nombre")
    })
    @PostMapping
    public ResponseEntity<HabilidadResponseDTO> crear(@Valid @RequestBody HabilidadRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(habilidadService.crear(dto));
    }

    @Operation(summary = "Actualizar una habilidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habilidad actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "La habilidad no existe"),
            @ApiResponse(responseCode = "409", description = "El nombre ya pertenece a otra habilidad")
    })
    @PutMapping("/{id}")
    public ResponseEntity<HabilidadResponseDTO> actualizar(@PathVariable Long id,
                                                           @Valid @RequestBody HabilidadRequestDTO dto) {
        return ResponseEntity.ok(habilidadService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar una habilidad", description = "No se puede eliminar si está asociada a carreras.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Habilidad eliminada"),
            @ApiResponse(responseCode = "404", description = "La habilidad no existe"),
            @ApiResponse(responseCode = "409", description = "La habilidad está asociada a carreras")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        habilidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
