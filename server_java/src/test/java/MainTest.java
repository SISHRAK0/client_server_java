//import org.example.Main;
//import org.example.Coordinates;
//import org.example.Product;
//import org.example.UnitOfMeasure;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayDeque;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class MainTest {
//
//    private Main mainApp;
//
//    @BeforeEach
//    void setUp() {
//        mainApp = Mockito.spy(new Main("test.xml"));
//    }
//
//    @Test
//    void testAddProduct() {
//        String input = "Test Product\n10\n20.0\n100\nKILOGRAMS\nno\n";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        mainApp.add();
//
//        ArrayDeque<Product> products = mainApp.getProducts();
//        assertEquals(1, products.size());
//        Product product = products.peek();
//        assertNotNull(product);
//        assertEquals("Test Product", product.getName());
//        assertEquals(100, product.getPrice());
//        assertEquals(UnitOfMeasure.KILOGRAMS, product.getUnitOfMeasure());
//        assertEquals(10, product.getCoordinates().getX());
//        assertEquals(20.0, product.getCoordinates().getY());
//        assertNull(product.getOwner());
//    }
//
//    @Test
//    void testRemoveById() {
//        Product product = new Product("Test Product", new Coordinates(10, 20.0), 100, UnitOfMeasure.KILOGRAMS, null);
//        mainApp.getProducts().add(product);
//        mainApp.removeById(String.valueOf(product.getId()));
//        assertTrue(mainApp.getProducts().isEmpty());
//    }
//}
