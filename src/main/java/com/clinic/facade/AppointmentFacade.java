package com.clinic.facade;

import com.clinic.entity.Appointment;
import com.clinic.facadeLocal.AppointmentFacadeLocal;
import jakarta.ejb.Stateless;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@Stateless
public class AppointmentFacade extends AbstractFacade implements AppointmentFacadeLocal {

    @Override
    public void create(Appointment appointment) {
        entityManager.persist(appointment);
    }

    @Override
    public void edit(Appointment appointment) {
        entityManager.merge(appointment);
    }

    @Override
    public void remove(Appointment appointment) {
        entityManager.remove(entityManager.merge(appointment));
    }

    @Override
    public Appointment find(Object id) {
        return entityManager.find(Appointment.class, id);
    }

    @Override
    public List<Appointment> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Appointment> cq = cb.createQuery(Appointment.class);
        Root<Appointment> root = cq.from(Appointment.class);
        // ترتيب تنازلي حسب التاريخ (DESC) مثلما طلب الدكتور
        cq.select(root).orderBy(cb.desc(root.get("appointmentDate")));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public long countAppointments() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Appointment.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }
}