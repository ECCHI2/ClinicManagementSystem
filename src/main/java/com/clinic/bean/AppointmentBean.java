package com.clinic.bean;

import com.clinic.entity.Appointment;
import com.clinic.enums.AppointmentStatus;
import com.clinic.entity.Doctor;
import com.clinic.entity.Patient;
import com.clinic.facadeLocal.AppointmentFacadeLocal;
import com.clinic.facadeLocal.DoctorFacadeLocal;
import com.clinic.facadeLocal.PatientFacadeLocal;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import jakarta.annotation.PostConstruct;

@Named
@ViewScoped
public class AppointmentBean implements Serializable {

    @EJB
    private AppointmentFacadeLocal appointmentFacade;
    @EJB
    private DoctorFacadeLocal doctorFacade;
    @EJB
    private PatientFacadeLocal patientFacade;

    private Appointment appointment = new Appointment();
    private List<Appointment> appointmentList;

    public List<Doctor> getAllDoctors() {
        return doctorFacade.findAll();
    }

    public List<Patient> getAllPatients() {
        return patientFacade.findAll();
    }

    public void loadAppointments() {
        this.appointmentList = appointmentFacade.findAll();
    }

    @PostConstruct
    public void init() {
        if (appointment == null) {
            appointment = new Appointment();
        }
        loadAppointments();
    }

    public void save() {
        try {
            if (appointment.getId() == null) {
                appointment.setStatus(AppointmentStatus.PENDING);
                appointmentFacade.create(appointment);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Yeni randevu başarıyla eklendi."));
            } else {
                appointmentFacade.edit(appointment);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Randevu başarıyla güncellendi."));
            }
            appointment = new Appointment();
            loadAppointments();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "İşlem başarısız oldu."));
        }
    }

    public void delete(Appointment a) {
        try {
            appointmentFacade.remove(a);
            loadAppointments();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Randevu başarıyla silindi."));
        } catch (Exception e) {
            // فحص الخطأ القادم من قاعدة البيانات
            Throwable t = e.getCause();
            boolean isConstraintViolation = false;

            while (t != null) {
                if (t.getMessage() != null && (t.getMessage().toLowerCase().contains("constraint") || t.getMessage().toLowerCase().contains("foreign key"))) {
                    isConstraintViolation = true;
                    break;
                }
                t = t.getCause();
            }

            if (isConstraintViolation) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu randevu silinemez! Çünkü sistemde ona bağlı fatura veya işlemler bulunmaktadır."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Silme işlemi başarısız oldu."));
            }
        }
    }

    public void prepareEdit(Appointment a) {
        this.appointment = appointmentFacade.find(a.getId());
    }

    public java.util.Date getCurrentDate() {
        return new java.util.Date();
    }

    public AppointmentStatus[] getStatuses() {
        return AppointmentStatus.values();
    }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public List<Appointment> getAppointmentList() {
        if (appointmentList == null) {
            appointmentList = appointmentFacade.findAll();
        }
        return appointmentList;
    }
}