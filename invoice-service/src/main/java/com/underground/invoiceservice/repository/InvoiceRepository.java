package com.underground.invoiceservice.repository;

import com.underground.invoiceservice.model.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceModel,Integer> {
}
