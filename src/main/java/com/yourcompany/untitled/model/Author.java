package com.yourcompany.untitled.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Entity @Getter @Setter
public class Author {

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
    @OneToMany(mappedBy="author")
    @ListProperties("number, description, price")
    Collection<Product> products;
}
