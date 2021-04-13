package com.desafiospring.janin_tomas.controllers;

import com.apple.eawt.Application;
import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.exceptions.*;
import com.desafiospring.janin_tomas.repositories.ArticuloRepository;
import com.desafiospring.janin_tomas.services.ArticuloService;
import com.desafiospring.janin_tomas.services.ArticuloServiceTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(APIController.class)
@ContextConfiguration(classes = Application.class)
@ComponentScan(basePackages = "com.desafiospring.janin_tomas")
public class APIControllerTest {
    @MockBean
    private ArticuloRepository articuloRepository;

    @MockBean
    private ArticuloService articuloService;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAll() throws Exception {
        List<ArticuloDTO> articulos = Arrays.asList(
            ArticuloServiceTest.getArticulo1(),
            ArticuloServiceTest.getArticulo2(),
            ArticuloServiceTest.getArticulo3(),
            ArticuloServiceTest.getArticulo4()
        );

        when(articuloService.findArticuloByFilters(any(), any(), any(), any(), any(), any())).thenReturn(articulos);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/articles"))
                .andExpect(status().isOk())
                .andReturn();

        List<ArticuloDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<ArticuloDTO>>() {
                });

        Assertions.assertEquals(articulos, responseArticles);
    }
}
