package org.example;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс-строитель для создания объектов {@link Product} и {@link Person}.
 * Позволяет создавать продукты как из интерактивного ввода пользователя, так и из аргументов командной строки.
 */
public class ProductBuilder {

    /**
     * Создает новый объект {@link Product} на основе интерактивного ввода пользователя.
     *
     * @param scanner сканер для чтения ввода пользователя.
     * @return созданный объект {@link Product} или {@code null}, если произошла ошибка.
     */
    public static Product buildProductFromInput(Scanner scanner) {
        try {
            System.out.print("Введите имя продукта: ");
            String name = readNonEmptyString(scanner);

            System.out.println("Введите координаты:");
            System.out.print("  x (int): ");
            Integer x = readInt(scanner);

            System.out.print("  y (Long <= 486): ");
            Long y = readLong(scanner, value -> value != null && value <= 486, "Значение должно быть не больше 486.");

            Coordinates coordinates = new Coordinates(x, y);

            System.out.print("Введите цену (Float > 0): ");
            Float price = readFloat(scanner, value -> value == null || value > 0, "Цена должна быть больше 0.");

            System.out.println("Выберите единицу измерения из списка:");
            for (UnitOfMeasure unit : UnitOfMeasure.values()) {
                System.out.println(" - " + unit);
            }
            System.out.print("Введите единицу измерения: ");
            UnitOfMeasure unitOfMeasure = readEnum(scanner, UnitOfMeasure.class, false);

            System.out.print("Введите информацию о владельце:\n");
            Person owner = createPerson(scanner);

            return new Product(name, coordinates, price, unitOfMeasure, owner);
        } catch (Exception e) {
            System.err.println("Ошибка при создании продукта: " + e.getMessage());
            return null;
        }
    }

