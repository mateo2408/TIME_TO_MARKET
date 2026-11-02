package com.yourcompany.untitled.model;

import javax.persistence.*;

import com.yourcompany.untitled.calculators.PricePerUnitCalculator;
import lombok.*;
import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.Depends;
import org.openxava.annotations.Money;
import org.openxava.annotations.PropertyValue;

import java.math.BigDecimal;

@Embeddable @Getter @Setter
public class Detail {

    int quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    Product product;

    @Money
    @Depends("pricePerUnit, quantity") // pricePerUnit en vez de product.number
    public BigDecimal getAmount() {
        if (pricePerUnit == null) return BigDecimal.ZERO; // pricePerUnit en vez de product y product.getPrice()
        return new BigDecimal(quantity).multiply(pricePerUnit); // pricePerUnit en vez de product.getPrice()
    }


    @DefaultValueCalculator(
            value= PricePerUnitCalculator.class, // Esta clase calcula el valor inicial
            properties=@PropertyValue(
                    name="productNumber", // La propiedad productNumber del calculador...
                    from="product.number") // ...se llena con el valor de product.number del detalle
    )
    @Money
    BigDecimal pricePerUnit; // Una propiedad persistente convencional


}
