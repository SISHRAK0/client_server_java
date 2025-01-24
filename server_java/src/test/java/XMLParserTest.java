//import org.example.*;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayDeque;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class XMLParserTest {
//
//    @Test
//    void testParseProducts() throws Exception {
//        String xmlContent = "<Products>" +
//                "<Product>" +
//                "<id>1</id>" +
//                "<name>Test Product</name>" +
//                "<price>100</price>" +
//                "<creationDate>2023-10-01T10:15:30+01:00[Europe/London]</creationDate>" +
//                "<unitOfMeasure>KILOGRAMS</unitOfMeasure>" +
//                "<coordinates><x>10</x><y>20.0</y></coordinates>" +
//                "<owner><name>John Doe</name><eyeColor>BLUE</eyeColor><hairColor>BROWN</hairColor></owner>" +
//                "</Product>" +
//                "</Products>";
//
//        XMLParser parser = new XMLParser();
//        ArrayDeque<Product> products = parser.parseProducts(xmlContent);
//
//        assertEquals(1, products.size());
//        Product product = products.peek();
//        assertNotNull(product);
//        assertEquals("Test Product", product.getName());
//        assertEquals(100, product.getPrice());
//        assertEquals(UnitOfMeasure.KILOGRAMS, product.getUnitOfMeasure());
//    }
//
//    @Test
//    void testSerializeProducts() throws Exception {
//        ArrayDeque<Product> products = new ArrayDeque<>();
//        Coordinates coordinates = new Coordinates(10, 20.0);
//        Person owner = new Person("John Doe", null, EyeColor.BLUE, HairColor.BROWN, null);
//        Product product = new Product("Test Product", coordinates, 100, UnitOfMeasure.KILOGRAMS, owner);
//        products.add(product);
//
//        XMLParser parser = new XMLParser();
//        String xmlContent = parser.serializeProducts(products);
//
//        assertTrue(xmlContent.contains("<name>Test Product</name>"));
//        assertTrue(xmlContent.contains("<price>100</price>"));
//        assertTrue(xmlContent.contains("<unitOfMeasure>KILOGRAMS</unitOfMeasure>"));
//    }
//}