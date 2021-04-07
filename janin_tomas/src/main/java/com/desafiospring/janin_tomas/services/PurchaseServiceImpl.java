package com.desafiospring.janin_tomas.services;

import com.desafiospring.janin_tomas.dtos.*;
import com.desafiospring.janin_tomas.exceptions.*;
import com.desafiospring.janin_tomas.repositories.ArticuloRepository;
import com.desafiospring.janin_tomas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private AtomicLong ticketId = new AtomicLong();

    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public PurchaseResponseDTO calculatePurchase(List<ArticuloPurchaseDTO> articulos) {
        TicketDTO ticket = null;
        StatusCodeDTO statusCode = null;

        try {
            double total = getTotal(articulos);

            ticket = new TicketDTO(ticketId.incrementAndGet(), articulos, total);
            statusCode = new StatusCodeDTO(200, "La solicitud de compra se completó con éxito");
        }
        catch (ProductIdNotFoundException e) {
            ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
            statusCode = new StatusCodeDTO(400, "La solicitud de compra no pudo ser completada ya que no se encontró un producto con ese ID");
        }
        catch (ProductNameNotFoundException e) {
            ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
            statusCode = new StatusCodeDTO(400, "La solicitud de compra no pudo ser completada ya que no se encontró un producto con ese nombre");
        }
        catch (BrandNotFoundException e) {
            ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
            statusCode = new StatusCodeDTO(400, "La solicitud de compra no pudo ser completada ya que no se encontró un producto con esa marca");
        }
        catch (OverQuantityException e) {
            ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
            statusCode = new StatusCodeDTO(400, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new PurchaseResponseDTO(ticket, statusCode);
    }

    @Override
    public PurchaseResponseDTO calculatePurchaseCart(List<ArticuloPurchaseDTO> articulos, String dni) throws DNINotFoundException {
        ClienteDTO cliente = clienteRepository.findClienteByDNI(dni);

        if (cliente != null) {
            TicketDTO ticket = null;
            StatusCodeDTO statusCode = null;

            try {
                double total = getTotal(articulos);

                CarritoDTO carritoDTO = clienteRepository.addToCart(articulos, total, dni);

                ticket = new TicketDTO(ticketId.incrementAndGet(), carritoDTO.getArticulos(), carritoDTO.getTotal());
                statusCode = new StatusCodeDTO(200, "La solicitud de compra se completó con éxito");
            }
            catch (ProductIdNotFoundException e) {
                ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
                statusCode = new StatusCodeDTO(400, "La solicitud de compra no pudo ser completada ya que no se encontró un producto con ese ID");
            }
            catch (ProductNameNotFoundException e) {
                ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
                statusCode = new StatusCodeDTO(400, "La solicitud de compra no pudo ser completada ya que no se encontró un producto con ese nombre");
            }
            catch (BrandNotFoundException e) {
                ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
                statusCode = new StatusCodeDTO(400, "La solicitud de compra no pudo ser completada ya que no se encontró un producto con esa marca");
            }
            catch (OverQuantityException e) {
                ticket = new TicketDTO(ticketId.incrementAndGet(), new ArrayList<>(), 0);
                statusCode = new StatusCodeDTO(400, e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new PurchaseResponseDTO(ticket, statusCode);
        }
        else {
            throw new DNINotFoundException("No se encontró ninguna persona con el DNI ingresado");
        }
    }

    private double getTotal(List<ArticuloPurchaseDTO> articulos) throws ProductIdNotFoundException, BrandNotFoundException, ProductNameNotFoundException, OverQuantityException, IOException {
        ArticuloDTO articuloDB = null;

        double total = 0;

        for (ArticuloPurchaseDTO articulo : articulos) {
            articuloDB = articuloService.findArticuloByProductIdNameBrand(articulo.getProductId(), articulo.getName(), articulo.getBrand()).stream().findFirst().get();

            if (articulo.getQuantity() <= articuloDB.getQuantity())
                total += articuloDB.getPrice() * articulo.getQuantity();
            else
                throw new OverQuantityException("La solicitud de compra no pudo ser completada ya que la cantidad de unidades de " + articulo.getName() + " superó el stock disponible");
        }

        return total;
    }
}
