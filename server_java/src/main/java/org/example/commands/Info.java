package org.example.commands;

import org.example.Product;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Info extends AbstractCommand {
    private ZonedDateTime initializationDate;

    public Info(ArrayDeque<Product> products, Scanner scanner, ZonedDateTime initializationDate) {
        super(products, scanner);
        this.initializationDate = initializationDate;
    }

    @Override
    public String getDescription() {
        return "Вывести информацию о коллекции";
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public void execute(String input) {
        System.out.println("Тип коллекции: " + products.getClass().getName());
        System.out.println("Дата инициализации: " + initializationDate);
        System.out.println("Количество элементов: " + products.size());
    }
}
