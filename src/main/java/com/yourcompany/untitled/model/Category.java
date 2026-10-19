package com.yourcompany.untitled.model;

import java.util.UUID;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
public class Category {

    @Id
    @Hidden // La propiedad no se muestra al usuario. Es un identificador interno
    @Column(length=36)
    String oid;

    @Column(length=50)
    String description;

    @PrePersist
    private void generateOid() {
        if (oid == null) {
            oid = UUID.randomUUID().toString();
        }
    }

}
