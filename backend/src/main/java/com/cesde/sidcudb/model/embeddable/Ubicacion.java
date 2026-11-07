package com.cesde.sidcudb.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Ubicacion {

    @Column (name = "direccion", length = 150)
    private String direccion;

    @Column (name = "codigo_postal", length = 20)
    private String codigoPostal;
}