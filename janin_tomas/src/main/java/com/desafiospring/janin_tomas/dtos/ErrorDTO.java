package com.desafiospring.janin_tomas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private String nombre;
    private String descripcion;
}
