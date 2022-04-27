package com.underground.invoiceservice.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class ProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;
    private String description;
    @Column(name = "price_buy")
    private double priceBuy;
    @Column(name = "price_sell")
    private double priceSell;
    private int stock;
    private String status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    private void loadData(){
        this.status = "Active";
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void updateData(){
        this.lastUpdated = LocalDateTime.now();
    }
}
