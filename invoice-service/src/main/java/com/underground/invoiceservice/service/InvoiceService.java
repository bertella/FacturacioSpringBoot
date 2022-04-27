package com.underground.invoiceservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.underground.invoiceservice.dto.*;
import com.underground.invoiceservice.exceptions.ResourceNotFoundException;
import com.underground.invoiceservice.model.ClientModel;
import com.underground.invoiceservice.model.InvoiceModel;
import com.underground.invoiceservice.model.ItemModel;
import com.underground.invoiceservice.model.ProductModel;
import com.underground.invoiceservice.repository.InvoiceRepository;
import com.underground.invoiceservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper mapper;


    public InvoiceModel getById(Integer id){
        return this.invoiceRepository.getById(id);
    }

    public InvoiceDTO create(OrderDTO order) throws ResourceNotFoundException {
        log.info("Entre a crear factura");
        if (order == null){
            throw new IllegalArgumentException("La orden de compra esta vacia o nula.Verificar");
        }
        log.info("La orden no es nula, OK.");
        /*
         * Creamos la factura y la lista de item que se va a guardar en la bd
         */
        InvoiceModel invoice = new InvoiceModel();
        List<ItemModel> invoiceDetail = new ArrayList<>();
        double totalMount = 0.0;

        for (OrderDetailDTO detail : order.getItems()){
            ItemModel item = new ItemModel();
            ProductModel productBD = mapper.convertValue(this.productService.findById(detail.getProduct().getId()), ProductModel.class);
            item.setInvoice(invoice);
            item.setProduct(productBD);

            log.info("PRODUCT ID : " + detail.getProduct().getId());
            log.info("STOCK PRODUCT BD : " + productBD.getStock());
            log.info("STOCK A DESCONTAR : " + detail.getQuantity());

            if (detail.getQuantity() > productBD.getStock()){
                throw new IllegalArgumentException("El Stock del producto : + " + detail.getProduct().getDescription() + " - CODE : "+ detail.getProduct().getCode() + " es insuficiente.");
            }

            item.setQuantity(detail.getQuantity());
            item.setUnitPrice(detail.getProduct().getPriceSell());
            item.setSubtotal(detail.getQuantity() * detail.getProduct().getPriceSell());
            invoiceDetail.add(item);
            totalMount += item.getSubtotal();
        }
        log.info("se verificaron la existencia y el stock de todos los productos ok");
        log.info("ITEM A GUARDAR : " + invoiceDetail);

        invoice.setItems(invoiceDetail);
        invoice.setTotal(totalMount);
        log.info("CLIENT ID : " + order.getClientId());
        log.info("CLIENTE RECIBIDO : " + clienteService.findById(order.getClientId()));
        invoice.setClient(mapper.convertValue(clienteService.findById(order.getClientId()),ClientModel.class));

        for (OrderDetailDTO detail : order.getItems()){
            productService.discountStock(detail.getProduct().getId(),detail.getQuantity());
        }
        log.info("Se realizo la resta de todos los productos ok.");

        InvoiceDTO invoiceDTO = mapper.convertValue(this.invoiceRepository.save(invoice), InvoiceDTO.class);
        this.itemRepository.saveAll(invoiceDetail);

        List<ItemDTO> itemDTOS = mapper.convertValue(invoiceDetail, new TypeReference<List<ItemDTO>>(){});

        log.info("ItemDTO " + itemDTOS);
        log.info("INVOICE DTO " + invoiceDTO);
        invoiceDTO.setItems(itemDTOS);

        return invoiceDTO;
    }
}
