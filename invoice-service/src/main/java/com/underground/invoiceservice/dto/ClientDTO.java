package com.underground.invoiceservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO {

    private Integer id;
    private String name;
    private String lastname;
    private String docNumber;
    private LocalDate dateOfBirth;
}
