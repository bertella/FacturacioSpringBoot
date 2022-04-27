package com.underground.invoiceservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    private Integer clientId;
    private List<OrderDetailDTO> items;
}
