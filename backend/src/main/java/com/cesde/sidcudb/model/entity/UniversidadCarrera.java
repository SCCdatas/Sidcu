
package com.cesde.sidcudb.model.entity;

import com.cesde.sidcudb.model.embeddable.UniversidadCarreraId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "universidad_carrera")
public class UniversidadCarrera {

    @EmbeddedId
    private UniversidadCarreraId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("universidadId")
    @JoinColumn(name = "universidad_id")
    private Universidad universidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("carreraId")
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;
}
