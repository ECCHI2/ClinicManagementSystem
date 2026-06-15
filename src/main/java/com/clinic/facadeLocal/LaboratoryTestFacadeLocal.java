package com.clinic.facadeLocal;

import com.clinic.entity.LaboratoryTest;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface LaboratoryTestFacadeLocal {
    void create(LaboratoryTest test);
    void edit(LaboratoryTest test);
    void remove(LaboratoryTest test);
    LaboratoryTest find(Object id);
    List<LaboratoryTest> findAll();
}