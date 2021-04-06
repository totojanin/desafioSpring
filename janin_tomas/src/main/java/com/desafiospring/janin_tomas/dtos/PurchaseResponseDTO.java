package com.desafiospring.janin_tomas.dtos;

import lombok.Data;

@Data
public class PurchaseResponseDTO {
    private TicketDTO ticket;
    private StatusCodeDTO statusCode;
}
