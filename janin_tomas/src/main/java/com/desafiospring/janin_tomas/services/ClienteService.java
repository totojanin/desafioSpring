package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.CarritoDTO;
import com.desafiospring.janin_tomas.dtos.ClienteDTO;
import com.desafiospring.janin_tomas.exceptions.DNINotFoundException;
import com.desafiospring.janin_tomas.exceptions.ExistingClientException;
import com.desafiospring.janin_tomas.exceptions.MissingClientDataException;

import java.util.List;

public interface ClienteService {
    boolean addCliente(ClienteDTO cliente) throws ExistingClientException, MissingClientDataException;
    List<ClienteDTO> getClientes();
    List<ClienteDTO> findClienteByProvincia(String provincia);
    CarritoDTO findCartByCliente(String dni) throws DNINotFoundException;
}
