package org.example.commands;

import org.example.Product;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Clear extends AbstractCommand {

    public Clear(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Очистить коллекцию";
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public void execute(String input) {
        products.clear();
        System.out.println("Коллекция очищена.");
    }
}
