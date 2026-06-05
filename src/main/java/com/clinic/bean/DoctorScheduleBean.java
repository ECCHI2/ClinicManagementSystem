package com.clinic.bean;

import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.Doctor;
import com.clinic.facadeLocal.DoctorScheduleFacadeLocal;
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
public class DoctorScheduleBean implements Serializable {

    private DoctorSchedule schedule = new DoctorSchedule();
    private List<DoctorSchedule> schedules;

    @EJB
    private DoctorScheduleFacadeLocal scheduleFacade;

    @PostConstruct
    public void init() {
        resetSchedule();
        loadSchedules();
    }

    public void loadSchedules() {
        schedules = scheduleFacade.findAll();
    }

    public void save() {
        try {
            if (schedule.getId() == null) {
                scheduleFacade.create(schedule);
                addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Çalışma saati eklendi.");
            } else {
                scheduleFacade.edit(schedule);
                addMessage(FacesMessage.SEVERITY_INFO, "Güncellendi", "Çalışma saati güncellendi.");
            }
            resetSchedule();
            loadSchedules();
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "İşlem başarısız!");
        }
    }

    public void delete(DoctorSchedule s) {
        try {
            scheduleFacade.remove(s);
            loadSchedules();
            addMessage(FacesMessage.SEVERITY_INFO, "Başarılı", "Saat silindi.");
        } catch (Exception e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Hata", "Silme başarısız!");
        }
    }

    public void prepareEdit(DoctorSchedule s) {
        this.schedule = s;
    }

    private void resetSchedule() {
        schedule = new DoctorSchedule();
        schedule.setDoctor(new Doctor());
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    // Getters and Setters
    public DoctorSchedule getSchedule() { return schedule; }
    public void setSchedule(DoctorSchedule schedule) { this.schedule = schedule; }
    public List<DoctorSchedule> getSchedules() { return schedules; }
}