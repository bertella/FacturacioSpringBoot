package com.underground.invoiceservice.controller;

import com.underground.invoiceservice.dto.ProductDTO;
import com.underground.invoiceservice.exceptions.ResourceAlreadyExistsException;
import com.underground.invoiceservice.exceptions.ResourceNotFoundException;
import com.underground.invoiceservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) throws ResourceNotFoundException {
        return new ResponseEntity<>(this.productService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<ProductDTO>> findAll(){
        return new ResponseEntity<>(this.productService.findAll(),HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO newProduct) throws ResourceAlreadyExistsException {
        return new ResponseEntity<>(this.productService.create(newProduct), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id,
                                             @RequestBody ProductDTO newProduct) throws ResourceNotFoundException {
        return new ResponseEntity<>(this.productService.updateProduct(id,newProduct), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws ResourceNotFoundException {
        return new ResponseEntity<>(this.productService.deleteById(id), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}/stock")
    public ResponseEntity<ProductDTO> discountStock(@PathVariable Integer id,
                                                    @RequestBody Map<String, Integer> body) throws ResourceNotFoundException {
        return new ResponseEntity<>(this.productService.discountStock(id,body.get("stock")),HttpStatus.OK);

    }
}
