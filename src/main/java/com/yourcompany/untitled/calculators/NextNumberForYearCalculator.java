package com.yourcompany.untitled.calculators;

import javax.persistence.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;
import lombok.*;

public class NextNumberForYearCalculator implements ICalculator { // Un calculador tiene que implementar ICalculator

    @Getter @Setter // Para que sea accesible públicamente
    int year; // Este valor se inyectará antes de calcular

    public Object calculate() throws Exception { // Hace el cálculo
        Query query = XPersistence.getManager() // Una consulta JPA
                .createQuery("select max(i.number) from Invoice i where i.year = :year"); // La consulta devuelve
        // el número de factura máximo del año indicado
        query.setParameter("year", year); // Ponemos el año inyectado como parámetro de la consulta
        Integer lastNumber = (Integer) query.getSingleResult();
        return lastNumber == null ? 1 : lastNumber + 1; // Devuelve el último número
        // de factura del año + 1 o 1 si no hay último número
    }

}
