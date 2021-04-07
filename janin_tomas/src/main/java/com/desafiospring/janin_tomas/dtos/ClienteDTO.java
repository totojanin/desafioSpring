package com.desafiospring.janin_tomas.dtos;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long clienteId;
    private String nombre;
    private String dni;
    private String provincia;
}
