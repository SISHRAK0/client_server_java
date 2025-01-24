package org.example.commands;

import org.example.Product;

import java.util.ArrayDeque;
import java.util.Scanner;

public class RemoveById extends AbstractCommand {

    public RemoveById(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Удалить элемент из коллекции по его id";
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public void execute(String input) {
        if (input.split("\\s+")[1].isEmpty()) {
            System.out.println("Не указан id.");
            return;
        }
        try {
            long id = Long.parseLong(input.split("\\s+")[1]);
            Product productToRemove = null;
            for (Product product : products) {
                if (product.getId() == id) {
                    productToRemove = product;
                    break;
                }
            }
            if (productToRemove == null) {
                System.out.println("Продукт с таким id не найден.");
                return;
            }
            products.remove(productToRemove);
            System.out.println("Продукт удален.");
        } catch (NumberFormatException e) {
            System.out.println("id должен быть числом.");
        }
    }
}
