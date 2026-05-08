package com.clinic.bean;

import com.clinic.facadeLocal.AppointmentFacadeLocal;
import com.clinic.facadeLocal.DoctorFacadeLocal;
import com.clinic.facadeLocal.PatientFacadeLocal;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {

    @EJB
    private DoctorFacadeLocal doctorFacade;
    @EJB
    private PatientFacadeLocal patientFacade;
    @EJB
    private AppointmentFacadeLocal appointmentFacade;

    // استخدام دوال العد المباشرة من الـ Facade
    public long getDoctorCount() {
        return doctorFacade.countDoctors();
    }

    public long getPatientCount() {
        return patientFacade.countPatients();
    }

    public long getAppointmentCount() {
        return appointmentFacade.countAppointments();
    }
}