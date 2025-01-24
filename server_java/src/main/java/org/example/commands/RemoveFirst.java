package org.example.commands;

import org.example.Product;

import java.util.ArrayDeque;
import java.util.Scanner;

public class RemoveFirst extends AbstractCommand {

    public RemoveFirst(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Удалить первый элемент из коллекции";
    }

    @Override
    public String getName() {
        return "remove_first";
    }

    @Override
    public void execute(String input) {
        if (!products.isEmpty()) {
            products.removeFirst();
            System.out.println("Первый элемент удален.");
        } else {
            System.out.println("Коллекция пуста.");
        }
    }
}
