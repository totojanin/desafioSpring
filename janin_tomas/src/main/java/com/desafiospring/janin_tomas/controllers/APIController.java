package com.desafiospring.janin_tomas.controllers;

import com.desafiospring.janin_tomas.dtos.*;
import com.desafiospring.janin_tomas.exceptions.*;
import com.desafiospring.janin_tomas.services.ArticuloService;

import com.desafiospring.janin_tomas.services.ClienteService;
import com.desafiospring.janin_tomas.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class APIController {
    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/articles")
    public ResponseEntity<List<ArticuloDTO>> articles(@RequestParam(value = "productId", required = false) Long productId, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "freeShipping", required = false) String freeShipping, @RequestParam(value = "productName", required = false) String productName, @RequestParam(value = "brand", required = false) String brand, @RequestParam(value = "order", required = false) Integer order) throws MaxFiltersException, ProductIdNotFoundException, CategoryNotFoundException, ShippingNotFoundException, InvalidShippingException, ProductNameNotFoundException, BrandNotFoundException, IOException {
        List<ArticuloDTO> articulos = articuloService.findArticuloByFilters(productId, category, freeShipping, productName, brand, order);

        return new ResponseEntity<>(articulos, HttpStatus.OK);
    }

    @PostMapping("/purchase-request")
    public ResponseEntity<PurchaseResponseDTO> purchaseRequest(@RequestBody PurchaseRequestDTO purchaseRequest) throws ProductIdNotFoundException {
        PurchaseResponseDTO purchaseResponse = purchaseService.calculatePurchase(purchaseRequest.getArticles());

        return new ResponseEntity<>(purchaseResponse, HttpStatus.OK);
    }

    // Metodo para hacer un purchase request pero agregando al carrito del cliente
    @PostMapping("/purchase-request-cart/{dni}")
    public ResponseEntity<PurchaseResponseDTO> purchaseRequestCart(@RequestBody PurchaseRequestDTO purchaseRequest, @PathVariable String dni) throws DNINotFoundException {
        PurchaseResponseDTO purchaseResponse = purchaseService.calculatePurchaseCart(purchaseRequest.getArticles(), dni);

        return new ResponseEntity<>(purchaseResponse, HttpStatus.OK);
    }

    // Metodo para agregar un nuevo cliente
    @PostMapping("/addClient")
    public ResponseEntity<String> addClient(@RequestBody ClienteDTO cliente) throws MissingClientDataException, ExistingClientException {
        boolean success = clienteService.addCliente(cliente);

        if (success)
            return new ResponseEntity<>("El cliente fue agregado correctamente", HttpStatus.OK);
        else
            return new ResponseEntity<>("El cliente no pudo ser agregado", HttpStatus.BAD_REQUEST);
    }

    // Metodo para obtener todos los clientes
    @GetMapping("/clients")
    public ResponseEntity<List<ClienteDTO>> clients() {
        List<ClienteDTO> clientes = clienteService.getClientes();

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Metodo para obtener los clientes filtrados por provincia
    @GetMapping("/clientsByProvincia/{provincia}")
    public ResponseEntity<List<ClienteDTO>> clientsByProvincia(@PathVariable String provincia) {
        List<ClienteDTO> clientes = clienteService.findClienteByProvincia(provincia);

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // Metodo para obtener el carrito de un cliente
    @GetMapping("/cartByCliente/{dni}")
    public ResponseEntity<CarritoDTO> cartByCliente(@PathVariable String dni) throws DNINotFoundException {
        CarritoDTO carrito = clienteService.findCartByCliente(dni);

        return new ResponseEntity<>(carrito, HttpStatus.OK);
    }

    @ExceptionHandler(MaxFiltersException.class)
    public ResponseEntity exceptionHandler(MaxFiltersException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductIdNotFoundException.class)
    public ResponseEntity exceptionHandler(ProductIdNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity exceptionHandler(CategoryNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ShippingNotFoundException.class)
    public ResponseEntity exceptionHandler(ShippingNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidShippingException.class)
    public ResponseEntity exceptionHandler(InvalidShippingException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNameNotFoundException.class)
    public ResponseEntity exceptionHandler(ProductNameNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity exceptionHandler(BrandNotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OverQuantityException.class)
    public ResponseEntity exceptionHandler(OverQuantityException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistingClientException.class)
    public ResponseEntity exceptionHandler(ExistingClientException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingClientDataException.class)
    public ResponseEntity exceptionHandler(MissingClientDataException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DNINotFoundException.class)
    public ResponseEntity exceptionHandler(DNINotFoundException e) {
        return new ResponseEntity(new ErrorDTO(e.getTitle(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
