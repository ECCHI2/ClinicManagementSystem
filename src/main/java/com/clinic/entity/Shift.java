package com.clinic.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Shift implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Temporal(TemporalType.DATE)
    private Date shiftDate;

    private String shiftType;
    private String department;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public Date getShiftDate() { return shiftDate; }
    public void setShiftDate(Date shiftDate) { this.shiftDate = shiftDate; }
    public String getShiftType() { return shiftType; }
    public void setShiftType(String shiftType) { this.shiftType = shiftType; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}