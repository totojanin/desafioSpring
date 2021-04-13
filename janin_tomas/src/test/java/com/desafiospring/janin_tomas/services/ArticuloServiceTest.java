package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import org.springframework.stereotype.Service;

@Service
public class ArticuloServiceTest {
    public static ArticuloDTO getArticulo1() {
        return new ArticuloDTO(1L, "Remera", "Indumentaria", "Nike", 1000, 2, true, "**");
    }

    public static ArticuloDTO getArticulo2() {
        return new ArticuloDTO(2L, "Martillo", "Herramientas", "Black & Decker", 3000, 1, false, "****");
    }

    public static ArticuloDTO getArticulo3() {
        return new ArticuloDTO(3L, "Notebook", "Tecnologia", "Dell", 25000, 3, true, "***");
    }

    public static ArticuloDTO getArticulo4() {
        return new ArticuloDTO(4L, "Moto G9", "Celulares", "Motorola", 28000, 4, true, "*****");
    }
}
