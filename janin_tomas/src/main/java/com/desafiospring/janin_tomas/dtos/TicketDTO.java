package com.desafiospring.janin_tomas.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TicketDTO {
    private long id;
    private List<ArticuloDTO> articulos;
    private double total;

}
