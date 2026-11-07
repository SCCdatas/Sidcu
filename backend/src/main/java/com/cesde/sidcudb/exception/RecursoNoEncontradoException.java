package com.cesde.sidcudb.exception;

/**
 * Se lanza cuando un recurso solicitado (por id, documento o clave compuesta) no existe.
 * Es traducida a HTTP 404 por {@link GlobalExceptionHandler}.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
