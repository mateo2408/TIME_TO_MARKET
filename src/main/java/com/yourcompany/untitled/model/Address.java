package com.yourcompany.untitled.model;

import javax.persistence.*;
import lombok.*;

@Embeddable // Usamos @Embeddable en vez de @Entity
@Getter @Setter
public class Address {

    @Column(length = 30) // Los miembros se anotan igual que en las entidades
    String street;

    @Column(length = 5)
    int zipCode;

    @Column(length = 20)
    String city;

    @Column(length = 30)
    String state;

}
