package org.example.commands;

import org.example.Product;

import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * Абстрактный класс для команд, предоставляющий общие методы и свойства.
 */
public abstract class AbstractCommand implements CommandInterface {
    protected ArrayDeque<Product> products;
    protected Scanner scanner;

    public AbstractCommand(ArrayDeque<Product> products, Scanner scanner) {
        this.products = products;
        this.scanner = scanner;
    }

    @Override
    public abstract void execute(String input);
}
