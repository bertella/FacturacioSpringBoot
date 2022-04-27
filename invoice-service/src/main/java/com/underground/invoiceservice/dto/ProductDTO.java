package com.underground.invoiceservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    private Integer id;
    private String code;
    private String description;
    private double priceBuy;
    private double priceSell;
    private int stock;
    private LocalDateTime createdAt;
    private String status;
}
