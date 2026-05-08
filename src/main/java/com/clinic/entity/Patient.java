package com.clinic.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String phone;
    private String tcNo;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    public String getAge() {
        if (this.birthDate == null) return "-";

        java.util.Calendar birth = java.util.Calendar.getInstance();
        birth.setTime(this.birthDate);
        java.util.Calendar today = java.util.Calendar.getInstance();

        int age = today.get(java.util.Calendar.YEAR) - birth.get(java.util.Calendar.YEAR);

        // تصحيح إذا كان يوم الميلاد لم يأتِ بعد في السنة الحالية
        if (today.get(java.util.Calendar.DAY_OF_YEAR) < birth.get(java.util.Calendar.DAY_OF_YEAR)) {
            age--;
        }

        // إذا كان العمر أقل من 1 (حديث ولادة)
        if (age < 1) {
            return "Yeni Doğan (0)";
        }

        return String.valueOf(age); // إرجاع العمر كـ نص
    }

    public Patient() {}

    public String getTcNo() {return tcNo;}
    public void setTcNo(String tcNo) {this.tcNo = tcNo;}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}