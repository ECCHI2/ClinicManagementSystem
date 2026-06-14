package com.clinic.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Invoice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = true)
    private Appointment appointment;

    private Double amount; // المبلغ (Tutar)

    @Temporal(TemporalType.DATE)
    private Date invoiceDate; // تاريخ الفاتورة

    private String status; // حالة الدفع (Ödendi / Bekliyor)
    private String description; // تفاصيل الخدمات (Açıklama)

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Date getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(Date invoiceDate) { this.invoiceDate = invoiceDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}