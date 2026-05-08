package com.clinic.facadeLocal;

import com.clinic.entity.Appointment;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface AppointmentFacadeLocal {
    void create(Appointment appointment);
    void edit(Appointment appointment);
    void remove(Appointment appointment);
    Appointment find(Object id);
    List<Appointment> findAll();
    long countAppointments();
}