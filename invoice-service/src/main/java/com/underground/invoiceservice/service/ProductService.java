package com.underground.invoiceservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.underground.invoiceservice.dto.ProductDTO;
import com.underground.invoiceservice.exceptions.ResourceAlreadyExistsException;
import com.underground.invoiceservice.exceptions.ResourceNotFoundException;
import com.underground.invoiceservice.model.ProductModel;
import com.underground.invoiceservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Edu Terror Gimeno.
 */

@Service
@Slf4j
public class ProductService {

    private static final String INVALID_ID_MSG = "El id brindado no es valido. Verificar";
    private static final String PRODUCT_NOT_FOUND_MSG = "El producto con el id brindado no existe.Verificar";
    private static final String STOCK_ERROR_MSG = "El stock a descontar es negativo o 0, o la cantidad de productos en existenncia es menor al stock brindado. Stock Actual : ";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper mapper;

    public ProductDTO findById(Integer id) throws ResourceNotFoundException {
        if (id <= 0){
            throw new IllegalArgumentException(INVALID_ID_MSG);
        }

        ProductModel product = this.productRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MSG));

        return mapper.convertValue(product, ProductDTO.class);
    }

    public List<ProductDTO> findAll(){
        return this.productRepository.findAll().stream()
                .map(product -> ProductDTO.builder()
                                .id(product.getId())
                                .code(product.getCode())
                                .description(product.getDescription())
                                .priceBuy(product.getPriceBuy())
                                .priceSell(product.getPriceSell())
                                .stock(product.getStock())
                                .status(product.getStatus())
                                .createdAt(product.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public ProductDTO create(ProductDTO newProduct) throws ResourceAlreadyExistsException {
        if (StringUtils.isBlank(newProduct.getCode())){
            throw new IllegalArgumentException("El codigo del producto no puede ser vacio o null. Verificar");
        }

        Optional<ProductModel> productBD = this.productRepository.findByCode(newProduct.getCode());
        if (productBD.isPresent()){
            throw new ResourceAlreadyExistsException("Ya existe un producto con el codigo " + newProduct.getCode() +" . Verificar");
        }

        this.validatedProduct(newProduct);

        ProductModel productModel = mapper.convertValue(newProduct,ProductModel.class);
        productModel = this.productRepository.save(productModel);
        newProduct = mapper.convertValue(productModel, ProductDTO.class);
        return newProduct;
    }

    public ProductDTO updateProduct(Integer id, ProductDTO updateProduct) throws ResourceNotFoundException {
        if (id <= 0){
            throw new IllegalArgumentException(INVALID_ID_MSG);
        }

        ProductModel productBD = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MSG));

        this.validatedProduct(updateProduct);

        productBD.setCode(updateProduct.getCode());
        productBD.setDescription(updateProduct.getDescription());
        productBD.setPriceBuy(updateProduct.getPriceBuy());
        productBD.setPriceSell(updateProduct.getPriceSell());
        productBD.setStock(updateProduct.getStock());

        return mapper.convertValue(this.productRepository.save(productBD), ProductDTO.class);
    }

    public String deleteById(Integer id) throws ResourceNotFoundException {
        if (id <= 0) {
            throw new IllegalArgumentException(INVALID_ID_MSG);
        }

        ProductModel product = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MSG));

        product.setStatus("Inactive");
        this.productRepository.save(product); // se aplica baja logica.
        return "El producto con el ID " + id + " fue eliminado.";
    }

    public ProductDTO discountStock(Integer id, int stockToDiscount) throws ResourceNotFoundException {
        if (id <= 0){
            throw new IllegalArgumentException(INVALID_ID_MSG);
        }

        ProductModel productModel = this.productRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND_MSG));

        boolean stockValid = ((stockToDiscount > 0) && (productModel.getStock() >= stockToDiscount));
        if (!stockValid){
            throw new IllegalArgumentException(STOCK_ERROR_MSG+productModel.getStock());
        }

        productModel.setStock(productModel.getStock() - stockToDiscount);
        return mapper.convertValue(this.productRepository.save(productModel), ProductDTO.class);
    }


    private void validatedProduct(ProductDTO updateProduct){
        if (StringUtils.isBlank(updateProduct.getCode())){
            throw new IllegalArgumentException("El codigo del producto no puede ser vacio o null. Verificar");
        }

        if (StringUtils.isBlank(updateProduct.getDescription())){
            throw new IllegalArgumentException("La descripcion del producto no puede ser vacia o nula. Verificar");
        }

        if (updateProduct.getPriceBuy() <= 0.0){
            throw new IllegalArgumentException("El precio compra del producto no puede ser negativo o cero, nada es gratis. Verificar");
        }

        if (updateProduct.getPriceSell() <= 0.0){
            throw new IllegalArgumentException("El precio venta del producto no puede ser negativo o cero, nada es gratis. Verificar");
        }

        if (updateProduct.getStock() < 0){
            throw new IllegalArgumentException("El stock del producto no puede ser negativo. Verificar");
        }
    }
}
