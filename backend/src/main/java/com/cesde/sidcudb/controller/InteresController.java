package com.cesde.sidcudb.controller;

import com.cesde.sidcudb.dto.request.InteresRequestDTO;
import com.cesde.sidcudb.dto.response.InteresResponseDTO;
import com.cesde.sidcudb.service.InteresService;
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
@RequestMapping("/api/intereses")
@Tag(name = "Intereses en carreras", description = "Carreras en las que un estudiante manifiesta interés")
public class InteresController {

    private final InteresService interesService;

    public InteresController(InteresService interesService) {
        this.interesService = interesService;
    }

    @Operation(summary = "Listar intereses en carreras", description = "Admite filtros opcionales por estudiante y carrera.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<InteresResponseDTO>> listar(
            @Parameter(description = "Id del estudiante") @RequestParam(required = false) Long estudianteId,
            @Parameter(description = "Id de la carrera") @RequestParam(required = false) Long carreraId) {
        return ResponseEntity.ok(interesService.listar(estudianteId, carreraId));
    }

    @Operation(summary = "Obtener un interés por estudiante y carrera")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Interés encontrado"),
            @ApiResponse(responseCode = "404", description = "El interés no existe")
    })
    @GetMapping("/estudiantes/{estudianteId}/carreras/{carreraId}")
    public ResponseEntity<InteresResponseDTO> buscarPorId(@PathVariable Long estudianteId,
                                                          @PathVariable Long carreraId) {
        return ResponseEntity.ok(interesService.buscarPorId(estudianteId, carreraId));
    }

    @Operation(summary = "Registrar interés en una carrera", description = "Un estudiante puede registrar hasta 5 carreras de interés, sin repetir.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Interés registrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "El estudiante o la carrera no existe"),
            @ApiResponse(responseCode = "409", description = "Interés duplicado o límite de intereses alcanzado")
    })
    @PostMapping
    public ResponseEntity<InteresResponseDTO> crear(@Valid @RequestBody InteresRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(interesService.crear(dto));
    }

    @Operation(summary = "Eliminar un interés en una carrera")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Interés eliminado"),
            @ApiResponse(responseCode = "404", description = "El interés no existe")
    })
    @DeleteMapping("/estudiantes/{estudianteId}/carreras/{carreraId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long estudianteId, @PathVariable Long carreraId) {
        interesService.eliminar(estudianteId, carreraId);
        return ResponseEntity.noContent().build();
    }
}
