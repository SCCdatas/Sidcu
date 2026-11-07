package com.cesde.sidcudb.model.entity;

import com.cesde.sidcudb.model.base.BaseEntity;
import com.cesde.sidcudb.model.enums.NivelAcademico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "carreras")
public class Carrera extends BaseEntity {

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "area", nullable = false)
    private String area;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", nullable = false, length = 20)
    private NivelAcademico nivel;

    /** Relación muchos a muchos: una carrera desarrolla varias habilidades y una habilidad aplica a varias carreras. */
    @ManyToMany
    @JoinTable(
            name = "carrera_habilidad",
            joinColumns = @JoinColumn(name = "carrera_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidad_id")
    )
    private Set<Habilidad> habilidades = new HashSet<>();
}
