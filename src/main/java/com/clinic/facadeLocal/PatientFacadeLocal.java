package com.clinic.facadeLocal;

import com.clinic.entity.Patient;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface PatientFacadeLocal {
    void create(Patient patient);
    void edit(Patient patient);
    void remove(Patient patient);
    Patient find(Object id);
    List<Patient> findAll();
    long countPatients();
    boolean existsByTc(String tc); // الدالة التي سألك عنها الدكتور للتحقق من رقم الهوية
}