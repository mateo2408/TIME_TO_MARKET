package com.yourcompany.untitled.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity  // Esto marca la clase Customer como una entidad
@Getter @Setter // Esto hace los campos a continuación públicamente accesibles
@View(name="Simple", // Esta vista solo se usará cuando se especifique “Simple”
     members="number, name" // Muestra únicamente number y name en la misma línea
)
public class Customer {

    @Id  // La propiedad number es la clave. Las claves son obligatorias (required) por defecto
    @Column(length=6)  // La longitud de columna se usa a nivel UI y a nivel DB
    int number;

    @Column(length=50) // La longitud de columna se usa a nivel UI y a nivel DB
    @Required  // Se mostrará un error de validación si la propiedad name se deja en blanco
    String name;

    @Embedded // Así para referenciar a una clase incrustable
    Address address; // Una referencia Java convencional
}
