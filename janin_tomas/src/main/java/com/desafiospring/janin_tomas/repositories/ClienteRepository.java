package com.desafiospring.janin_tomas.repositories;

import com.desafiospring.janin_tomas.dtos.ArticuloPurchaseDTO;
import com.desafiospring.janin_tomas.dtos.CarritoDTO;
import com.desafiospring.janin_tomas.dtos.ClienteDTO;
import com.desafiospring.janin_tomas.exceptions.DNINotFoundException;
import com.desafiospring.janin_tomas.exceptions.ExistingClientException;
import com.desafiospring.janin_tomas.exceptions.MissingClientDataException;

import java.util.List;

public interface ClienteRepository {
    boolean addCliente(ClienteDTO cliente) throws ExistingClientException, MissingClientDataException;
    List<ClienteDTO> getClientes();
    List<ClienteDTO> findClienteByProvincia(String provincia);
    ClienteDTO findClienteByDNI(String dni) throws DNINotFoundException;
    CarritoDTO findCartByCliente(String dni) throws DNINotFoundException;
    CarritoDTO addToCart(List<ArticuloPurchaseDTO> articulos, double total, String dni);
}
