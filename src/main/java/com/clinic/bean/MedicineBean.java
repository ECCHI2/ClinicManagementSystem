package com.clinic.bean;

import com.clinic.entity.Medicine;
import com.clinic.facadeLocal.MedicineFacadeLocal;
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
public class MedicineBean implements Serializable {

    private Medicine medicine;
    private List<Medicine> medicines;

    @EJB
    private MedicineFacadeLocal medicineFacade;

    @PostConstruct
    public void init() {
        resetMedicine();
        loadMedicines();
    }

    public void loadMedicines() {
        this.medicines = medicineFacade.findAll();
    }

    public void save() {
        try {
            if (medicine.getId() == null) {
                medicineFacade.create(medicine);
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Yeni ilaç stoka eklendi.");
            } else {
                medicineFacade.edit(medicine);
                addMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "İlaç bilgileri başarıyla güncellendi.");
            }
            resetMedicine();
            loadMedicines();
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "İşlem başarısız oldu.");
        }
    }

    public void delete(Medicine m) {
        try {
            medicineFacade.remove(m);
            loadMedicines();
            addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "İlaç stoktan silindi.");
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
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Bu ilaç silinemez! Çünkü sistemde yazılmış reçetelerde kullanılmaktadır.");
            } else {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata!", "Silme işlemi başarısız oldu.");
            }
        }
    }

    public void prepareEdit(Medicine m) {
        this.medicine = m;
    }

    private void resetMedicine() {
        medicine = new Medicine();
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters and Setters
    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public List<Medicine> getMedicines() { return medicines; }
}