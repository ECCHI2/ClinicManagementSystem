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
    private List<Patient> patients; // تعريف القائمة مرة واحدة فقط

    // استدعاء البيانات عند تحميل الصفحة لأول مرة
    @PostConstruct
    public void init() {
        loadPatients();
    }

    public void loadPatients() {
        this.patients = patientFacade.findAll();
    }

    public void save() {
        try {
            // 1. فحص التكرار باستخدام الـ Facade (أسرع وأضمن)
            if (patient.getId() == null && patientFacade.existsByTc(patient.getTcNo())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Bu TC numarası zaten kayıtlı!"));
                return;
            }

            if (patient.getId() == null) {
                patientFacade.create(patient);
            } else {
                patientFacade.edit(patient);
            }

            patient = new Patient(); // تصفير الكائن
            loadPatients(); // تحديث القائمة

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Hasta sisteme kaydedildi."));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Patient p) {
        patientFacade.remove(p);
        loadPatients(); // تحديث القائمة بعد الحذف
    }

    public void prepareEdit(Patient p) {
        this.patient = p;
    }

    // --- Getters & Setters (بنفس مسمياتك الأصلية) ---

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Patient> getPatients() {
        if (patients == null) {
            loadPatients();
        }
        return patients;
    }

    // أبقيت لك هذه المسميات إذا كنت تستخدمها في أماكن أخرى
    public Patient getNewPatient() {
        return patient;
    }
    public void setNewPatient(Patient newPatient) {
        this.patient = newPatient;
    }
}