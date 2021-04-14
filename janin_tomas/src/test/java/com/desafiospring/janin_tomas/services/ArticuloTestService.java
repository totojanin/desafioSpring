package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.dtos.ArticuloPurchaseDTO;
import org.springframework.stereotype.Service;

@Service
public class ArticuloTestService {
    public static ArticuloDTO getArticulo1() {
        return new ArticuloDTO(1L, "Remera", "Indumentaria", "Nike", 1000, 2, true, "**");
    }

    public static ArticuloDTO getArticulo2() {
        return new ArticuloDTO(2L, "Martillo", "Herramientas", "Black & Decker", 3000, 1, true, "****");
    }

    public static ArticuloDTO getArticulo3() {
        return new ArticuloDTO(3L, "Notebook", "Tecnologia", "Dell", 25000, 3, true, "***");
    }

    public static ArticuloDTO getArticulo4() {
        return new ArticuloDTO(4L, "Moto G9", "Celulares", "Motorola", 28000, 4, true, "*****");
    }

    public static ArticuloDTO getArticulo5() {
        return new ArticuloDTO(5L, "Destornillador", "Herramientas", "Black & Decker", 2000, 4, true, "*");
    }

    public static ArticuloDTO getArticulo6() {
        return new ArticuloDTO(6L, "J12", "Celulares", "LG", 50000, 1, false, "**");
    }

    public static ArticuloDTO getArticulo7() {
        return new ArticuloDTO(7L, "Television", "Tecnologia", "Black & Decker", 2000, 4, false, "****");
    }

    public static ArticuloDTO getArticulo8() {
        return new ArticuloDTO(8L, "Llave", "Herramientas", "Black & Decker", 500, 2, true, "***");
    }

    public static ArticuloPurchaseDTO getArticuloPurchase1() {
        return new ArticuloPurchaseDTO(1L, "Desmalezadora", "Makita", 1);
    }

    public static ArticuloPurchaseDTO getArticuloPurchase2() {
        return new ArticuloPurchaseDTO(2L, "Moto G8", "Motorola", 3);
    }

    public static ArticuloPurchaseDTO getArticuloPurchase3() {
        return new ArticuloPurchaseDTO(3L, "Camisa", "Zara", 2);
    }

    public static ArticuloPurchaseDTO getArticuloPurchase4() {
        return new ArticuloPurchaseDTO(4L, "Zapatillas Deportivas", "Nike", 2);
    }
}
