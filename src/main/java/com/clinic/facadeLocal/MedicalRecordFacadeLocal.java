package com.clinic.facadeLocal;

import com.clinic.entity.MedicalRecord;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface MedicalRecordFacadeLocal {
    void create(MedicalRecord record);
    void edit(MedicalRecord record);
    void remove(MedicalRecord record);
    MedicalRecord find(Object id);
    List<MedicalRecord> findAll();
}