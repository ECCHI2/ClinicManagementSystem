package com.clinic.facade;

import com.clinic.entity.Medicine;
import com.clinic.facadeLocal.MedicineFacadeLocal;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class MedicineFacade extends AbstractFacade implements MedicineFacadeLocal {
    @Override
    public void create(Medicine medicine) { entityManager.persist(medicine); }
    @Override
    public void edit(Medicine medicine) { entityManager.merge(medicine); }
    @Override
    public void remove(Medicine medicine) { entityManager.remove(entityManager.merge(medicine)); }
    @Override
    public Medicine find(Object id) { return entityManager.find(Medicine.class, id); }
    @Override
    public List<Medicine> findAll() {
        return entityManager.createQuery("SELECT m FROM Medicine m", Medicine.class).getResultList();
    }
}