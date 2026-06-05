package com.clinic.bean;

import com.clinic.entity.Prescription;
import com.clinic.entity.MedicalRecord;
import com.clinic.facadeLocal.PrescriptionFacadeLocal;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PrescriptionBean implements Serializable {

    private Prescription prescription = new Prescription();
    private List<Prescription> prescriptions;

    @EJB
    private PrescriptionFacadeLocal prescriptionFacade;

    @PostConstruct
    public void init() {
        resetPrescription();
        loadPrescriptions();
    }

    public void loadPrescriptions() {
        prescriptions = prescriptionFacade.findAll();
    }

    public void save() {
        try {
            if (prescription.getId() == null) {
                prescriptionFacade.create(prescription);
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Reçete eklendi.");
            } else {
                prescriptionFacade.edit(prescription);
                addMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Reçete güncellendi.");
            }
            resetPrescription();
            loadPrescriptions();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "İşlem başarısız!");
        }
    }

    public void delete(Prescription p) {
        try {
            prescriptionFacade.remove(p);
            loadPrescriptions();
            addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Reçete silindi.");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Silme başarısız!");
        }
    }

    public void prepareEdit(Prescription p) {
        this.prescription = p;
    }

    private void resetPrescription() {
        prescription = new Prescription();
        prescription.setMedicalRecord(new MedicalRecord());
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters and Setters
    public Prescription getPrescription() { return prescription; }
    public void setPrescription(Prescription prescription) { this.prescription = prescription; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
}