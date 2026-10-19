package com.yourcompany.untitled.model;

import java.time.*;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.*;

import com.yourcompany.untitled.calculators.NextNumberForYearCalculator;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import lombok.*;

@Entity @Getter @Setter
@View(members= // Esta vista no tiene nombre, por tanto será la vista usada por defecto
        "year, number, date;" + // Separados por coma significa en la misma línea
                "customer;" + // Punto y coma significa nueva línea
                "details;" +
                "remarks"
)
public class Invoice {

    @Id
    @Hidden
    @Column(length=36)
    String oid;

    @Column(length=4)
    @DefaultValueCalculator(CurrentYearCalculator.class) // Año actual
    int year;

    @Column(length=6)
    @DefaultValueCalculator(value= NextNumberForYearCalculator.class,
            properties=@PropertyValue(name="year") // Para inyectar el valor de year de Invoice
            // en el calculador antes de llamar a calculate()
    )
    int number;

    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) // Fecha actual
    LocalDate date;

    @TextArea
    String remarks;

    @ManyToOne(fetch=FetchType.LAZY, optional=false) // El cliente es obligatorio
    @ReferenceView("Simple") // La vista llamada 'Simple' se usará para visualizar esta referencia
    Customer customer;

    @ElementCollection
    @ListProperties("product.number, product.description, quantity")
    Collection<Detail> details;

    @PrePersist
    private void generateOid() {
        if (oid == null) {
            oid = UUID.randomUUID().toString();
        }
    }
}
