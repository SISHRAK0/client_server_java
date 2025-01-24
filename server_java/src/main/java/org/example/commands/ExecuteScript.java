package org.example.commands;

import org.example.Main;
import org.example.Product;

import java.io.File;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class ExecuteScript extends AbstractCommand {
    private Main app;
    private Map<String, CommandInterface> commands;
    private HashSet<String> executingScripts;

    public ExecuteScript(ArrayDeque<Product> products, Scanner scanner, Main app, Map<String, CommandInterface> commands) {
        super(products, scanner);
        this.app = app;
        this.commands = commands;
        this.executingScripts = new HashSet<>();
    }

    @Override
    public String getDescription() {
        return "Исполнить скрипт из файла";
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public void execute(String input) {
        if (input.isEmpty()) {
            System.out.println("Не указан файл скрипта.");
            return;
        }
        String scriptName = input.split("\\s+")[1];
        if (executingScripts.contains(scriptName)) {
            System.out.println("Ошибка: скрипт '" + scriptName + "' уже выполняется.");
            return;
        }
        executingScripts.add(scriptName);
        try {
            File scriptFile = new File(input.split("\\s+")[1]);
            if (!scriptFile.exists() || !scriptFile.isFile() || !scriptFile.canRead()) {
                System.out.println("Невозможно прочитать файл скрипта.");
                return;
            }
            Scanner fileScanner = new Scanner(scriptFile);
            Scanner oldScanner = app.getScanner();
            app.setScanner(fileScanner);

            while (app.getScanner().hasNextLine()) {
                String line = app.getScanner().nextLine();
                System.out.println("> " + line);
                if (line.trim().isEmpty()) continue;

                String commandName = line.split("\\s+")[0];
                CommandInterface command = commands.get(commandName);
                if (command != null) {
                    try {
                        command.execute(line);
                        app.getCommandHistory().add(commandName);
                        if (app.getCommandHistory().size() > 12) {
                            app.getCommandHistory().removeFirst();
                        }
                    } catch (Exception e) {
                        System.err.println("Ошибка при выполнении команды '" + commandName + "': " + e.getMessage());
                    }
                } else {
                    System.out.println("Неизвестная команда: " + commandName);
                }
            }
            app.setScanner(oldScanner);
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении скрипта: " + e.getMessage());
        }
    }
}