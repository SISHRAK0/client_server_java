package org.example;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Класс, представляющий человека. Содержит информацию об имени, дате рождения,
 * цвете глаз, цвете волос и национальности.
 */
public class Person {
    /**
     * Имя человека. Не может быть {@code null} или пустым.
     */
    private String name;

    /**
     * Дата рождения человека. Может быть {@code null}.
     */
    private LocalDate birthday;

    /**
     * Цвет глаз человека. Не может быть {@code null}.
     */
    private EyeColor eyeColor;

    /**
     * Цвет волос человека. Не может быть {@code null}.
     */
    private HairColor hairColor;

    /**
     * Национальность человека. Может быть {@code null}.
     */
    private Country nationality;

    /**
     * Конструктор, создающий объект {@code Person} с заданными параметрами.
     *
     * @param name        имя человека, не может быть {@code null} или пустым.
     * @param birthday    дата рождения человека, может быть {@code null}.
     * @param eyeColor    цвет глаз человека, не может быть {@code null}.
     * @param hairColor   цвет волос человека, не может быть {@code null}.
     * @param nationality национальность человека, может быть {@code null}.
     * @throws IllegalArgumentException если {@code name} является {@code null} или пустым, или если {@code eyeColor} или {@code hairColor} являются {@code null}.
     */
    public Person(String name, LocalDate birthday, EyeColor eyeColor, HairColor hairColor, Country nationality) {
        setName(name);
        setBirthday(birthday);
        setEyeColor(eyeColor);
        setHairColor(hairColor);
        setNationality(nationality);
    }

    public Person(String ownerName) {
        this.name = ownerName;
    }

    /**
     * Возвращает имя человека.
     *
     * @return имя человека.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя человека.
     *
     * @param name имя человека, не может быть {@code null} или пустым.
     * @throws IllegalArgumentException если {@code name} является {@code null} или пустым.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Имя не может быть пустым.");
        this.name = name;
    }

    /**
     * Возвращает дату рождения человека.
     *
     * @return дата рождения человека, может быть {@code null}.
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Устанавливает дату рождения человека.
     *
     * @param birthday дата рождения человека, может быть {@code null}.
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Возвращает цвет глаз человека.
     *
     * @return цвет глаз человека.
     */
    public EyeColor getEyeColor() {
        return eyeColor;
    }

    /**
     * Устанавливает цвет глаз человека.
     *
     * @param eyeColor цвет глаз человека, не может быть {@code null}.
     * @throws IllegalArgumentException если {@code eyeColor} является {@code null}.
     */
    public void setEyeColor(EyeColor eyeColor) {
        if (eyeColor == null) throw new IllegalArgumentException("Цвет глаз не может быть null.");
        this.eyeColor = eyeColor;
    }

    /**
     * Возвращает цвет волос человека.
     *
     * @return цвет волос человека.
     */
    public HairColor getHairColor() {
        return hairColor;
    }

    /**
     * Устанавливает цвет волос человека.
     *
     * @param hairColor цвет волос человека, не может быть {@code null}.
     * @throws IllegalArgumentException если {@code hairColor} является {@code null}.
     */
    public void setHairColor(HairColor hairColor) {
        if (hairColor == null) throw new IllegalArgumentException("Цвет волос не может быть null.");
        this.hairColor = hairColor;
    }

    /**
     * Возвращает национальность человека.
     *
     * @return национальность человека, может быть {@code null}.
     */
    public Country getNationality() {
        return nationality;
    }

    /**
     * Устанавливает национальность человека.
     *
     * @param nationality национальность человека, может быть {@code null}.
     */
    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    /**
     * Возвращает строковое представление объекта {@code Person}.
     *
     * @return строковое представление объекта {@code Person}.
     */
    @Override
    public String toString() {
        return "Person{name='" + name + '\'' +
                ", birthday=" + birthday +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name); // Сравниваем по имени
    }

    @Override
    public int hashCode() {
        return Objects.hash(name); // Генерируем hashCode по имени
    }
}
