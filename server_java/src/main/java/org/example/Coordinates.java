package org.example;

/**
 * Класс, представляющий координаты.
 */
public class Coordinates {
    private Integer x;
    private Long y; // Максимальное значение: 486, не может быть null

    public Coordinates(Integer x, Long y) {
        setX(x);
        setY(y);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        if (y == null || y > 486) throw new IllegalArgumentException("y не может быть null и должна быть не больше 486.");
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}