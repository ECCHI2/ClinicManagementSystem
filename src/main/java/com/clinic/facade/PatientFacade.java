package com.clinic.facade;

import com.clinic.entity.Patient;
import com.clinic.facadeLocal.PatientFacadeLocal;
import jakarta.ejb.Stateless;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

@Stateless
public class PatientFacade extends AbstractFacade implements PatientFacadeLocal {

    @Override
    public void create(Patient patient) {
        entityManager.persist(patient);
    }

    @Override
    public void edit(Patient patient) {
        entityManager.merge(patient);
    }

    @Override
    public void remove(Patient patient) {
        entityManager.remove(entityManager.merge(patient));
    }

    @Override
    public Patient find(Object id) {
        return entityManager.find(Patient.class, id);
    }

    @Override
    public List<Patient> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
        Root<Patient> root = cq.from(Patient.class);
        cq.select(root);
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public long countPatients() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(Patient.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public boolean existsByTc(String tc) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Patient> root = cq.from(Patient.class);
        cq.select(cb.count(root)).where(cb.equal(root.get("tcNo"), tc));
        return entityManager.createQuery(cq).getSingleResult() > 0;
    }
}