    /**
     * Создает новый объект {@link Product} на основе аргументов командной строки.
     * <p>
     * Аргументы должны передаваться в формате key=value, например:
     * name="Product1" x=100 y=200 price=50 unit=PCS ownerName="John Doe" eyeColor=BLUE hairColor=RED nationality=USA
     *
     * @param args массив строк с аргументами.
     * @return созданный объект {@link Product} или {@code null}, если произошла ошибка.
     */
    public static Product buildProductFromArgs(String[] args) {
        try {
            Map<String, String> argsMap = parseArguments(args);

            String name = argsMap.get("name");
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Имя продукта не указано.");
            }

            String xStr = argsMap.get("x");
//            if (xStr == null) {
//                throw new IllegalArgumentException("Координата x не указана.");
//            }
            Integer x = Integer.parseInt(xStr);


            String yStr = argsMap.get("y");
            if (yStr == null) {
                throw new IllegalArgumentException("Координата y не указана.");
            }
            Long y = Long.parseLong(yStr);
            if (y > 486) {
                throw new IllegalArgumentException("Значение y должно быть не больше 486.");
            }

            Coordinates coordinates = new Coordinates(x, y);

            String priceStr = argsMap.get("price");
            if (priceStr == null) {
                throw new IllegalArgumentException("Цена не указана.");
            }
            Float price = Float.parseFloat(priceStr);
//            if (price <= 0) {
//                throw new IllegalArgumentException("Цена должна быть больше 0.");
//            }

            String unitStr = argsMap.get("unitOfMeasure");
            UnitOfMeasure unitOfMeasure = null;
            if (unitStr != null && !unitStr.isEmpty()) {
                try {
                    unitOfMeasure = UnitOfMeasure.valueOf(unitStr);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Некорректная единица измерения: " + unitStr);
                }
            }

            Person owner = null;
            if (argsMap.containsKey("ownerName")) {
                owner = buildPersonFromArgs(argsMap);
            }

            return new Product(name, coordinates, price, unitOfMeasure, owner);
        } catch (Exception e) {
            System.err.println("Ошибка при создании продукта из аргументов: " + e.getMessage());
            return null;
        }
    }

    /**
     * Создает нового владельца {@link Person} на основе интерактивного ввода пользователя.
     *
     * @param scanner сканер для чтения ввода пользователя.
     * @return созданный объект {@link Person} или {@code null}, если произошла ошибка.
     */
    public static Person createPerson(Scanner scanner) {
        try {
            System.out.print("Введите имя владельца: ");
            String name = readNonEmptyString(scanner);

            System.out.print("Введите дату рождения (YYYY-MM-DD) или оставьте пустым: ");
            String dateStr = scanner.nextLine().trim();
            LocalDate birthday = dateStr.isEmpty() ? null : LocalDate.parse(dateStr);

            System.out.println("Выберите цвет глаз из списка:");
            for (EyeColor color : EyeColor.values()) {
                System.out.println(" - " + color);
            }
            System.out.print("Введите цвет глаз: ");
            EyeColor eyeColor = readEnum(scanner, EyeColor.class, false);

            System.out.println("Выберите цвет волос из списка:");
            for (HairColor color : HairColor.values()) {
                System.out.println(" - " + color);
            }
            System.out.print("Введите цвет волос: ");
            HairColor hairColor = readEnum(scanner, HairColor.class, false);

            System.out.println("Выберите национальность из списка:");
            for (Country country : Country.values()) {
                System.out.println(" - " + country);
            }
            System.out.print("Введите национальность (или оставьте пустым): ");
            Country nationality = readEnum(scanner, Country.class, true);

            return new Person(name, birthday, eyeColor, hairColor, nationality);
        } catch (Exception e) {
            System.err.println("Ошибка при создании владельца: " + e.getMessage());
            return null;
        }
    }

    /**
     * Создает нового владельца {@link Person} на основе аргументов командной строки.
     * <p>
     * Аргументы должны содержать ключи:
     * ownerName, birthday (опционально), eyeColor, hairColor, nationality (опционально)
     *
     * @param argsMap карта с аргументами.
     * @return созданный объект {@link Person} или {@code null}, если произошла ошибка.
     */
    private static Person buildPersonFromArgs(Map<String, String> argsMap) {
        try {
            String name = argsMap.get("ownerName");
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Имя владельца не указано.");
            }

            String birthdayStr = argsMap.get("birthday");
            LocalDate birthday = (birthdayStr == null || birthdayStr.isEmpty()) ? null : LocalDate.parse(birthdayStr);

            String eyeColorStr = argsMap.get("eyeColor");
            EyeColor eyeColor = null;
            if (eyeColorStr != null && !eyeColorStr.isEmpty()) {
                try {
                    eyeColor = EyeColor.valueOf(eyeColorStr);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Некорректный цвет глаз: " + eyeColorStr);
                }
            } else {
                throw new IllegalArgumentException("Цвет глаз не указан.");
            }

            String hairColorStr = argsMap.get("hairColor");
            HairColor hairColor = null;
            if (hairColorStr != null && !hairColorStr.isEmpty()) {
                try {
                    hairColor = HairColor.valueOf(hairColorStr);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Некорректный цвет волос: " + hairColorStr);
                }
            } else {
                throw new IllegalArgumentException("Цвет волос не указан.");
            }

            String nationalityStr = argsMap.get("nationality");
            Country nationality = null;
            if (nationalityStr != null && !nationalityStr.isEmpty()) {
                try {
                    nationality = Country.valueOf(nationalityStr);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Некорректная национальность: " + nationalityStr);
                }
            }

            return new Person(name, birthday, eyeColor, hairColor, nationality);
        } catch (Exception e) {
            System.err.println("Ошибка при создании владельца из аргументов: " + e.getMessage());
            return null;
        }
    }

    /**
     * Читает непустую строку из сканера.
     *
     * @param scanner сканер для чтения ввода.
     * @return непустая строка.
     */
    private static String readNonEmptyString(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.print("Строка не может быть пустой. Попробуйте снова: ");
            }
        }
    }

    /**
     * Читает целое число из сканера с проверкой условия.
     *
     * @param scanner      сканер для чтения ввода.
     * @param condition    условие для проверки значения.
     * @param errorMessage сообщение об ошибке при несоответствии условию.
     * @return прочитанное целое число.
     */
    private static long readLong(Scanner scanner, java.util.function.Predicate<Long> condition, String errorMessage) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                long value = Long.parseLong(input);
                if (condition.test(value)) {
                    return value;
                } else {
                    System.out.print(errorMessage + " Попробуйте снова: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число. Попробуйте снова: ");
            }
        }
    }

    private static Integer readInt(Scanner scanner) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return null;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число. Попробуйте снова: ");
            }
        }
    }

    /**
     * Читает число с плавающей точкой из сканера с проверкой условия.
     *
     * @param scanner      сканер для чтения ввода.
     * @param condition    условие для проверки значения.
     * @param errorMessage сообщение об ошибке при несоответствии условию.
     * @return прочитанное число с плавающей точкой или {@code null}, если ввод некорректен.
     */
    private static Double readDouble(Scanner scanner, java.util.function.Predicate<Double> condition, String errorMessage) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);
                if (condition.test(value)) {
                    return value;
                }
                if (input.isEmpty()) {
                    return null;
                }
                System.out.print("Введите корректное число. Попробуйте снова: ");
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число. Попробуйте снова: ");
            }
        }
    }


    private static Float readFloat(Scanner scanner, java.util.function.Predicate<Float> condition, String errorMessage) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return null;
                }
                Float value = Float.parseFloat(input);
                if (condition.test(value)) {
                    return value;
                }
                System.out.print("Введите корректное число. Попробуйте снова: ");
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число. Попробуйте снова: ");
            }
        }
    }

    /**
     * Читает значение перечисления из сканера.
     *
     * @param scanner   сканер для чтения ввода.
     * @param enumClass класс перечисления.
     * @param allowNull разрешать ли пустой ввод.
     * @param <T>       тип перечисления.
     * @return значение перечисления или {@code null}, если разрешен пустой ввод.
     */
    private static <T extends Enum<T>> T readEnum(Scanner scanner, Class<T> enumClass, boolean allowNull) {
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty() && allowNull) {
                return null;
            }
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                System.out.print("Некорректное значение. Введите одно из: ");
                for (T enumConst : enumClass.getEnumConstants()) {
                    System.out.print(enumConst.name() + " ");
                }
                System.out.print("\nПопробуйте снова: ");
            }
        }
    }

    /**
     * Парсит массив строк с аргументами в формате key=value.
     *
     * @param args массив строк с аргументами.
     * @return карта с ключами и значениями аргументов.
     */
    private static Map<String, String> parseArguments(String[] args) {
        Map<String, String> argsMap = new HashMap<>();
        Pattern pattern = Pattern.compile("(\\w+)=([^\"]\\S*|\".+?\")");
        for (String arg : args) {
            Matcher matcher = pattern.matcher(arg);
            if (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2).replace("\"", "");
                argsMap.put(key, value);
            }
        }
        return argsMap;
    }
}
