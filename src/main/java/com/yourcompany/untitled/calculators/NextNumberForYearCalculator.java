package com.yourcompany.untitled.calculators;

import javax.persistence.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;
import lombok.*;

public class NextNumberForYearCalculator implements ICalculator { // Un calculador tiene que implementar ICalculator

    @Getter @Setter // Para que sea accesible públicamente
    int year; // Este valor se inyectará antes de calcular

    public Object calculate() throws Exception {
        Query query = XPersistence.getManager().createQuery(
                "select max(i.number) from " +
                        "CommercialDocument i " + // CommercialDocument en vez de Invoice
                        "where i.year = :year");
        query.setParameter("year", year);
        Integer lastNumber = (Integer) query.getSingleResult();
        return lastNumber == null?1:lastNumber + 1;
    }


}
