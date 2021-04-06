package com.desafiospring.janin_tomas.repositories;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticulosRepositoryImpl implements ArticulosRepository {
    @Override
    public List<ArticuloDTO> findArticulos() {
        List<ArticuloDTO> articulos = loadDataBase();

        List<ArticuloDTO> result = new ArrayList<>();

        if (articulos != null) {
            result = articulos;
        }

        return result;
    }

    private List<ArticuloDTO> loadDataBase() {
        List<ArticuloDTO> articulos = new ArrayList<>();

        try (Scanner scanner = new Scanner(ResourceUtils.getFile("classpath:dbProductos.csv"));) {
            if (scanner.hasNextLine())
                scanner.nextLine();

            while (scanner.hasNextLine()) {
                articulos.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return articulos;
    }

    private ArticuloDTO getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();

        ArticuloDTO articulo = new ArticuloDTO();

        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");

            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }

            articulo = mapToArticuloDTO(values);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return articulo;
    }

    private ArticuloDTO mapToArticuloDTO(List<String> values) {
        long productId = Long.parseLong(values.get(0));
        String name = values.get(1);
        String category = values.get(2);
        String brand = values.get(3);
        double price = Double.parseDouble(values.get(4).replace("$", "").replace(".", ""));
        int quantity = Integer.parseInt(values.get(5));
        boolean freeShipping = values.get(6).equals("SI");
        int prestige = values.get(7).length();

        return new ArticuloDTO(productId, name, category, brand, price, quantity, freeShipping, prestige);
    }
}
