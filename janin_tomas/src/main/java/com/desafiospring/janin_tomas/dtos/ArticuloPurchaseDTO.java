package com.desafiospring.janin_tomas.dtos;

import lombok.Data;

@Data
public class ArticuloPurchaseDTO {
    private Long productId;
    private String name;
    private String brand;
    private int quantity;
}
