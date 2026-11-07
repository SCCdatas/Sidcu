package com.cesde.sidcudb.model.entity;

import com.cesde.sidcudb.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "perfiles_estudiante")
public class PerfilEstudiante extends BaseEntity {

    @Column(name = "biografia", length = 255)
    private String biografia;

    @Column(name = "sitio_web", length = 100)
    private String sitioWeb;

    /** Relación uno a uno (lado propietario). Sin cascada: eliminar un perfil nunca debe eliminar al estudiante. */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "estudiante_id",
            referencedColumnName = "id",
            nullable = false,
            unique = true
    )
    private Estudiante estudiante;
}
