package com.clinic.bean;

import com.clinic.entity.Doctor;
import com.clinic.entity.Patient;
import com.clinic.entity.Prescription;
import com.clinic.entity.MedicalRecord;
import com.clinic.facadeLocal.PrescriptionFacadeLocal;
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
public class PrescriptionBean implements Serializable {

    private Prescription prescription;
    private List<Prescription> prescriptions;
    private Long medicalRecordId;

    @EJB
    private PrescriptionFacadeLocal prescriptionFacade;

    @EJB
    private MedicalRecordFacadeLocal medicalRecordFacade;

    @PostConstruct
    public void init() {
        resetPrescription();
        loadPrescriptions();
    }

    public void loadPrescriptions() {
        prescriptions = prescriptionFacade.findAll();
    }

    // دالة جديدة لجلب كل القيود الطبية وعرضها بالقائمة المنسدلة
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordFacade.findAll();
    }

    public void save() {
        try {
            if (medicalRecordId == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Lütfen bir tıbbi kayıt seçin!");
                return;
            }

            // 1. جلب القيد الطبي
            MedicalRecord mr = medicalRecordFacade.find(medicalRecordId);
            if (mr == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Seçilen Tıbbi Kayıt bulunamadı!");
                return;
            }

            // 2. ربط الراشيتة بالقيد، وبالمريض، وبالدكتور أوتوماتيكياً!
            prescription.setMedicalRecord(mr);
            prescription.setPatient(mr.getPatient());
            prescription.setDoctor(mr.getDoctor());

            // 3. الحفظ أو التعديل
            if (prescription.getId() == null) {
                prescriptionFacade.create(prescription);
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Reçete başarıyla eklendi.");
            } else {
                prescriptionFacade.edit(prescription);
                addMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Reçete başarıyla güncellendi.");
            }

            resetPrescription();
            loadPrescriptions();

        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "İşlem başarısız: " + e.getMessage());
        }
    }

    public void delete(Prescription p) {
        try {
            prescriptionFacade.remove(p);
            loadPrescriptions();
            addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Reçete başarıyla silindi.");
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
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu reçete silinemez! Çünkü sistemde ona bağlı başka işlemler bulunmaktadır.");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Silme işlemi başarısız oldu.");
            }
        }
    }

    public void prepareEdit(Prescription p) {
        this.prescription = p;
        if (p != null && p.getMedicalRecord() != null) {
            this.medicalRecordId = p.getMedicalRecord().getId();
        } else {
            this.medicalRecordId = null;
        }
    }

    private void resetPrescription() {
        prescription = new Prescription();
        medicalRecordId = null;
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters and Setters
    public Prescription getPrescription() { return prescription; }
    public void setPrescription(Prescription prescription) { this.prescription = prescription; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public Long getMedicalRecordId() { return medicalRecordId; }
    public void setMedicalRecordId(Long medicalRecordId) { this.medicalRecordId = medicalRecordId; }
}