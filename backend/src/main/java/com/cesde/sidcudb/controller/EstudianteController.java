package com.cesde.sidcudb.controller;

import com.cesde.sidcudb.dto.request.EstudianteRequestDTO;
import com.cesde.sidcudb.dto.response.EstudianteResponseDTO;
import com.cesde.sidcudb.dto.response.UniversidadResponseDTO;
import com.cesde.sidcudb.service.EstudianteService;
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
@RequestMapping("/api/estudiantes")
@Tag(name = "Estudiantes", description = "Gestión de estudiantes que buscan orientación académica")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Operation(summary = "Listar estudiantes", description = "Admite filtros opcionales por documento y ciudad.")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<EstudianteResponseDTO>> listar(
            @Parameter(description = "Número de documento exacto") @RequestParam(required = false) String documento,
            @Parameter(description = "Ciudad de residencia") @RequestParam(required = false) String ciudad) {
        return ResponseEntity.ok(estudianteService.listar(documento, ciudad));
    }

    @Operation(summary = "Obtener un estudiante por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado"),
            @ApiResponse(responseCode = "404", description = "El estudiante no existe")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.buscarPorId(id));
    }

    @Operation(summary = "Universidades recomendadas para un estudiante",
            description = "Universidades que ofrecen al menos una de sus carreras de interés; primero las de su ciudad.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recomendaciones calculadas (puede ser una lista vacía)"),
            @ApiResponse(responseCode = "404", description = "El estudiante no existe")
    })
    @GetMapping("/{id}/universidades-recomendadas")
    public ResponseEntity<List<UniversidadResponseDTO>> recomendarUniversidades(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.recomendarUniversidades(id));
    }

    @Operation(summary = "Crear un estudiante", description = "El documento debe ser único.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estudiante creado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un estudiante con ese documento")
    })
    @PostMapping
    public ResponseEntity<EstudianteResponseDTO> crear(@Valid @RequestBody EstudianteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.crear(dto));
    }

    @Operation(summary = "Actualizar un estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "El estudiante no existe"),
            @ApiResponse(responseCode = "409", description = "El documento ya pertenece a otro estudiante")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteResponseDTO> actualizar(@PathVariable Long id,
                                                            @Valid @RequestBody EstudianteRequestDTO dto) {
        return ResponseEntity.ok(estudianteService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un estudiante", description = "También elimina su perfil y sus intereses registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Estudiante eliminado"),
            @ApiResponse(responseCode = "404", description = "El estudiante no existe")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
