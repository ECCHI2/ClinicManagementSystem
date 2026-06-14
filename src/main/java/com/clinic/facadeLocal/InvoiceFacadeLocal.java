package com.clinic.facadeLocal;

import com.clinic.entity.Invoice;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface InvoiceFacadeLocal {
    void create(Invoice invoice);
    void edit(Invoice invoice);
    void remove(Invoice invoice);
    Invoice find(Object id);
    List<Invoice> findAll();
}