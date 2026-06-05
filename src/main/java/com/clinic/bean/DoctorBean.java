package com.clinic.bean;

import com.clinic.entity.Appointment;
import com.clinic.entity.Doctor;
import com.clinic.facadeLocal.AppointmentFacadeLocal;
import com.clinic.facadeLocal.DoctorFacadeLocal;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named
@ViewScoped
public class DoctorBean implements Serializable {

    private Doctor doctor = new Doctor();
    private List<Doctor> doctors;

    @EJB
    private DoctorFacadeLocal doctorFacade;

    // الحل النووي: استدعينا واجهة المواعيد لنفحص المواعيد بأنفسنا
    @EJB
    private AppointmentFacadeLocal appointmentFacade;

    public void loadDoctors() {
        this.doctors = doctorFacade.findAll();
    }

    @PostConstruct
    public void init() {
        loadDoctors();
    }

    public void save() {
        try {
            if (doctor.getId() == null && doctorFacade.existsByTc(doctor.getTcNo())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Bu TC Kimlik numarası ile kayıtlı bir doktor zaten var!"));
                return;
            }

            if (doctor.getId() == null) {
                doctorFacade.create(doctor);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Doktor başarıyla kaydedildi."));
            } else {
                doctorFacade.edit(doctor);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Doktor bilgileri başarıyla güncellendi."));
            }

            doctor = new Doctor();
            loadDoctors();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Sistem Hatası", "İşlem sırasında bir hata oluştu."));
            e.printStackTrace();
        }
    }

    public void delete(Doctor doctor) {
        // الفحص الاستباقي اليدوي القاطع
        boolean hasAppointments = false;
        List<Appointment> allAppointments = appointmentFacade.findAll();
        for (Appointment a : allAppointments) {
            if (a.getDoctor() != null && a.getDoctor().getId().equals(doctor.getId())) {
                hasAppointments = true;
                break;
            }
        }

        if (hasAppointments) {
            // نبعت الرسالة مباشرة بدون Flash Scope
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu doktor silinemez! Çünkü sistemde ona ait randevular bulunmaktadır."));
            return;
        }

        try {
            doctorFacade.remove(doctor);
            doctors = doctorFacade.findAll();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Doktor başarıyla silindi."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Sistem Hatası", "Silme işlemi sırasında bir hata oluştu."));
        }
    }

    public void prepareEdit(Doctor d) {
        this.doctor = d;
    }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public List<Doctor> getDoctorList() {
        if (doctors == null) {
            loadDoctors();
        }
        return doctors;
    }
    public void setDoctorList(List<Doctor> doctorList) {
        this.doctors = doctorList;
    }
}