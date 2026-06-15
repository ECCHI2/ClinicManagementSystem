package com.clinic.facadeLocal;

import com.clinic.entity.Shift;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface ShiftFacadeLocal {
    void create(Shift shift);
    void edit(Shift shift);
    void remove(Shift shift);
    Shift find(Object id);
    List<Shift> findAll();
}