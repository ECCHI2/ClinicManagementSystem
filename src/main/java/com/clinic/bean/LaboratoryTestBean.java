package com.clinic.bean;

import com.clinic.entity.LaboratoryTest;
import com.clinic.entity.MedicalRecord;
import com.clinic.facadeLocal.LaboratoryTestFacadeLocal;
import com.clinic.facadeLocal.MedicalRecordFacadeLocal;
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
public class LaboratoryTestBean implements Serializable {

    private LaboratoryTest labTest;
    private List<LaboratoryTest> labTests;
    private Long medicalRecordId;

    @EJB
    private LaboratoryTestFacadeLocal labTestFacade;

    @EJB
    private MedicalRecordFacadeLocal medicalRecordFacade;

    @PostConstruct
    public void init() {
        resetTest();
        loadTests();
    }

    public void loadTests() {
        this.labTests = labTestFacade.findAll();
    }

    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordFacade.findAll();
    }

    public void save() {
        try {
            if (medicalRecordId == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Lütfen bir tıbbi kayıt seçin!");
                return;
            }

            MedicalRecord mr = medicalRecordFacade.find(medicalRecordId);
            if (mr == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Seçilen tıbbi kayıt bulunamadı!");
                return;
            }

            labTest.setMedicalRecord(mr);

            if (labTest.getId() == null) {
                labTestFacade.create(labTest);
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Yeni tahlil talebi oluşturuldu.");
            } else {
                labTestFacade.edit(labTest);
                addMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Tahlil sonuçları başarıyla kaydedildi.");
            }

            resetTest();
            loadTests();

        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "İşlem başarısız oldu!");
        }
    }

    public void delete(LaboratoryTest test) {
        try {
            labTestFacade.remove(test);
            loadTests();
            addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Tahlil kaydı silindi.");
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
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu kayıt silinemez! Sisteme entegre başka işlemlerle bağlantılıdır.");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Silme işlemi başarısız oldu.");
            }
        }
    }

    public void prepareEdit(LaboratoryTest test) {
        this.labTest = test;
        if (test != null && test.getMedicalRecord() != null) {
            this.medicalRecordId = test.getMedicalRecord().getId();
        } else {
            this.medicalRecordId = null;
        }
    }

    private void resetTest() {
        labTest = new LaboratoryTest();
        labTest.setTestDate(new java.util.Date());
        labTest.setStatus("Bekliyor");
        medicalRecordId = null;
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters and Setters
    public LaboratoryTest getLabTest() { return labTest; }
    public void setLabTest(LaboratoryTest labTest) { this.labTest = labTest; }
    public List<LaboratoryTest> getLabTests() { return labTests; }
    public Long getMedicalRecordId() { return medicalRecordId; }
    public void setMedicalRecordId(Long medicalRecordId) { this.medicalRecordId = medicalRecordId; }
}