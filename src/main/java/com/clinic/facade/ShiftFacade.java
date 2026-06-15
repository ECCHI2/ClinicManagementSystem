package com.clinic.facade;

import com.clinic.entity.Shift;
import com.clinic.facadeLocal.ShiftFacadeLocal;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ShiftFacade extends AbstractFacade implements ShiftFacadeLocal {
    @Override
    public void create(Shift shift) { entityManager.persist(shift); }
    @Override
    public void edit(Shift shift) { entityManager.merge(shift); }
    @Override
    public void remove(Shift shift) { entityManager.remove(entityManager.merge(shift)); }
    @Override
    public Shift find(Object id) { return entityManager.find(Shift.class, id); }
    @Override
    public List<Shift> findAll() {
        return entityManager.createQuery("SELECT s FROM Shift s", Shift.class).getResultList();
    }
}