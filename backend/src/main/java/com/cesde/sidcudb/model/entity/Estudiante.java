package com.cesde.sidcudb.model.entity;

import com.cesde.sidcudb.model.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estudiantes")
public class Estudiante extends BaseEntity {

    @Column(name = "documento", nullable = false, unique = true, length = 20)
    private String documento;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "ciudad", nullable = false, length = 50)
    private String ciudad;

    /** Relación uno a muchos: carreras de interés del estudiante. */
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.REMOVE)
    private List<Interes> intereses = new ArrayList<>();

    /** Relación uno a muchos: universidades de interés del estudiante. */
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.REMOVE)
    private List<InteresUniversidad> interesesUniversidad = new ArrayList<>();

    /** Relación uno a uno (lado inverso): perfil extendido del estudiante. */
    @OneToOne(mappedBy = "estudiante", cascade = CascadeType.REMOVE)
    private PerfilEstudiante perfil;
}
