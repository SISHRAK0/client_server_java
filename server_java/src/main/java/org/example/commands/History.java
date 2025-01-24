package org.example.commands;

import org.example.Product;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class History extends AbstractCommand {
    private Deque<String> commandHistory;

    public History(ArrayDeque<Product> products, Scanner scanner, Deque<String> commandHistory) {
        super(products, scanner);
        this.commandHistory = commandHistory;
    }

    @Override
    public String getDescription() {
        return "Вывести последние 12 команд";
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public void execute(String input) {
        System.out.println("История команд:");
        for (String cmd : commandHistory) {
            System.out.println(cmd);
        }
    }
}
