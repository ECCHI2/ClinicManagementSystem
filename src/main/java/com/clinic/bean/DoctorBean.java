package com.clinic.bean;

import com.clinic.entity.Doctor;
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

    public void loadDoctors() {
        this.doctors = doctorFacade.findAll();
    }
    @EJB
    private DoctorFacadeLocal doctorFacade;

    @PostConstruct
    public void init() {
        loadDoctors();
    }

    public void save() {
        try {
            // Yeni kayıt eklenirken TC kimlik numarası kontrolü
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

            doctor = new Doctor(); // Formu temizle
            loadDoctors(); // Listeyi yenile

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL, "Sistem Hatası", "İşlem sırasında bir hata oluştu."));
            e.printStackTrace();
        }
    }

    public List<Doctor> getDoctorList() {
        if (doctors == null) {
            loadDoctors();
        }
        return doctors;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctors = doctorList;
    }

    public void delete(Doctor d) {
        doctorFacade.remove(d);
        loadDoctors(); // اسحب القائمة فوراً بعد الحذف
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Silindi", "Doktor kaydı silindi."));
    }

    public void prepareEdit(Doctor d) {
        // هنا نقوم بضبط الكائن 'doctor' ليكون هو الكائن المختار من الجدول
        this.doctor = d;
    }

    // Getters & Setters
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public List<Doctor> getDoctors() {
        if (doctors == null) {
            doctors = doctorFacade.findAll();
        }
        return doctors;
    }
}