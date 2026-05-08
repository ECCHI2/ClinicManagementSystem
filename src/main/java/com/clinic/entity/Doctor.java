package com.clinic.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table (name = "doctor")
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique =  true, nullable = false)
    private String tcNo;

    private String firstName;
    private String lastName;
    private String specialty;
    private String phone;
    private String address;

    public Doctor() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getTcNo() { return tcNo; }
    public void setTcNo(String tcNo) { this.tcNo = tcNo; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o; // تم التصحيح: تحويل لطبيب وليس مريض
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}