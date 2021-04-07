package com.desafiospring.janin_tomas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CarritoDTO {
    private List<ArticuloPurchaseDTO> articulos;
    private double total;
}
