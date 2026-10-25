package com.yourcompany.untitled.model;

import javax.persistence.*;
import lombok.*;
import org.openxava.annotations.CollectionView;
import org.openxava.annotations.View;

import java.util.Collection;

@Entity @Getter @Setter
@View( extendsView="super.DEFAULT", // La vista por defecto
        members="orders { orders }"
)
@View( name="NoCustomerNoOrders", // Una vista llamada NoCustomerNoOrders
        members=                      // que no incluye cliente ni pedidos
                "year, number, date;" +   // Ideal para usarse desde Order
                        "details;" +
                        "remarks"
)
public class Invoice extends CommercialDocument {

    @OneToMany(mappedBy="invoice")
    @CollectionView("NoCustomerNoInvoice") // Esta vista se usa para visualizar pedidos
    private Collection<Order> orders;

}
