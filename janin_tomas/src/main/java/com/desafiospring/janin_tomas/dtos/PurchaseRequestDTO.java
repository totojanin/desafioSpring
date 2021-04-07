package com.desafiospring.janin_tomas.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseRequestDTO {
    private List<ArticuloPurchaseDTO> articles;
}
