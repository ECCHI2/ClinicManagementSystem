package com.clinic.bean;

import com.clinic.entity.MedicalRecord;
import com.clinic.entity.Doctor;
import com.clinic.entity.Patient;
import com.clinic.facadeLocal.MedicalRecordFacadeLocal;
import com.clinic.facadeLocal.DoctorFacadeLocal;
import com.clinic.facadeLocal.PatientFacadeLocal;
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
public class MedicalRecordBean implements Serializable {

    private MedicalRecord record = new MedicalRecord();
    private List<MedicalRecord> records;

    @EJB
    private MedicalRecordFacadeLocal recordFacade;

    @EJB
    private DoctorFacadeLocal doctorFacade;

    @EJB
    private PatientFacadeLocal patientFacade;

    @PostConstruct
    public void init() {
        record = new MedicalRecord();
        record.setPatient(new Patient());
        record.setDoctor(new Doctor());
    }

    public void loadRecords() {
        records = recordFacade.findAll();
    }

    public void save() {
        try {
            if (record.getDoctor() != null && record.getDoctor().getId() != null) {
                record.setDoctor(doctorFacade.find(record.getDoctor().getId()));
            }
            if (record.getPatient() != null && record.getPatient().getId() != null) {
                record.setPatient(patientFacade.find(record.getPatient().getId()));
            }

            if (this.record.getId() == null) {
                recordFacade.create(record);
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Yeni tıbbi kayıt başarıyla oluşturuldu.");
            } else {
                MedicalRecord existing = recordFacade.find(this.record.getId());
                if (existing != null) {
                    existing.setDiagnosis(record.getDiagnosis());
                    existing.setClinicalNotes(record.getClinicalNotes());
                    existing.setDoctor(record.getDoctor());
                    existing.setPatient(record.getPatient());
                    recordFacade.edit(existing);
                    addMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Tıbbi kayıt başarıyla güncellendi.");
                }
            }

            resetRecord();
            loadRecords();

        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Kayıt işlemi başarısız oldu!");
        }
    }

    public void delete(Long id) {
        if (id == null) {
            return;
        }
        try {
            MedicalRecord toDelete = recordFacade.find(id);
            if (toDelete != null) {
                recordFacade.remove(toDelete);
                loadRecords();
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Tıbbi kayıt başarıyla silindi.");
            }
        } catch (Exception e) {
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
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu kayıt silinemez! Çünkü sistemde bu kayda bağlı reçeteler bulunmaktadır.");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Silme işlemi başarısız oldu!");
            }
        }
    }

    public void prepareEdit(Long id) {
        if (id != null) {
            this.record = recordFacade.find(id);
            if (this.record.getDoctor() == null) this.record.setDoctor(new Doctor());
            if (this.record.getPatient() == null) this.record.setPatient(new Patient());
        }
    }

    private void resetRecord() {
        record = new MedicalRecord();
        record.setDoctor(new Doctor());
        record.setPatient(new Patient());
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public MedicalRecord getRecord() { return record; }
    public void setRecord(MedicalRecord record) { this.record = record; }

    public List<MedicalRecord> getRecords() {
        if (records == null) {
            loadRecords();
        }
        return records;
    }
}