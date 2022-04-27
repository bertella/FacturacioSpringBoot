package com.underground.invoiceservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "invoices")
public class InvoiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientModel client;

    @OneToMany(mappedBy = "invoice")
    private List<ItemModel> items;

    private double total;

    private void loadDate(){
        this.createdAt = LocalDateTime.now();
    }

}
