package com.yourcompany.untitled.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

import java.util.UUID;

@MappedSuperclass // Marcada como una superclase mapeada en vez de como una entidad
@Getter @Setter
public class Identifiable {

    @Id @Hidden
    @Column(length=36)
    String oid; // La definición de la propiedad incluye anotaciones de OpenXava y JPA

    @PrePersist
    private void generateOid() {
        if (oid == null) {
            oid = UUID.randomUUID().toString();
        }
    }

}
