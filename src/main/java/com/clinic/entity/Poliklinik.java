package com.clinic.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Poliklinik implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clinicName; // اسم العيادة

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }
}