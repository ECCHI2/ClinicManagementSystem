package com.clinic.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentDate;

    private String note;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PENDING; // القيمة الافتراضية عند الحجز
    @Column(name = "status")
    // Getter and Setter
    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Appointment() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public Date getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}