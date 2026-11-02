package com.yourcompany.untitled.calculators;

import org.openxava.calculators.*;
import com.yourcompany.untitled.model.*;
import lombok.*;

import static org.openxava.jpa.XPersistence.*; // Para usar getManager()

public class PricePerUnitCalculator implements ICalculator {

    @Getter @Setter
    int productNumber; // Contiene el número de producto cuando se llama a calculate()

    public Object calculate() throws Exception {
        Product product = getManager() // getManager() de XPersistence
                .find(Product.class, productNumber); // Busca el producto
        return product.getPrice(); // Retorna su precio
    }

}

