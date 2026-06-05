package com.clinic.facade;

import com.clinic.entity.DoctorSchedule;
import com.clinic.facadeLocal.DoctorScheduleFacadeLocal;
import jakarta.ejb.Stateless;

@Stateless
public class DoctorScheduleFacade extends AbstractFacade implements DoctorScheduleFacadeLocal {
    @Override
    public void create(DoctorSchedule schedule) { entityManager.persist(schedule); }
    @Override
    public void edit(DoctorSchedule schedule) { entityManager.merge(schedule); }
    @Override
    public void remove(DoctorSchedule schedule) { entityManager.remove(entityManager.merge(schedule)); }
    @Override
    public DoctorSchedule find(Object id) { return entityManager.find(DoctorSchedule.class, id); }
    @Override
    public java.util.List<DoctorSchedule> findAll() {
        return entityManager.createQuery("SELECT d FROM DoctorSchedule d", DoctorSchedule.class).getResultList();
    }
}