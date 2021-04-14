package com.desafiospring.janin_tomas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDTO {
    private TicketDTO ticket;
    private StatusCodeDTO statusCode;
}
