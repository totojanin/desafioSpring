package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.services.comparators.*;
import lombok.Data;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Data
public class SorterService {
    public static SorterService getSorter(Integer order) {
        if (order != null) {
            switch (order) {
                case 0:
                    return new NameAscService();
                case 1:
                    return new NameDescService();
                case 2:
                    return new PriceAscService();
                case 3:
                    return new PriceDescService();
                case 4:
                    return new PrestigeAscService();
                case 5:
                    return new PrestigeDescService();
            }
        }

        return new DefaultOrderService();
    }

    public List<ArticuloDTO> sort(List<ArticuloDTO> articulos) {
        throw new NotImplementedException();
    }
}
