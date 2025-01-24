package org.example;

import org.example.commands.*;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private ArrayDeque<Product> products = new ArrayDeque<>();
    private final String filename;
    private Scanner scanner = new Scanner(System.in);
    private final Deque<String> commandHistory = new ArrayDeque<>();
    private final ZonedDateTime initializationDate = ZonedDateTime.now();
    private final Map<String, CommandInterface> commands = new HashMap<>();

    public Main(String filename) {
        this.filename = filename;
        loadFromFile();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put("help", new Help(commands, scanner));
        commands.put("info", new Info(products, scanner, initializationDate));
        commands.put("show", new Show(products, scanner));
        commands.put("add", new Add(products, scanner));
        commands.put("update", new Update(products, scanner));
        commands.put("remove_by_id", new RemoveById(products, scanner));
        commands.put("clear", new Clear(products, scanner));
        commands.put("save", new Save(products, scanner, filename));
        commands.put("execute_script", new ExecuteScript(products, scanner, this, commands));
        commands.put("exit", new Exit(products, scanner));
        commands.put("remove_first", new RemoveFirst(products, scanner));
        commands.put("add_if_min", new AddIfMin(products, scanner));
        commands.put("history", new History(products, scanner, commandHistory));
        commands.put("remove_all_by_owner", new RemoveAllByOwner(products, scanner));
        commands.put("filter_by_price", new FilterByPrice(products, scanner));
        commands.put("print_field_descending_unit_of_measure", new PrintFieldDescendingUnitOfMeasure(products, scanner));
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Использование: java Main <имя_файла>");
            System.exit(1);
        }

        Main app = new Main(args[0]);
        app.run();
    }

    public void run() {
        System.out.println("Добро пожаловать в систему управления продуктами. Введите 'help' для списка команд.");
        sortProducts();
        while (true) {
            System.out.print("> ");
            sortProducts();
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            String commandName = input.split("\\s+")[0];
            CommandInterface command = commands.get(commandName);
            if (command != null) {
                try {
                    command.execute(input);
                    commandHistory.add(commandName);
                    if (commandHistory.size() > 12) {
                        commandHistory.removeFirst();
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка при выполнении команды '" + commandName + "': " + e.getMessage());
                }
            } else {
                System.out.println("Неизвестная команда. Введите 'help' для списка команд.");
            }
        }
    }

    private void loadFromFile() {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("Файл данных не найден, начинается с пустой коллекции.");
                return;
            }

            Scanner fileScanner = new Scanner(file);
            StringBuilder xmlContent = new StringBuilder();
            while (fileScanner.hasNextLine()) {
                xmlContent.append(fileScanner.nextLine());
            }
            fileScanner.close();
            XMLParser parser = new XMLParser();
            products = parser.parseProducts(xmlContent.toString());
            validateProducts();
            updateIdCounter();
            sortProducts();
            System.out.println("Коллекция загружена из файла.");
        } catch (Exception e) {
            System.err.println("Не удалось загрузить коллекцию из файла: " + e.getMessage());
        }
    }

    private void sortProducts() {
        List<Product> sortedList = new ArrayList<>(products);
        sortedList.sort(Comparator.comparing(Product::getPrice, Comparator.nullsLast(Comparator.naturalOrder())));
        products.clear();
        products.addAll(sortedList);
    }
    private void validateProducts() throws Exception {
        Set<Long> ids = new HashSet<>();
        boolean flag = false;
        int cnt = 1;
        for (Product product : products) {
            if (product.getId() <= 0) {
                throw new Exception("Неверный id продукта: " + product.getId());
            }
            if (!ids.add(product.getId())) {
                flag = true;
            }
        }

        if (flag) {
            System.out.println("Перезаписываем новые ID, так как были совпадения");
            for (Product product : products) {
                product.setId(cnt);
                cnt++;
            }
        }
    }

    private void updateIdCounter() {
        long maxId = products.stream()
                .mapToLong(Product::getId)
                .max()
                .orElse(0);
        Product.setIdCounter(maxId);
    }

    public ArrayDeque<Product> getProducts() {
        return products;
    }

    /**
     *
     * @return Deque<String>
     */
    public Deque<String> getCommandHistory() {
        return commandHistory;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Map<String, CommandInterface> getCommands() {
        return commands;
    }
}
