package com.desafiospring.janin_tomas.controllers;

import com.desafiospring.janin_tomas.dtos.*;
import com.desafiospring.janin_tomas.exceptions.*;
import com.desafiospring.janin_tomas.services.ArticuloService;

import com.desafiospring.janin_tomas.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArticulosController {
    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/articles")
    public ResponseEntity<List<ArticuloDTO>> articles(@RequestParam(value = "productId", required = false) Long productId, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "freeShipping", required = false) String freeShipping, @RequestParam(value = "productName", required = false) String productName, @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "order", required = false) Integer order) throws MaxFiltersException, ProductIdNotFoundException, CategoryNotFoundException, ShippingNotFoundException, InvalidShippingException, ProductNameNotFoundException, BrandNotFoundException {
        List<ArticuloDTO> articulos = articuloService.findArticuloByFilters(productId, category, freeShipping, productName, brand);

        articulos = articuloService.orderArticuloBy(articulos, order);

        return new ResponseEntity<List<ArticuloDTO>>(articulos, HttpStatus.OK);
    }

    @PostMapping("/purchase-request")
    public ResponseEntity<PurchaseResponseDTO> purchaseRequest(@RequestBody PurchaseRequestDTO purchaseRequest) throws ProductIdNotFoundException {
        PurchaseResponseDTO purchaseResponse = purchaseService.calculatePurchase(purchaseRequest.getArticles());

        return new ResponseEntity<PurchaseResponseDTO>(purchaseResponse, HttpStatus.OK);
    }

    @ExceptionHandler(MaxFiltersException.class)
    public ResponseEntity exceptionHandler(MaxFiltersException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductIdNotFoundException.class)
    public ResponseEntity exceptionHandler(ProductIdNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.NOT_FOUND);
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
