package com.clinic.facadeLocal;

import com.clinic.entity.Medicine;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface MedicineFacadeLocal {
    void create(Medicine medicine);
    void edit(Medicine medicine);
    void remove(Medicine medicine);
    Medicine find(Object id);
    List<Medicine> findAll();
}