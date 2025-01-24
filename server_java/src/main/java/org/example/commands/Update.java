package org.example.commands;

import org.example.Product;
import org.example.ProductBuilder;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Update extends AbstractCommand {

    public Update(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Обновить значение элемента по id";
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public void execute(String input) {
//        System.out.print("Введите id продукта для обновления: ");
//        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("Не указан id.");
            return;
        }
        try {
            long id = Long.parseLong(input.split("\\s+")[1]);
            Product existingProduct = null;
            for (Product product : products) {
                if (product.getId() == id) {
                    existingProduct = product;
                    break;
                }
            }
            if (existingProduct == null) {
                System.out.println("Продукт с таким id не найден.");
                return;
            }
            String[] args = input.split("\\s+");
            Product updatedProduct;
            if (args.length < 7) {
                updatedProduct = ProductBuilder.buildProductFromInput(scanner);
            } else {
                updatedProduct = ProductBuilder.buildProductFromArgs(args);
            }
            if (updatedProduct != null) {
                updatedProduct.setId(id);
                products.remove(existingProduct);
                products.add(updatedProduct);
                System.out.println("Продукт обновлен.");
            }
        } catch (NumberFormatException e) {
            System.out.println("id должен быть числом.");
        }
    }
}
