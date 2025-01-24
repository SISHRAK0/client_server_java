package org.example.commands;

import org.example.Product;
import org.example.UnitOfMeasure;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Scanner;

public class PrintFieldDescendingUnitOfMeasure extends AbstractCommand {

    public PrintFieldDescendingUnitOfMeasure(ArrayDeque<Product> products, Scanner scanner) {
        super(products, scanner);
    }

    @Override
    public String getDescription() {
        return "Вывести unitOfMeasure в порядке убывания";
    }

    @Override
    public String getName() {
        return "print_field_descending_unit_of_measure";
    }

    @Override
    public void execute(String input) {
        products.stream()
                .map(Product::getPrice)
                .sorted(Comparator.nullsLast(Comparator.reverseOrder()))
                .forEach(System.out::println);
    }
}
