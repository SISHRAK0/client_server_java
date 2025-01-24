package org.example.commands;

import org.example.Product;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Show extends AbstractCommand {

    public Show(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Вывести все элементы коллекции";
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public void execute(String input) {
        if (products.isEmpty()) {
            System.out.println("Коллекция пуста.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }
}
