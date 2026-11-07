package com.cesde.sidcudb.exception;

/**
 * Se lanza cuando una operación viola una regla de negocio del dominio
 * (duplicados, límites, dependencias existentes, etc.).
 * Es traducida a HTTP 409 por {@link GlobalExceptionHandler}.
 */
public class ReglaDeNegocioException extends RuntimeException {

    public ReglaDeNegocioException(String mensaje) {
        super(mensaje);
    }
}
