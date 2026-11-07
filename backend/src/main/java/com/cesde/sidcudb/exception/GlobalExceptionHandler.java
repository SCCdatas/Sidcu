package com.cesde.sidcudb.exception;

import com.cesde.sidcudb.dto.response.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Punto único de captura de excepciones de la API.
 * Convierte cada excepción en una respuesta JSON uniforme ({@link ErrorResponseDTO}).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> manejarNoEncontrado(RecursoNoEncontradoException ex,
                                                                HttpServletRequest request) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage(), request, List.of());
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<ErrorResponseDTO> manejarReglaDeNegocio(ReglaDeNegocioException ex,
                                                                  HttpServletRequest request) {
        return construir(HttpStatus.CONFLICT, ex.getMessage(), request, List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> manejarValidacion(MethodArgumentNotValidException ex,
                                                              HttpServletRequest request) {
        List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        return construir(HttpStatus.BAD_REQUEST, "La solicitud contiene datos inválidos", request, detalles);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> manejarCuerpoIlegible(HttpMessageNotReadableException ex,
                                                                  HttpServletRequest request) {
        return construir(HttpStatus.BAD_REQUEST,
                "El cuerpo de la solicitud es inválido o tiene un formato incorrecto", request, List.of());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> manejarTipoIncorrecto(MethodArgumentTypeMismatchException ex,
                                                                  HttpServletRequest request) {
        String mensaje = "El parámetro '" + ex.getName() + "' tiene un valor inválido";
        return construir(HttpStatus.BAD_REQUEST, mensaje, request, List.of());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> manejarIntegridad(DataIntegrityViolationException ex,
                                                              HttpServletRequest request) {
        log.warn("Violación de integridad de datos: {}", ex.getMostSpecificCause().getMessage());
        return construir(HttpStatus.CONFLICT,
                "La operación viola una restricción de integridad de los datos", request, List.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> manejarErrorInesperado(Exception ex, HttpServletRequest request) {
        // Las excepciones propias de Spring (ruta inexistente, método no permitido, etc.) ya traen su estado HTTP.
        if (ex instanceof ErrorResponse errorSpring) {
            String detalle = errorSpring.getBody().getDetail();
            return construir(errorSpring.getStatusCode(),
                    detalle != null ? detalle : "No fue posible procesar la solicitud", request, List.of());
        }
        log.error("Error inesperado en {}", request.getRequestURI(), ex);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno en el servidor",
                request, List.of());
    }

    private ResponseEntity<ErrorResponseDTO> construir(HttpStatusCode estado, String mensaje,
                                                       HttpServletRequest request, List<String> detalles) {
        HttpStatus conocido = HttpStatus.resolve(estado.value());
        ErrorResponseDTO cuerpo = new ErrorResponseDTO(
                LocalDateTime.now(),
                estado.value(),
                conocido != null ? conocido.getReasonPhrase() : "Error",
                mensaje,
                request.getRequestURI(),
                detalles
        );
        return ResponseEntity.status(estado).body(cuerpo);
    }
}
