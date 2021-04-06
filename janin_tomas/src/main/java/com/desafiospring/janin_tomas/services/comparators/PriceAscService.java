package com.desafiospring.janin_tomas.services.comparators;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.services.SorterService;

import java.util.Comparator;
import java.util.List;

public class PriceAscService extends SorterService {
    private Comparator<ArticuloDTO> c = new Comparator<ArticuloDTO>() {
        @Override
        public int compare(ArticuloDTO o1, ArticuloDTO o2) {
            return (int)(o1.getPrice() - o2.getPrice());
        }
    };

    public List<ArticuloDTO> sort(List<ArticuloDTO> articulos) {
        articulos.sort(c);

        return articulos;
    }
}
