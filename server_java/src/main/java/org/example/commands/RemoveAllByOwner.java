package org.example.commands;

import org.example.Person;
import org.example.Product;
import org.example.ProductBuilder;

import java.util.ArrayDeque;
import java.util.Scanner;

public class RemoveAllByOwner extends AbstractCommand {

    public RemoveAllByOwner(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Удалить элементы с заданным owner";
    }

    @Override
    public String getName() {
        return "remove_all_by_owner";
    }

    @Override
    public void execute(String input) {
        String[] parts = input.split("\\s+", 2);
        if (parts.length < 2) {
            System.out.println("Не указано имя владельца.");
            return;
        }
        String ownerName = parts[1];
        Person criteriaOwner = new Person(ownerName);
        int initialSize = products.size();
        products.removeIf(product -> product.getOwner() != null && product.getOwner().equals(criteriaOwner));
        int removed = initialSize - products.size();
        System.out.println("Удалено продуктов: " + removed);
    }
}