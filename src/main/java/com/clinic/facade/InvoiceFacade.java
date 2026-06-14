package com.clinic.facade;

import com.clinic.entity.Invoice;
import com.clinic.facadeLocal.InvoiceFacadeLocal;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class InvoiceFacade extends AbstractFacade implements InvoiceFacadeLocal {
    @Override
    public void create(Invoice invoice) { entityManager.persist(invoice); }
    @Override
    public void edit(Invoice invoice) { entityManager.merge(invoice); }
    @Override
    public void remove(Invoice invoice) { entityManager.remove(entityManager.merge(invoice)); }
    @Override
    public Invoice find(Object id) { return entityManager.find(Invoice.class, id); }
    @Override
    public List<Invoice> findAll() {
        return entityManager.createQuery("SELECT i FROM Invoice i", Invoice.class).getResultList();
    }
}