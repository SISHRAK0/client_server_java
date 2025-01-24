package org.example.commands;

import org.example.Product;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Exit extends AbstractCommand {

    public Exit(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Завершить программу (без сохранения в файл)";
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public void execute(String input) {
        System.out.println("Завершение программы без сохранения...");
        System.exit(0);
    }
}
