package com.desafiospring.janin_tomas.controllers;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.dtos.ErrorDTO;
import com.desafiospring.janin_tomas.dtos.PurchaseResponseDTO;
import com.desafiospring.janin_tomas.exceptions.*;
import com.desafiospring.janin_tomas.services.ArticulosService;
import jdk.jfr.internal.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArticulosController {
    @Autowired
    private ArticulosService articulosService;

    @GetMapping("/articles")
    public ResponseEntity<List<ArticuloDTO>> articles(@RequestParam(value = "category", required = false) String category, @RequestParam(value = "freeShipping", required = false) String freeShipping, @RequestParam(value = "productName", required = false) String productName, @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "order", required = false) Integer order) throws MaxFiltersException, CategoryNotFoundException, ShippingNotFoundException, InvalidShippingException, ProductNameNotFoundException, BrandNotFoundException {
        List<ArticuloDTO> articulos = articulosService.findArticuloByFilters(category, freeShipping, productName, brand);

        articulos = articulosService.orderArticuloBy(articulos, order);

        return new ResponseEntity<List<ArticuloDTO>>(articulos, HttpStatus.OK);
    }

    @PostMapping("/purchase-request")
    public ResponseEntity<PurchaseResponseDTO> purchaseRequest() {
        PurchaseResponseDTO purchaseResponse = new PurchaseResponseDTO();

        return new ResponseEntity<PurchaseResponseDTO>(purchaseResponse, HttpStatus.OK);
    }

    @ExceptionHandler(MaxFiltersException.class)
    public ResponseEntity exceptionHandler(MaxFiltersException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity exceptionHandler(CategoryNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShippingNotFoundException.class)
    public ResponseEntity exceptionHandler(ShippingNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidShippingException.class)
    public ResponseEntity exceptionHandler(InvalidShippingException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNameNotFoundException.class)
    public ResponseEntity exceptionHandler(ProductNameNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity exceptionHandler(BrandNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
