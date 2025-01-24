package org.example.commands;

import org.example.Product;
import org.example.XMLParser;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Save extends AbstractCommand {
    private String filename;

    public Save(ArrayDeque<Product> products, Scanner scanner, String filename) {
        super(products, scanner);
        this.filename = filename;
    }

    @Override
    public String getDescription() {
        return "Сохранить коллекцию в файл";
    }


    @Override
    public String getName() {
        return "save";
    }

    /**
     *
     * @param input
     */
    @Override
    public void execute(String input) {
        try {
            XMLParser parser = new XMLParser();
            String xmlContent = parser.serializeProducts(products);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filename));
            bos.write(xmlContent.getBytes());
            bos.close();
            System.out.println("Коллекция сохранена в файл.");
        } catch (Exception e) {
            System.err.println("Не удалось сохранить коллекцию: " + e.getMessage());
        }
    }
}
