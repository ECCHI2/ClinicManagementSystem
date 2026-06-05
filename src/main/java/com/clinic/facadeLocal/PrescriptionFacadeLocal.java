package com.clinic.facadeLocal;

import com.clinic.entity.Prescription;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface PrescriptionFacadeLocal {
    void create(Prescription prescription);
    void edit(Prescription prescription);
    void remove(Prescription prescription);
    Prescription find(Object id);
    List<Prescription> findAll();
}