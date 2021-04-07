package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.ArticuloPurchaseDTO;
import com.desafiospring.janin_tomas.dtos.PurchaseResponseDTO;
import com.desafiospring.janin_tomas.exceptions.DNINotFoundException;
import com.desafiospring.janin_tomas.exceptions.ProductIdNotFoundException;

import java.util.List;

public interface PurchaseService {
    PurchaseResponseDTO calculatePurchase(List<ArticuloPurchaseDTO> articulos);
    PurchaseResponseDTO calculatePurchaseCart(List<ArticuloPurchaseDTO> articulos, String dni) throws DNINotFoundException;
}
