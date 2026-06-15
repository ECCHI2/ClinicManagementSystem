package com.clinic.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class LaboratoryTest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medical_record_id", nullable = false)
    private MedicalRecord medicalRecord;

    private String testName;

    @Temporal(TemporalType.DATE)
    private Date testDate;

    private String testResult;
    private String status;
    private String labNotes;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public MedicalRecord getMedicalRecord() { return medicalRecord; }
    public void setMedicalRecord(MedicalRecord medicalRecord) { this.medicalRecord = medicalRecord; }

    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }

    public Date getTestDate() { return testDate; }
    public void setTestDate(Date testDate) { this.testDate = testDate; }

    public String getTestResult() { return testResult; }
    public void setTestResult(String testResult) { this.testResult = testResult; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLabNotes() { return labNotes; }
    public void setLabNotes(String labNotes) { this.labNotes = labNotes; }
}