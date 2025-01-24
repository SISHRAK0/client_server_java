//import org.example.*;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class ProductTest {
//
//    @Test
//    void testProductCreation() {
//        Coordinates coordinates = new Coordinates(10, 20.0);
//        Person owner = new Person("John Doe", null, EyeColor.BLUE, HairColor.BROWN, null);
//        Product product = new Product("Test Product", coordinates, 100, UnitOfMeasure.KILOGRAMS, owner);
//
//        assertEquals("Test Product", product.getName());
//        assertEquals(100, product.getPrice());
//        assertEquals(coordinates, product.getCoordinates());
//        assertEquals(owner, product.getOwner());
//    }
//
//    @Test
//    void testInvalidProductName() {
//        Coordinates coordinates = new Coordinates(10, 20.0);
//        assertThrows(IllegalArgumentException.class, () -> new Product("", coordinates, 100, UnitOfMeasure.KILOGRAMS, null));
//    }
//
//    @Test
//    void testInvalidProductPrice() {
//        Coordinates coordinates = new Coordinates(10, 20.0);
//        assertThrows(IllegalArgumentException.class, () -> new Product("Test Product", coordinates, -1, UnitOfMeasure.KILOGRAMS, null));
//    }
//}