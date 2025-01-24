package org.example.commands;

import org.example.Product;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Scanner;

public class FilterByPrice extends AbstractCommand {

    public FilterByPrice(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Вывести элементы с заданным значением price";
    }

    @Override
    public String getName() {
        return "filter_by_price";
    }

    @Override
    public void execute(String input) {
        String com = input.split("\\s+")[1];
        if (com.isEmpty()) {
            System.out.println("Не указана цена.");
            return;
        }
        try {
            Float price = Float.parseFloat(com);
            products.stream()
                    .filter(product -> Objects.equals(product.getPrice(), price))
                    .forEach(System.out::println);
        } catch (NumberFormatException e) {
            System.out.println("Цена должна быть числом.");
        }
    }
}
