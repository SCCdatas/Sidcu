package com.cesde.sidcudb.controller;

import com.cesde.sidcudb.dto.request.PerfilEstudianteActualizacionRequestDTO;
import com.cesde.sidcudb.dto.request.PerfilEstudianteRequestDTO;
import com.cesde.sidcudb.dto.response.PerfilEstudianteResponseDTO;
import com.cesde.sidcudb.service.PerfilEstudianteService;
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
@RequestMapping("/api/perfiles-estudiantes")
@Tag(name = "Perfiles de estudiantes", description = "Información ampliada de cada estudiante (relación uno a uno)")
public class PerfilEstudianteController {

    private final PerfilEstudianteService perfilEstudianteService;

    public PerfilEstudianteController(PerfilEstudianteService perfilEstudianteService) {
        this.perfilEstudianteService = perfilEstudianteService;
    }

    @Operation(summary = "Listar perfiles", description = "Con el filtro opcional estudianteId devuelve solo el perfil de ese estudiante.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<PerfilEstudianteResponseDTO>> listar(
            @Parameter(description = "Id del estudiante dueño del perfil") @RequestParam(required = false) Long estudianteId) {
        return ResponseEntity.ok(perfilEstudianteService.listar(estudianteId));
    }

    @Operation(summary = "Obtener un perfil por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "El perfil no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilEstudianteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfilEstudianteService.buscarPorId(id));
    }

    @Operation(summary = "Crear el perfil de un estudiante", description = "Cada estudiante puede tener un único perfil.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Perfil creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "El estudiante no existe"),
            @ApiResponse(responseCode = "409", description = "El estudiante ya tiene un perfil")
    })
    @PostMapping
    public ResponseEntity<PerfilEstudianteResponseDTO> crear(@Valid @RequestBody PerfilEstudianteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilEstudianteService.crear(dto));
    }

    @Operation(summary = "Actualizar un perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "El perfil no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PerfilEstudianteResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PerfilEstudianteActualizacionRequestDTO dto) {
        return ResponseEntity.ok(perfilEstudianteService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un perfil", description = "No elimina al estudiante.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Perfil eliminado"),
            @ApiResponse(responseCode = "404", description = "El perfil no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        perfilEstudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
