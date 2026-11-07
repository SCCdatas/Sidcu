package com.cesde.sidcudb.controller;

import com.cesde.sidcudb.dto.request.InteresUniversidadRequestDTO;
import com.cesde.sidcudb.dto.response.InteresUniversidadResponseDTO;
import com.cesde.sidcudb.service.InteresUniversidadService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/intereses-universidades")
@Tag(name = "Intereses en universidades", description = "Universidades en las que un estudiante manifiesta interés")
public class InteresUniversidadController {

    private final InteresUniversidadService interesUniversidadService;

    public InteresUniversidadController(InteresUniversidadService interesUniversidadService) {
        this.interesUniversidadService = interesUniversidadService;
    }

    @Operation(summary = "Listar intereses en universidades", description = "Admite filtros opcionales por estudiante y universidad.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<InteresUniversidadResponseDTO>> listar(
            @Parameter(description = "Id del estudiante") @RequestParam(required = false) Long estudianteId,
            @Parameter(description = "Id de la universidad") @RequestParam(required = false) Long universidadId) {
        return ResponseEntity.ok(interesUniversidadService.listar(estudianteId, universidadId));
    }

    @Operation(summary = "Obtener un interés por estudiante y universidad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interés encontrado"),
            @ApiResponse(responseCode = "404", description = "El interés no existe")
    })
    @GetMapping("/estudiantes/{estudianteId}/universidades/{universidadId}")
    public ResponseEntity<InteresUniversidadResponseDTO> buscarPorId(@PathVariable Long estudianteId,
                                                                     @PathVariable Long universidadId) {
        return ResponseEntity.ok(interesUniversidadService.buscarPorId(estudianteId, universidadId));
    }

    @Operation(summary = "Registrar interés en una universidad",
            description = "La universidad debe ofrecer al menos una de las carreras de interés del estudiante.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interés registrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "El estudiante o la universidad no existe"),
            @ApiResponse(responseCode = "409", description = "Interés duplicado o sin carreras de interés en común")
    })
    @PostMapping
    public ResponseEntity<InteresUniversidadResponseDTO> crear(@Valid @RequestBody InteresUniversidadRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(interesUniversidadService.crear(dto));
    }

    @Operation(summary = "Eliminar un interés en una universidad")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Interés eliminado"),
            @ApiResponse(responseCode = "404", description = "El interés no existe")
    })
    @DeleteMapping("/estudiantes/{estudianteId}/universidades/{universidadId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long estudianteId, @PathVariable Long universidadId) {
        interesUniversidadService.eliminar(estudianteId, universidadId);
        return ResponseEntity.noContent().build();
    }
}
