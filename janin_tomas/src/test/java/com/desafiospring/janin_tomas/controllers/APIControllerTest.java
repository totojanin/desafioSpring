package com.desafiospring.janin_tomas.controllers;

import com.apple.eawt.Application;
import com.desafiospring.janin_tomas.dtos.*;
import com.desafiospring.janin_tomas.services.ArticuloService;
import com.desafiospring.janin_tomas.services.ArticuloTestService;
import com.desafiospring.janin_tomas.services.PurchaseService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
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
    private ArticuloService articuloService;

    @MockBean
    private PurchaseService purchaseService;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAllProducts() throws Exception {
        List<ArticuloDTO> articulos = Arrays.asList(
            ArticuloTestService.getArticulo1(),
            ArticuloTestService.getArticulo2(),
            ArticuloTestService.getArticulo3(),
            ArticuloTestService.getArticulo4()
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

    @Test
    public void getFilteredProducts() throws Exception {
        List<ArticuloDTO> articulos = Arrays.asList(
                ArticuloTestService.getArticulo1(),
                ArticuloTestService.getArticulo2(),
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo5()
        );

        List<ArticuloDTO> articulosFiltrados = Arrays.asList(
                ArticuloTestService.getArticulo2(),
                ArticuloTestService.getArticulo5()
        );

        when(articuloService.findArticuloByFilters(any(), any(), any(), any(), any(), any())).thenReturn(articulosFiltrados);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/articles")
                .param("category", "Herramientas"))
                .andExpect(status().isOk())
                .andReturn();

        List<ArticuloDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<ArticuloDTO>>() {
                });

        Assertions.assertEquals(articulosFiltrados, responseArticles);
    }

    @Test
    public void getFilteredWithFreeShippingProducts() throws Exception {
        List<ArticuloDTO> articulos = Arrays.asList(
                ArticuloTestService.getArticulo1(),
                ArticuloTestService.getArticulo2(),
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo4(),
                ArticuloTestService.getArticulo5(),
                ArticuloTestService.getArticulo6(),
                ArticuloTestService.getArticulo7(),
                ArticuloTestService.getArticulo8()
        );

        List<ArticuloDTO> articulosFiltrados = Arrays.asList(
                ArticuloTestService.getArticulo2(),
                ArticuloTestService.getArticulo5(),
                ArticuloTestService.getArticulo8()
        );

        when(articuloService.findArticuloByFilters(any(), any(), any(), any(), any(), any())).thenReturn(articulosFiltrados);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/articles")
                .param("category", "Herramientas")
                .param("freeShipping", "true"))
                .andExpect(status().isOk())
                .andReturn();

        List<ArticuloDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<ArticuloDTO>>() {
                });

        Assertions.assertEquals(articulosFiltrados, responseArticles);
    }

    @Test
    public void getOrderedNameAscProducts() throws Exception {
        List<ArticuloDTO> articulos = Arrays.asList(
                ArticuloTestService.getArticulo1(),
                ArticuloTestService.getArticulo2(),
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo4()
        );

        List<ArticuloDTO> articulosFiltrados = Arrays.asList(
                ArticuloTestService.getArticulo2(),
                ArticuloTestService.getArticulo4(),
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo1()
        );

        when(articuloService.findArticuloByFilters(any(), any(), any(), any(), any(), any())).thenReturn(articulosFiltrados);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/articles")
                .param("order", "0"))
                .andExpect(status().isOk())
                .andReturn();

        List<ArticuloDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<ArticuloDTO>>() {
                });

        Assertions.assertEquals(articulosFiltrados, responseArticles);
    }

    @Test
    public void getOrderedNameDescProducts() throws Exception {
        List<ArticuloDTO> articulos = Arrays.asList(
                ArticuloTestService.getArticulo1(),
                ArticuloTestService.getArticulo2(),
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo4()
        );

        List<ArticuloDTO> articulosFiltrados = Arrays.asList(
                ArticuloTestService.getArticulo1(),
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo4(),
                ArticuloTestService.getArticulo2()
        );

        when(articuloService.findArticuloByFilters(any(), any(), any(), any(), any(), any())).thenReturn(articulosFiltrados);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/articles")
                .param("order", "1"))
                .andExpect(status().isOk())
                .andReturn();

        List<ArticuloDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<ArticuloDTO>>() {
                });

        Assertions.assertEquals(articulosFiltrados, responseArticles);
    }

    @Test
    public void getOrderedPriceDescProducts() throws Exception {
        List<ArticuloDTO> articulos = Arrays.asList(
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo4(),
                ArticuloTestService.getArticulo5(),
                ArticuloTestService.getArticulo6()
        );

        List<ArticuloDTO> articulosFiltrados = Arrays.asList(
                ArticuloTestService.getArticulo6(),
                ArticuloTestService.getArticulo4(),
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo5()
        );

        when(articuloService.findArticuloByFilters(any(), any(), any(), any(), any(), any())).thenReturn(articulosFiltrados);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/articles")
                .param("order", "3"))
                .andExpect(status().isOk())
                .andReturn();

        List<ArticuloDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<ArticuloDTO>>() {
                });

        Assertions.assertEquals(articulosFiltrados, responseArticles);
    }

    @Test
    public void getOrderedPriceAscProducts() throws Exception {
        List<ArticuloDTO> articulos = Arrays.asList(
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo4(),
                ArticuloTestService.getArticulo5(),
                ArticuloTestService.getArticulo6()
        );

        List<ArticuloDTO> articulosFiltrados = Arrays.asList(
                ArticuloTestService.getArticulo5(),
                ArticuloTestService.getArticulo3(),
                ArticuloTestService.getArticulo4(),
                ArticuloTestService.getArticulo6()
        );

        when(articuloService.findArticuloByFilters(any(), any(), any(), any(), any(), any())).thenReturn(articulosFiltrados);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/articles")
                .param("order", "2"))
                .andExpect(status().isOk())
                .andReturn();

        List<ArticuloDTO> responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<List<ArticuloDTO>>() {
                });

        Assertions.assertEquals(articulosFiltrados, responseArticles);
    }

    @Test
    public void existingPurchaseRequest() throws Exception {
        List<ArticuloPurchaseDTO> articuloPurchases = Arrays.asList(
                ArticuloTestService.getArticuloPurchase1(),
                ArticuloTestService.getArticuloPurchase2(),
                ArticuloTestService.getArticuloPurchase3(),
                ArticuloTestService.getArticuloPurchase4()
        );

        List<ArticuloPurchaseDTO> articuloPurchasesToBuy = Arrays.asList(
                ArticuloTestService.getArticuloPurchase1(),
                ArticuloTestService.getArticuloPurchase4()
        );

        PurchaseRequestDTO purchaseRequest = new PurchaseRequestDTO(articuloPurchasesToBuy);

        PurchaseResponseDTO purchaseResponse = new PurchaseResponseDTO(
                new TicketDTO(1L, articuloPurchasesToBuy, 37600),
                new StatusCodeDTO(200, "La solicitud de compra se completó con éxito")
        );

        when(purchaseService.calculatePurchase(any())).thenReturn(purchaseResponse);

        String jsonRequest = objectMapper.writeValueAsString(purchaseRequest);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/purchase-request")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PurchaseResponseDTO responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<PurchaseResponseDTO>() {
                });

        Assertions.assertEquals(purchaseResponse.getTicket().getTotal(), responseArticles.getTicket().getTotal());
    }

    @Test
    public void notExistingPurchaseRequest() throws Exception {
        List<ArticuloPurchaseDTO> articuloPurchases = Arrays.asList(
                ArticuloTestService.getArticuloPurchase1(),
                ArticuloTestService.getArticuloPurchase2(),
                ArticuloTestService.getArticuloPurchase3(),
                ArticuloTestService.getArticuloPurchase4()
        );

        List<ArticuloPurchaseDTO> articuloPurchasesToBuy = Arrays.asList(
                ArticuloTestService.getArticuloPurchase2(),
                ArticuloTestService.getArticuloPurchase3()
        );

        PurchaseRequestDTO purchaseRequest = new PurchaseRequestDTO(articuloPurchasesToBuy);

        PurchaseResponseDTO purchaseResponse = new PurchaseResponseDTO(
                new TicketDTO(1L, new ArrayList<>(), 0),
                new StatusCodeDTO(400, "La solicitud de compra no pudo ser completada ya que no se encontró un producto con ese nombre")
        );

        when(purchaseService.calculatePurchase(any())).thenReturn(purchaseResponse);

        String jsonRequest = objectMapper.writeValueAsString(purchaseRequest);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/purchase-request")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        PurchaseResponseDTO responseArticles =
                objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), new TypeReference<PurchaseResponseDTO>() {
                });

        Assertions.assertEquals(purchaseResponse.getStatusCode().getCode(), responseArticles.getStatusCode().getCode());
        Assertions.assertEquals(purchaseResponse.getStatusCode().getMessage(), responseArticles.getStatusCode().getMessage());
    }
}
