package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.*;
import com.desafiospring.janin_tomas.exceptions.ProductIdNotFoundException;
import com.desafiospring.janin_tomas.repositories.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private AtomicLong ticketId = new AtomicLong();

    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Override
    public PurchaseResponseDTO calculatePurchase(List<ArticuloPurchaseDTO> articulos) throws ProductIdNotFoundException {
        double total = 0;

        ArticuloDTO articulo = null;
        TicketDTO ticket = null;
        StatusCodeDTO statusCode = null;

        try {
            for (ArticuloPurchaseDTO a : articulos) {
                articulo = articuloService.findArticuloByProductId(articuloRepository.findArticulos(), a.getProductId()).stream().findFirst().get();

                total += articulo.getPrice() * a.getQuantity();
            }

            ticket = new TicketDTO(ticketId.incrementAndGet(), articulos, total);
            statusCode = new StatusCodeDTO(200, "La solicitud de compra se completó con éxito");
        }
        catch (ProductIdNotFoundException e) {
            ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
            statusCode = new StatusCodeDTO(404, "La solicitud de compra no pudo ser completada");
        }

        return new PurchaseResponseDTO(ticket, statusCode);
    }
}
