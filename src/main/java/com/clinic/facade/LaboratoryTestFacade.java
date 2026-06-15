package com.clinic.facade;

import com.clinic.entity.LaboratoryTest;
import com.clinic.facadeLocal.LaboratoryTestFacadeLocal;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class LaboratoryTestFacade extends AbstractFacade implements LaboratoryTestFacadeLocal {
    @Override
    public void create(LaboratoryTest test) { entityManager.persist(test); }
    @Override
    public void edit(LaboratoryTest test) { entityManager.merge(test); }
    @Override
    public void remove(LaboratoryTest test) { entityManager.remove(entityManager.merge(test)); }
    @Override
    public LaboratoryTest find(Object id) { return entityManager.find(LaboratoryTest.class, id); }
    @Override
    public List<LaboratoryTest> findAll() {
        return entityManager.createQuery("SELECT l FROM LaboratoryTest l", LaboratoryTest.class).getResultList();
    }
}