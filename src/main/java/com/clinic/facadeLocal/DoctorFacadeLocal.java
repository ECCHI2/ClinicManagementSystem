package com.clinic.facadeLocal;

import com.clinic.entity.Doctor;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface DoctorFacadeLocal {
    void create(Doctor doctor);
    void edit(Doctor doctor);
    void remove(Doctor doctor);
    Doctor find(Object id);
    List<Doctor> findAll();
    long countDoctors();

    boolean existsByTc(String tc);
}