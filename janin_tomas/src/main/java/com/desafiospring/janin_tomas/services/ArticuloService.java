package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.exceptions.*;

import java.io.IOException;
import java.util.List;

public interface ArticuloService {
    List<ArticuloDTO> findArticuloByFilters(Long productId, String category, String shipping, String productName, String brand, Integer order) throws MaxFiltersException, ProductIdNotFoundException, CategoryNotFoundException, ShippingNotFoundException, InvalidShippingException, ProductNameNotFoundException, BrandNotFoundException, IOException;
    List<ArticuloDTO> findArticuloByProductId(List<ArticuloDTO> articulos, Long productId) throws ProductIdNotFoundException;
    List<ArticuloDTO> findArticuloByCategory(List<ArticuloDTO> articulos, String category) throws CategoryNotFoundException;
    List<ArticuloDTO> findArticuloByShipping(List<ArticuloDTO> articulos, String shipping) throws ShippingNotFoundException, InvalidShippingException;
    List<ArticuloDTO> findArticuloByProductName(List<ArticuloDTO> articulos, String productName) throws ProductNameNotFoundException;
    List<ArticuloDTO> findArticuloByBrand(List<ArticuloDTO> articulos, String brand) throws BrandNotFoundException;
    List<ArticuloDTO> findArticuloByProductIdNameBrand(Long productId, String name, String brand) throws ProductIdNotFoundException, ProductNameNotFoundException, BrandNotFoundException, IOException;
    List<ArticuloDTO> orderArticuloBy(List<ArticuloDTO> articulos, Integer order);
}
