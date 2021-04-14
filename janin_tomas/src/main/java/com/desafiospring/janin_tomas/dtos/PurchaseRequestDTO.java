package com.desafiospring.janin_tomas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequestDTO {
    private List<ArticuloPurchaseDTO> articles;
}
