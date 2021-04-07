package com.desafiospring.janin_tomas.repositories;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;

import java.util.List;

public interface ArticuloRepository {
    List<ArticuloDTO> findArticulos();
}
