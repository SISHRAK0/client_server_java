package org.example;

import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс, представляющий продукт. Содержит уникальный идентификатор, название, координаты,
 * дату создания, цену, единицы измерения и информацию о владельце.
 * Реализует интерфейс {@link Comparable} для сравнения продуктов по их цене.
 */
public class Product implements Comparable<Product> {
    private static final AtomicLong idCounter = new AtomicLong(0);

    /**
     * Уникальный идентификатор продукта, больше 0. Генерируется автоматически.
     */
    private long id;

    /**
     * Название продукта. Не может быть {@code null} или пустой строкой.
     */
    private String name;

    /**
     * Координаты продукта. Не могут быть {@code null}.
     */
    private Coordinates coordinates;

    /**
     * Дата создания продукта. Не может быть {@code null} и генерируется автоматически.
     */
    private final ZonedDateTime creationDate;

    /**
     * Цена продукта. Должна быть больше 0.
     */
    private Float price;

    /**
     * Единицы измерения продукта. Не могут быть {@code null}.
     */
    private UnitOfMeasure unitOfMeasure;

    /**
     * Владелец продукта. Может быть {@code null}.
     */
    private Person owner;

    /**
     * Конструктор, создающий экземпляр {@code Product} с заданными параметрами.
     *
     * @param name          название продукта, не может быть {@code null} или пустым.
     * @param coordinates   координаты продукта, не могут быть {@code null}.
     * @param price         цена продукта, должна быть больше 0.
     * @param unitOfMeasure единица измерения продукта, не может быть {@code null}.
     * @param owner         владелец продукта, может быть {@code null}.
     * @throws IllegalArgumentException если какое-либо из обязательных полей имеет недопустимое значение.
     */
    public Product(String name, Coordinates coordinates, Float price, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = generateId();
        this.creationDate = ZonedDateTime.now();
        setName(name);
        setCoordinates(coordinates);
        setPrice(price);
        setUnitOfMeasure(unitOfMeasure);
        setOwner(owner);
    }

    /**
     * Возвращает уникальный идентификатор продукта.
     *
     * @return уникальный идентификатор продукта.
     */
    public long getId() {
        return id;
    }


    public static void setIdCounter(long value) {
        idCounter.set(value);
    }

    /**
     * Устанавливает уникальный идентификатор продукта.
     *
     * @param id уникальный идентификатор, должен быть больше 0.
     * @throws IllegalArgumentException если {@code id} меньше или равен 0.
     */
    public void setId(long id) {
        if (id <= 0) throw new IllegalArgumentException("id должен быть больше 0.");
        this.id = id;
    }

    /**
     * Возвращает название продукта.
     *
     * @return название продукта.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название продукта.
     *
     * @param name название продукта, не может быть {@code null} или пустым.
     * @throws IllegalArgumentException если {@code name} является {@code null} или пустым.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Имя не может быть пустым.");
        this.name = name;
    }

    /**
     * Возвращает координаты продукта.
     *
     * @return координаты продукта.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает координаты продукта.
     *
     * @param coordinates координаты продукта, не могут быть {@code null}.
     * @throws IllegalArgumentException если {@code coordinates} является {@code null}.
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) throw new IllegalArgumentException("Координаты не могут быть null.");
        this.coordinates = coordinates;
    }

    /**
     * Возвращает дату создания продукта.
     *
     * @return дата создания продукта.
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает цену продукта.
     *
     * @return цена продукта.
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Устанавливает цену продукта.
     *
     * @param price цена продукта, должна быть больше 0.
     * @throws IllegalArgumentException если {@code price} меньше или равен 0.
     */
    public void setPrice(Float price) {
        if (price != null && price <= 0) throw new IllegalArgumentException("Цена должна быть больше 0.");
        this.price = price;
    }

    /**
     * Возвращает единицы измерения продукта.
     *
     * @return единицы измерения продукта.
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Устанавливает единицы измерения продукта.
     *
     * @param unitOfMeasure единицы измерения продукта, не могут быть {@code null}.
     * @throws IllegalArgumentException если {@code unitOfMeasure} является {@code null}.
     */
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            throw new IllegalArgumentException("Единицы измерения не могут быть null.");
        }
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * Возвращает владельца продукта.
     *
     * @return владелец продукта.
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Устанавливает владельца продукта.
     *
     * @param owner владелец продукта, не может быть {@code null}.
     */
    public void setOwner(Person owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner не может быть null");
        }
        this.owner = owner;
    }

    /**
     * Генерирует уникальный идентификатор для продукта.
     *
     * @return уникальный идентификатор для продукта.
     */
    public long generateId() {
        return idCounter.incrementAndGet();
    }

    /**
     * Сравнивает продукт с другим продуктом по цене.
     *
     * @param other другой продукт для сравнения.
     * @return отрицательное число, если цена текущего продукта меньше цены другого;
     * ноль, если цены равны; положительное число, если цена текущего продукта больше.
     */
    @Override
    public int compareTo(Product other) {
        if (this.price == null && other.price == null) return 0; // Оба null
        if (this.price == null) return 1; // null считается больше
        if (other.price == null) return -1; // null считается меньше
        return Float.compare(this.price, other.price);
    }

    /**
     * Возвращает строковое представление объекта {@code Product}.
     *
     * @return строковое представление объекта {@code Product}.
     */
    @Override
    public String toString() {
        return "Product{id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner +
                '}';
    }
}
