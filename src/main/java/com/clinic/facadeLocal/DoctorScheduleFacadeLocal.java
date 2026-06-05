package com.clinic.facadeLocal;

import com.clinic.entity.DoctorSchedule;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface DoctorScheduleFacadeLocal {
    void create(DoctorSchedule schedule);
    void edit(DoctorSchedule schedule);
    void remove(DoctorSchedule schedule);
    DoctorSchedule find(Object id);
    List<DoctorSchedule> findAll();
}