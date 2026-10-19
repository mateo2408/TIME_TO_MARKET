package com.yourcompany.untitled.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

import java.math.BigDecimal;

@Entity @Getter @Setter
public class Product {

    @Id @Column(length=9)
    int number;

    @Column(length=50) @Required
    String description;

    @ManyToOne( // La referencia se almacena como una relación en la base de datos
            fetch=FetchType.LAZY, // La referencia se carga bajo demanda
            optional=true) // La referencia puede estar sin valor
    @DescriptionsList // Así la referencia se visualiza usando un combo
    Category category; // Una referencia Java convencional

    @Money // La propiedad price se usa para almacenar dinero
    BigDecimal price; // BigDecimal se suele usar para dinero

    @Files // Una galería de fotos completa está disponible
    @Column(length=32) // La cadena de 32 de longitud es para almacenar la clave de la galería
    String photos;

    @TextArea // Esto es para un texto grande, se usará un área de texto o equivalente
    String remarks;

    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    Author author;
}

