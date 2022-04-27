package com.underground.invoiceservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {

    private Integer id;
    private int quantity;
    private ProductDTO product;
    private double unitPrice;
    private double subtotal;
}
