package com.clinic.entity;

import com.clinic.entity.Doctor;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
public class DoctorSchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    private String dayOfWeek;

    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Temporal(TemporalType.TIME)
    private Date endTime;

    // Getters and Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}