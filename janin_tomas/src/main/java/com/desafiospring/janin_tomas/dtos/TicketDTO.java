package com.desafiospring.janin_tomas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TicketDTO {
    private long id;
    private List<ArticuloPurchaseDTO> articles;
    private double total;

}
