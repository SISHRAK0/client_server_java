package org.example.commands;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Help extends AbstractCommand {
    private Map<String, CommandInterface> commands;

    public Help(Map<String, CommandInterface> commands, Scanner scanner) {
        super(null, scanner);
        this.commands = commands;
    }
    @Override
    public void execute(String input) {
        System.out.println("Доступные команды:");
        for (CommandInterface cmd : commands.values()) {
            printWithDelay(cmd.getName() + ": " + cmd.getDescription(), 200);
        }
    }

    @Override
    public String getDescription() {
        return "Вывести справку по доступным командам";
    }

    @Override
    public String getName() {
        return "help";
    }

    private void printWithDelay(String message, int delayInMillis) {
        try {
            TimeUnit.MILLISECONDS.sleep(delayInMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(message);
    }

}
