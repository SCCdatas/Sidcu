package com.cesde.sidcudb.controller;

import com.cesde.sidcudb.dto.request.UniversidadCarreraRequestDTO;
import com.cesde.sidcudb.dto.response.UniversidadCarreraResponseDTO;
import com.cesde.sidcudb.service.UniversidadCarreraService;
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
@RequestMapping("/api/universidades-carreras")
@Tag(name = "Oferta académica", description = "Carreras que ofrece cada universidad")
public class UniversidadCarreraController {

    private final UniversidadCarreraService universidadCarreraService;

    public UniversidadCarreraController(UniversidadCarreraService universidadCarreraService) {
        this.universidadCarreraService = universidadCarreraService;
    }

    @Operation(summary = "Listar la oferta académica", description = "Admite filtros opcionales por universidad y carrera.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<UniversidadCarreraResponseDTO>> listar(
            @Parameter(description = "Id de la universidad") @RequestParam(required = false) Long universidadId,
            @Parameter(description = "Id de la carrera") @RequestParam(required = false) Long carreraId) {
        return ResponseEntity.ok(universidadCarreraService.listar(universidadId, carreraId));
    }

    @Operation(summary = "Obtener una relación universidad-carrera")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relación encontrada"),
            @ApiResponse(responseCode = "404", description = "La relación no existe")
    })
    @GetMapping("/universidades/{universidadId}/carreras/{carreraId}")
    public ResponseEntity<UniversidadCarreraResponseDTO> buscarPorId(@PathVariable Long universidadId,
                                                                     @PathVariable Long carreraId) {
        return ResponseEntity.ok(universidadCarreraService.buscarPorId(universidadId, carreraId));
    }

    @Operation(summary = "Registrar que una universidad ofrece una carrera")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Relación registrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "La universidad o la carrera no existe"),
            @ApiResponse(responseCode = "409", description = "La universidad ya ofrece esa carrera")
    })
    @PostMapping
    public ResponseEntity<UniversidadCarreraResponseDTO> crear(@Valid @RequestBody UniversidadCarreraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(universidadCarreraService.crear(dto));
    }

    @Operation(summary = "Retirar una carrera de la oferta de una universidad")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Relación eliminada"),
            @ApiResponse(responseCode = "404", description = "La relación no existe")
    })
    @DeleteMapping("/universidades/{universidadId}/carreras/{carreraId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long universidadId, @PathVariable Long carreraId) {
        universidadCarreraService.eliminar(universidadId, carreraId);
        return ResponseEntity.noContent().build();
    }
}
