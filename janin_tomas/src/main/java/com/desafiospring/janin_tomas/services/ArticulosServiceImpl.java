package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.exceptions.*;
import com.desafiospring.janin_tomas.repositories.ArticulosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArticulosServiceImpl implements ArticulosService {
    @Autowired
    private ArticulosRepository articulosRepository;

    @Override
    public List<ArticuloDTO> findArticuloByFilters(String category, String shipping, String productName, String brand) throws MaxFiltersException, CategoryNotFoundException, ShippingNotFoundException, InvalidShippingException, ProductNameNotFoundException, BrandNotFoundException {
        if (countFiltros(category, shipping, productName, brand) <= 2) {
            List<ArticuloDTO> articulos = articulosRepository.findArticulos();

            articulos = findArticuloByCategory(articulos, category);
            articulos = findArticuloByShipping(articulos, shipping);
            articulos = findArticuloByProductName(articulos, productName);
            articulos = findArticuloByBrand(articulos, brand);

            return articulos.stream().collect(Collectors.toList());
        }
        else {
            throw new MaxFiltersException("No puede enviar más de dos filtros a la vez");
        }
    }

    @Override
    public List<ArticuloDTO> findArticuloByCategory(List<ArticuloDTO> articulos, String category) throws CategoryNotFoundException {
        if (category == null)
            return articulos;

        List<ArticuloDTO> response = articulos.stream()
                .filter(a -> a.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());

        if (!response.isEmpty())
            return response;
        else
            throw new CategoryNotFoundException("La categoría o la combinación ingresada no existe");
    }

    @Override
    public List<ArticuloDTO> findArticuloByShipping(List<ArticuloDTO> articulos, String shipping) throws ShippingNotFoundException, InvalidShippingException {
        if (shipping == null)
            return articulos;

        if (shipping.toLowerCase().equals("true") || shipping.toLowerCase().equals("false")) {
            boolean isFreeShopping = Boolean.parseBoolean(shipping);

            List<ArticuloDTO> response = articulos.stream()
                    .filter(a -> a.isFreeShipping() == isFreeShopping)
                    .collect(Collectors.toList());

            if (!response.isEmpty())
                return response;
            else
                throw new ShippingNotFoundException("No hay productos con el tipo de Shipping elegido");
        }
        else {
            throw new InvalidShippingException("Los valores de Shipping permitidos son 'true' y 'false'");
        }
    }

    @Override
    public List<ArticuloDTO> findArticuloByProductName(List<ArticuloDTO> articulos, String productName) throws ProductNameNotFoundException {
        if (productName == null)
            return articulos;

        List<ArticuloDTO> response = articulos.stream()
                .filter(a -> a.getName().equalsIgnoreCase(productName))
                .collect(Collectors.toList());

        if (!response.isEmpty())
            return response;
        else
            throw new ProductNameNotFoundException("El producto o la combinación ingresada no existe");
    }

    @Override
    public List<ArticuloDTO> findArticuloByBrand(List<ArticuloDTO> articulos, String brand) throws BrandNotFoundException {
        if (brand == null)
            return articulos;

        List<ArticuloDTO> response = articulos.stream()
                .filter(a -> a.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());

        if (!response.isEmpty())
            return response;
        else
            throw new BrandNotFoundException("La marca o la combinación ingresada no existe");
    }

    @Override
    public List<ArticuloDTO> orderArticuloBy(List<ArticuloDTO> articulos, Integer order) {
        return SorterService.getSorter(order).sort(articulos);
    }

    private int countFiltros(String category, String freeShipping, String productName, String brand) {
        int cont = 0;

        if (category != null)
            cont++;

        if (freeShipping != null)
            cont++;

        if (productName != null)
            cont++;

        if (brand != null)
            cont++;

        return cont;
    }
}
