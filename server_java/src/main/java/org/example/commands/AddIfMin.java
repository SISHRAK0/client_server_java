package org.example.commands;

import org.example.Product;
import org.example.ProductBuilder;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Scanner;

public class AddIfMin extends AbstractCommand {

    public AddIfMin(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент, если его значение меньше наименьшего";
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public void execute(String input) {
        String[] args = input.split("\\s+");
        Product product;
        if(args.length < 10){
            product = ProductBuilder.buildProductFromInput(scanner);
        } else{
            product = ProductBuilder.buildProductFromArgs(args);
        }
        if (product != null) {
            if (products.isEmpty() || product.compareTo(Collections.min(products)) < 0) {
                products.add(product);
                System.out.println("Продукт добавлен в коллекцию.");
            } else {
                System.out.println("Продукт не минимален и не был добавлен.");
            }
        }
    }
}
