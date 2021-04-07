package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.CarritoDTO;
import com.desafiospring.janin_tomas.dtos.ClienteDTO;
import com.desafiospring.janin_tomas.exceptions.DNINotFoundException;
import com.desafiospring.janin_tomas.exceptions.ExistingClientException;
import com.desafiospring.janin_tomas.exceptions.MissingClientDataException;
import com.desafiospring.janin_tomas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean addCliente(ClienteDTO cliente) throws ExistingClientException, MissingClientDataException {
        return clienteRepository.addCliente(cliente);
    }

    @Override
    public List<ClienteDTO> getClientes() {
        return clienteRepository.getClientes();
    }

    @Override
    public List<ClienteDTO> findClienteByProvincia(String provincia) {
        return clienteRepository.findClienteByProvincia(provincia);
    }

    @Override
    public CarritoDTO findCartByCliente(String dni) throws DNINotFoundException {
        return clienteRepository.findCartByCliente(dni);
    }
}
