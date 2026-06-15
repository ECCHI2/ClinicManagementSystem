package com.clinic.facade;

import com.clinic.entity.Doctor;
import com.clinic.facadeLocal.DoctorFacadeLocal;
import jakarta.ejb.Stateless;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@Stateless
public class DoctorFacade extends AbstractFacade implements DoctorFacadeLocal {

    @Override
    public void create(Doctor doctor) {
        entityManager.persist(doctor);
    }

    @Override
    public void edit(Doctor doctor) {
        entityManager.merge(doctor);
    }

    @Override
    public void remove(Doctor doctor) {
        entityManager.remove(entityManager.merge(doctor));
        entityManager.flush();
    }

    @Override
    public Doctor find(Object id) {
        return entityManager.find(Doctor.class, id);
    }

    @Override
    public List<Doctor> findAll() {
        // استخدام CriteriaBuilder بدلاً من SELECT
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Doctor> cq = cb.createQuery(Doctor.class);
        Root<Doctor> root = cq.from(Doctor.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public long countDoctors() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Doctor> root = cq.from(Doctor.class);
        cq.select(cb.count(root));
        return entityManager.createQuery(cq).getSingleResult();
    }
    @Override
    public boolean existsByTc(String tc) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Doctor> root = cq.from(Doctor.class);

        cq.select(cb.count(root)).where(cb.equal(root.get("tcNo"), tc));
        return entityManager.createQuery(cq).getSingleResult() > 0;
    }
    @Override
    public boolean hasAppointments(Long doctorId) {
        try {
            Long count = (Long) entityManager.createQuery("SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = :docId")
                    .setParameter("docId", doctorId)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}