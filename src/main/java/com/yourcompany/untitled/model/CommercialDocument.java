package com.yourcompany.untitled.model;

import com.yourcompany.untitled.model.Customer;

import java.math.BigDecimal;
import java.time.*;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.Digits;

import com.yourcompany.untitled.calculators.NextNumberForYearCalculator;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import lombok.*;

@Entity @Getter @Setter
@View(members=
        "year, number, date," + // Los miembros para la cabecera en una línea
                "data {" + // Una pestaña 'data' para los datos principales del documento
                "customer;" +
                "details;" +
                "remarks" +
                "}"
)
abstract public class CommercialDocument extends Identifiable {

    @ElementCollection
    @ListProperties(
            "product.number, product.description, quantity, pricePerUnit, " +
                    "amount+[" +
                    "commercialDocument.vatPercentage," +
                    "commercialDocument.vat," +
                    "commercialDocument.totalAmount" +
                    "]"
    )
    private Collection<Detail> details;


    @Column(length=50)
    String description;

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


    @Digits(integer=2, fraction=0) // Para indicar su tamaño
    BigDecimal vatPercentage;

    @ReadOnly
    @Money
    @Calculation("sum(details.amount) * vatPercentage / 100")
    BigDecimal vat;

    @ReadOnly
    @Money
    @Calculation("sum(details.amount) + vat")
    BigDecimal totalAmount;


}
