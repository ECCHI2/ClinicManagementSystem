package com.clinic.facade;

import com.clinic.entity.MedicalRecord;
import com.clinic.facadeLocal.MedicalRecordFacadeLocal;
import jakarta.ejb.Stateless;

@Stateless
public class MedicalRecordFacade extends AbstractFacade implements MedicalRecordFacadeLocal {
    @Override
    public void create(MedicalRecord record) { entityManager.persist(record); }
    @Override
    public void edit(MedicalRecord record) { entityManager.merge(record); }
    @Override
    public void remove(MedicalRecord record) { entityManager.remove(entityManager.merge(record)); }
    @Override
    public MedicalRecord find(Object id) { return entityManager.find(MedicalRecord.class, id); }
    @Override
    public java.util.List<MedicalRecord> findAll() {
        return entityManager.createQuery("SELECT m FROM MedicalRecord m", MedicalRecord.class).getResultList();
    }
}