package com.yourcompany.untitled.model;

import javax.persistence.*;
import lombok.*;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.View;

@Entity @Getter @Setter
@View( extendsView="super.DEFAULT", // La vista por defecto
        members="invoice { invoice } "
)
@View( name="NoCustomerNoInvoice", // Una vista llamada NoCustomerNoInvoice
        members=                       // que no incluye cliente ni factura.
                "year, number, date;" +    // Ideal para ser usada desde Invoice
                        "details;" +
                        "remarks"
)
public class Order extends CommercialDocument {

    @ManyToOne
    @ReferenceView("NoCustomerNoOrders") // Esta vista se usa para visualizar invoice
    private Invoice invoice;
}
