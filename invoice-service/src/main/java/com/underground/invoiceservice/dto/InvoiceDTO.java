package com.underground.invoiceservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDTO {

    private Integer id;
    private ClientDTO client;
    private LocalDateTime createdAt;
    private double total;
    private List<ItemDTO> items;
}
