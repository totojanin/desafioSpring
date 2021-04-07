package com.desafiospring.janin_tomas.repositories;

import com.desafiospring.janin_tomas.dtos.ArticuloPurchaseDTO;
import com.desafiospring.janin_tomas.dtos.CarritoDTO;
import com.desafiospring.janin_tomas.dtos.ClienteDTO;
import com.desafiospring.janin_tomas.exceptions.DNINotFoundException;
import com.desafiospring.janin_tomas.exceptions.ExistingClientException;
import com.desafiospring.janin_tomas.exceptions.MissingClientDataException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Data
public class ClienteRepositoryImpl implements ClienteRepository {
    private AtomicLong clienteId = new AtomicLong(0);

    private List<ClienteDTO> clientes = new ArrayList<>();

    private Map<String, CarritoDTO> carritos = new HashMap<>();

    @Autowired
    private ArticuloRepository articuloRepository;

    @Override
    public boolean addCliente(ClienteDTO cliente) throws ExistingClientException, MissingClientDataException {
        if (cliente.getNombre().isEmpty() || cliente.getNombre() == null)
            throw new MissingClientDataException("Debe completar el nombre del cliente");

        if (cliente.getDni().isEmpty() || cliente.getDni() == null)
            throw new MissingClientDataException("Debe completar el DNI del cliente");

        if (cliente.getProvincia().isEmpty() || cliente.getProvincia() == null)
            throw new MissingClientDataException("Debe completar la provincia del cliente");

        Optional<ClienteDTO> c = clientes.stream()
                       .filter(cl -> cl.getDni().equals(cliente.getDni()))
                       .findFirst();

        if (c.isPresent())
            throw new ExistingClientException("Ya existe un cliente con el mismo DNI");

        cliente.setClienteId(clienteId.incrementAndGet());

        return clientes.add(cliente);
    }

    @Override
    public List<ClienteDTO> getClientes() {
        return clientes;
    }

    @Override
    public List<ClienteDTO> findClienteByProvincia(String provincia) {
        return clientes.stream()
                .filter(c -> c.getProvincia().equalsIgnoreCase(provincia))
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO findClienteByDNI(String dni) throws DNINotFoundException {
        Optional<ClienteDTO> c = clientes.stream()
                .filter(cl -> cl.getDni().equals(dni))
                .findFirst();

        if (c.isPresent())
            return c.get();
        else
            throw new DNINotFoundException("No se encontró ninguna persona con el DNI ingresado");
    }

    @Override
    public CarritoDTO findCartByCliente(String dni) throws DNINotFoundException {
        if (carritos.containsKey(dni))
            return carritos.get(dni);
        else
            throw new DNINotFoundException("No se encontró ninguna persona con el DNI ingresado");
    }

    @Override
    public CarritoDTO addToCart(List<ArticuloPurchaseDTO> articulos, double total, String dni) {
        try {
            for (ArticuloPurchaseDTO a : articulos) {
                articuloRepository.descontarStock(a);
            }

            CarritoDTO carrito = findCartByCliente(dni);

            carrito.getArticulos().addAll(articulos);
            carrito.setTotal(carrito.getTotal() + total);

            carritos.put(dni, carrito);

            return carrito;
        }
        catch (DNINotFoundException e) {
            CarritoDTO carrito = new CarritoDTO(articulos, total);

            carritos.put(dni, carrito);

            return carrito;
        }
    }
}
