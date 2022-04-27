package com.underground.invoiceservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.underground.invoiceservice.commons.Util;
import com.underground.invoiceservice.dto.ClientDTO;
import com.underground.invoiceservice.exceptions.ResourceAlreadyExistsException;
import com.underground.invoiceservice.exceptions.ResourceNotFoundException;
import com.underground.invoiceservice.model.ClientModel;
import com.underground.invoiceservice.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ClienteService {

    @Autowired
    private ClientRepository clienteRepository;

    @Autowired
    private ObjectMapper mapper;

    public ClientDTO findById(Integer id) throws ResourceNotFoundException {
        log.info("ID CON EL QUE INGRESAMOS A CLIENTE ID " + id);
        if (id <= 0) {
            throw new IllegalArgumentException("El id brindado no es valido. Verificar");
        }

        ClientModel clientModel = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El Cliente con el ID " + id + " no existe . Verificar"));

        return this.mapper.convertValue(clientModel,ClientDTO.class);
    }

    public List<ClientModel> findAll() {
        return this.clienteRepository.findAll();
    }

    public ClientDTO create(ClientDTO newClient) throws ResourceAlreadyExistsException {
        Optional<ClientModel> clientOp = this.clienteRepository.findByDocNumber(newClient.getDocNumber());

        if (clientOp.isPresent()){
            throw new ResourceAlreadyExistsException("El dni " + newClient.getDocNumber() + " ya se encuentra registrado en el sistema. Verificar");
        }

        if (!Util.isOnlyLetters(newClient.getName())) {
            throw new IllegalArgumentException("El nombre no es valido, verifique que no contenga digitos o caracteres extraños");
        }

        if (!Util.isOnlyLetters(newClient.getLastname())) {
            throw new IllegalArgumentException("El apellido no es valido, verifique que no contenga digitos o caracteres extraños");
        }

        if (!Util.isOnlyNumbers(newClient.getDocNumber())) {
            throw new IllegalArgumentException("El numero de documento no es valido, verifique que no contenga caracteres extraños o que la cantidad de numeros sea mayor a 7");
        }

        ClientModel clientModel = mapper.convertValue(newClient, ClientModel.class);
        clientModel = this.clienteRepository.save(clientModel);
        newClient.setId(clientModel.getId());

        return newClient;
    }

    public ClientDTO update(Integer id, ClientDTO clientUpdated) throws ResourceNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("El id brindado no es valido. Verificar");
        }

        ClientModel client = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El Cliente con el ID " + id + " no existe . Verificar"));

        if (!Util.isOnlyLetters(clientUpdated.getName())) {
            throw new IllegalArgumentException("El nombre no es valido, verifique que no contenga digitos o caracteres extraños");
        }

        if (!Util.isOnlyLetters(clientUpdated.getLastname())) {
            throw new IllegalArgumentException("El apellido no es valido, verifique que no contenga digitos o caracteres extraños");
        }

        if (Util.isOnlyNumbers(clientUpdated.getDocNumber())) {
            String docNumberClean = Util.getOnlyNumbers(clientUpdated.getDocNumber());
            boolean docNumberLongValid = ((docNumberClean.length() >= 7) && (docNumberClean.length() <12));
            if (!docNumberLongValid){
                throw new IllegalArgumentException("El dni debe ser de al menos 7 digitos y maximo 11. Verificar");
            }
        } else {
            throw new IllegalArgumentException("El numero de documento no es valido, verifique que no contenga caracteres extraños o que la cantidad de numeros sea mayor a 7");
        }

        client.setName(clientUpdated.getName());
        client.setLastname(clientUpdated.getLastname());
        client.setDocNumber(Util.getOnlyNumbers(clientUpdated.getDocNumber()));
        client.setDateOfBirth(clientUpdated.getDateOfBirth());

        client = this.clienteRepository.save(client);

        return mapper.convertValue(client,ClientDTO.class);

    }

    public String deleteById(Integer id) throws ResourceNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException("El id brindado no es valido. Verificar");
        }

        ClientModel client = this.clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El Cliente con el ID " + id + " no existe . Verificar"));

        if (client != null) {
            client.setStatus("Inactivo");
            this.clienteRepository.save(client);
            return "Cliente eliminado";
        } else {
            return "El cliente no pudo ser eliminado o no existe.";
        }
    }

}