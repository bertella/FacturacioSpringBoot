package com.underground.invoiceservice.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {

    private int quantity;
    private ProductDTO product;
}
