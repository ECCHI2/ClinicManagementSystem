package com.clinic.facade;

import com.clinic.entity.Prescription;
import com.clinic.facadeLocal.PrescriptionFacadeLocal;
import jakarta.ejb.Stateless;

@Stateless
public class PrescriptionFacade extends AbstractFacade implements PrescriptionFacadeLocal {
    @Override
    public void create(Prescription prescription) { entityManager.persist(prescription); }
    @Override
    public void edit(Prescription prescription) { entityManager.merge(prescription); }
    @Override
    public void remove(Prescription prescription) { entityManager.remove(entityManager.merge(prescription)); }
    @Override
    public Prescription find(Object id) { return entityManager.find(Prescription.class, id); }
    @Override
    public java.util.List<Prescription> findAll() {
        return entityManager.createQuery("SELECT p FROM Prescription p", Prescription.class).getResultList();
    }
}