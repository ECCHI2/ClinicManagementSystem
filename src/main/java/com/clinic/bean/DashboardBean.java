package com.clinic.bean;

import com.clinic.facadeLocal.AppointmentFacadeLocal;
import com.clinic.facadeLocal.DoctorFacadeLocal;
import com.clinic.facadeLocal.PatientFacadeLocal;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class DashboardBean {

    @EJB
    private DoctorFacadeLocal doctorFacade;

    @EJB
    private PatientFacadeLocal patientFacade;

    @EJB
    private AppointmentFacadeLocal appointmentFacade;

    private int totalDoctors;
    private int totalPatients;
    private int totalAppointments;

    @PostConstruct
    public void init() {
        totalDoctors = doctorFacade.findAll().size();
        totalPatients = patientFacade.findAll().size();
        totalAppointments = appointmentFacade.findAll().size();
    }

    public int getTotalDoctors() { return totalDoctors; }
    public int getTotalPatients() { return totalPatients; }
    public int getTotalAppointments() { return totalAppointments; }
}