package com.desafiospring.janin_tomas.repositories;

import com.desafiospring.janin_tomas.dtos.ArticuloDTO;
import com.desafiospring.janin_tomas.dtos.ArticuloPurchaseDTO;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Repository
@Data
public class ArticuloRepositoryImpl implements ArticuloRepository {
    @Override
    public List<ArticuloDTO> findArticulos() throws IOException {
        List<ArticuloDTO> db = loadDataBase();

        List<ArticuloDTO> articulos = new ArrayList<>();

        if (db != null) {
            articulos = db;
        }

        return articulos;
    }

    @Override
    public boolean descontarStock(ArticuloPurchaseDTO articulo) {
        ArticuloDTO articuloDB = null;

        int row = 1;

        try {
            CSVReader reader = new CSVReader(new FileReader("/Users/tjanin/IdeaProjects/DesafioSpring/janin_tomas/src/main/resources/dbProductos.csv"), ',');

            String[] nextLine = reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                articuloDB = getRecordFromLine(nextLine);

                if (articuloDB.getProductId() == articulo.getProductId()) {
                    Integer quantity = articuloDB.getQuantity() - articulo.getQuantity();

                    updateCSV("/Users/tjanin/IdeaProjects/DesafioSpring/janin_tomas/src/main/resources/dbProductos.csv", quantity.toString(), row, 5);

                    break;
                }

                row++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private List<ArticuloDTO> loadDataBase() throws IOException {
        List<ArticuloDTO> articulos = new ArrayList<>();

        try {
            CSVReader reader = new CSVReader(new FileReader("/Users/tjanin/IdeaProjects/DesafioSpring/janin_tomas/src/main/resources/dbProductos.csv"), ',');

            String[] nextLine = reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                articulos.add(getRecordFromLine(nextLine));
            }

            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return articulos;
    }

    private ArticuloDTO getRecordFromLine(String[] nextLine) {
        ArticuloDTO articulo = new ArticuloDTO();

        try {
            List<String> lineAsList = new ArrayList<String>(Arrays.asList(nextLine));

            articulo = mapToArticuloDTO(lineAsList);
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
        String prestige = values.get(7);

        return new ArticuloDTO(productId, name, category, brand, price, quantity, freeShipping, prestige);
    }

    public static void updateCSV(String fileToUpdate, String replace, int row, int col) throws IOException {

        File inputFile = new File(fileToUpdate);

        CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
        List<String[]> csvBody = reader.readAll();

        csvBody.get(row)[col] = replace;
        reader.close();

        CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',');

        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }
}
