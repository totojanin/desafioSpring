package com.desafiospring.janin_tomas.repositories;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.dtos.ArticuloPurchaseDTO;

import java.io.IOException;
import java.util.List;

public interface ArticuloRepository {
    List<ArticuloDTO> findArticulos() throws IOException;
    boolean descontarStock(ArticuloPurchaseDTO articulo);
}
