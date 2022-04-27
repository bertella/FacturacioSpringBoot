package com.underground.Invoiceservice.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StandardResponse {

    private int statusCode;
    private String status;
    private String msg;
}
