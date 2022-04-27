package com.underground.invoiceservice.controller;

import com.underground.invoiceservice.dto.ClientDTO;
import com.underground.invoiceservice.exceptions.ResourceAlreadyExistsException;
import com.underground.invoiceservice.exceptions.ResourceNotFoundException;
import com.underground.invoiceservice.model.ClientModel;
import com.underground.invoiceservice.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cliente")
public class ClientController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping(path = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) throws ResourceNotFoundException {
        return new ResponseEntity<>(this.clienteService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<ClientModel>> findAll(){
        return new ResponseEntity<>(this.clienteService.findAll(), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ClientDTO> uppdated(@PathVariable Integer id,
                                              @RequestBody ClientDTO newClient)throws ResourceNotFoundException {
        return new ResponseEntity<>(this.clienteService.update(id, newClient), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO newClient) throws ResourceAlreadyExistsException {
        return new ResponseEntity<>(this.clienteService.create(newClient), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        return new ResponseEntity<>(this.clienteService.deleteById(id), HttpStatus.OK);
    }
}