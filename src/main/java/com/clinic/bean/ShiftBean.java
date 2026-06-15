package com.clinic.bean;

import com.clinic.entity.Shift;
import com.clinic.entity.Doctor;
import com.clinic.facadeLocal.ShiftFacadeLocal;
import com.clinic.facadeLocal.DoctorFacadeLocal;
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
public class ShiftBean implements Serializable {

    private Shift shift = new Shift();
    private List<Shift> shifts;
    private Long doctorId;

    @EJB
    private ShiftFacadeLocal shiftFacade;
    @EJB
    private DoctorFacadeLocal doctorFacade;

    @PostConstruct
    public void init() {
        loadShifts();
    }

    public void loadShifts() {
        this.shifts = shiftFacade.findAll();
    }

    public List<Doctor> getAllDoctors() {
        return doctorFacade.findAll();
    }

    public void save() {
        try {
            if (doctorId == null) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Lütfen bir doktor seçin!");
                return;
            }
            shift.setDoctor(doctorFacade.find(doctorId));

            if (shift.getId() == null) {
                shiftFacade.create(shift);
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Nöbet kaydı eklendi.");
            } else {
                shiftFacade.edit(shift);
                addMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Nöbet kaydı güncellendi.");
            }
            shift = new Shift();
            doctorId = null;
            loadShifts();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "İşlem başarısız!");
        }
    }

    public void delete(Shift s) {
        try {
            shiftFacade.remove(s);
            loadShifts();
            addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Nöbet silindi.");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Silme başarısız!");
        }
    }

    public void prepareEdit(Shift s) {
        this.shift = s;
        this.doctorId = s.getDoctor().getId();
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }
    public List<Shift> getShifts() { return shifts; }
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
}