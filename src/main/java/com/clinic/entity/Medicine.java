package com.clinic.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Medicine implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // اسم الدواء
    private String manufacturer; // الشركة المصنعة
    private Integer stockQuantity; // الكمية المتوفرة بالمستودع

    @Temporal(TemporalType.DATE)
    private Date expirationDate; // تاريخ الصلاحية

    private String description; // وصف أو استخدامات الدواء

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Date getExpirationDate() { return expirationDate; }
    public void setExpirationDate(Date expirationDate) { this.expirationDate = expirationDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}