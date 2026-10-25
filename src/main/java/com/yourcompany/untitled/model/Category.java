package com.yourcompany.untitled.model;

import java.util.UUID;

import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity @Getter @Setter
public class Category extends Identifiable {

    @Column(length=50)
    String description;

}
