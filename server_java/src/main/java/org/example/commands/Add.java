package org.example.commands;

import org.example.Product;
import org.example.ProductBuilder;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Add extends AbstractCommand {

    public Add(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Добавить новый элемент в коллекцию";
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public void execute(String input) {
        String[] args = input.split("\\s+");
        Product product;
        if(args.length < 4){
            product = ProductBuilder.buildProductFromInput(scanner);
        } else{
            product = ProductBuilder.buildProductFromArgs(args);
        }

        if (product != null) {
            products.add(product);
            System.out.println(product);
            System.out.println("Продукт добавлен в коллекцию.");
        }
    }
}
