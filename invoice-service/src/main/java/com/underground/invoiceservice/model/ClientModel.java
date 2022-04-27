package com.underground.invoiceservice.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "clients")
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastname;
    @Column(name = "doc_number")
    private String docNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String status;


    @PrePersist
    private void loadCreatedAt(){
        this.createdAt = LocalDateTime.now();
        this.status = "Activo";
    }
}
