package com.clinic.entity;

public enum AppointmentStatus {
    PENDING("Beklemede"),
    APPROVED("Onaylandı"),
    COMPLETED("Tamamlandı"),
    CANCELLED("İptal Edildi");

    private final String label;

    AppointmentStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}