package com.yourcompany.untitled.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Entity @Getter @Setter

public class Author extends Identifiable {

    @Column(length=50)
    String description;

@OneToMany(mappedBy="author")
    @ListProperties("number, description, price")
    Collection<Product> products;
}
