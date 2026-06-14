package com.clinic.bean;

import com.clinic.entity.Patient;
import com.clinic.facadeLocal.PatientFacadeLocal;
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
public class PatientBean implements Serializable {

    @EJB
    private PatientFacadeLocal patientFacade;

    private Patient patient = new Patient();
    private List<Patient> patients;

    @PostConstruct
    public void init() {
        loadPatients();
    }

    public void loadPatients() {
        this.patients = patientFacade.findAll();
    }

    public void save() {
        try {
            if (patient.getId() == null && patientFacade.existsByTc(patient.getTcNo())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu TC numarası zaten kayıtlı!"));
                return;
            }

            if (patient.getId() == null) {
                patientFacade.create(patient);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Hasta sisteme başarıyla kaydedildi."));
            } else {
                patientFacade.edit(patient);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Hasta bilgileri güncellendi."));
            }

            patient = new Patient();
            loadPatients();

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Kaydetme sırasında bir sorun oluştu."));
        }
    }

    public void delete(Patient p) {
        try {
            patientFacade.remove(p);
            loadPatients();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Hasta başarıyla silindi."));

        } catch (Exception e) {
            // البحث داخل تفاصيل الخطأ عن سبب الرفض من قاعدة البيانات
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
                // إذا كان المريض مرتبط بجدول آخر
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu hasta silinemez! Çünkü sistemde ona ait tıbbi kayıtlar, reçeteler veya randevular bulunmaktadır."));
            } else {
                // إذا كان خطأ عام آخر
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Silme işlemi başarısız oldu."));
            }
        }
    }

    public void prepareEdit(Patient p) {
        this.patient = p;
    }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public List<Patient> getPatientList() {
        if (patients == null) {
            patients = patientFacade.findAll();
        }
        return patients;
    }

    public Patient getNewPatient() { return patient; }
    public void setNewPatient(Patient newPatient) { this.patient = newPatient; }
}