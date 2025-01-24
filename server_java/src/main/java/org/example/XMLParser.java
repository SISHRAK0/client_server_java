package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayDeque;

/**
 * Класс для парсинга и сериализации объектов в формате XML.
 */
public class XMLParser {

    /**
     * Парсит строку XML и возвращает коллекцию продуктов.
     * @param xmlContent Строка с содержимым XML.
     * @return Коллекция продуктов.
     * @throws Exception При ошибках парсинга.
     */
    public ArrayDeque<Product> parseProducts(String xmlContent) throws Exception {
        ArrayDeque<Product> products = new ArrayDeque<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xmlContent.getBytes());
        Document doc = builder.parse(is);
        NodeList productNodes = doc.getElementsByTagName("Product");

        for (int i = 0; i < productNodes.getLength(); i++) {
            Element element = (Element) productNodes.item(i);
            Product product = parseProduct(element);
            products.add(product);
        }

        return products;
    }

    private Product parseProduct(Element element) {
        String name = getElementTextContent(element, "name");
        long id = Long.parseLong(getElementTextContent(element, "id"));


        String priceStr = getElementTextContent(element, "price");
        Float price = priceStr.isEmpty() ? null : Float.parseFloat(priceStr);

        String unitStr = getElementTextContent(element, "unitOfMeasure");
        UnitOfMeasure unitOfMeasure = unitStr.isEmpty() ? null : UnitOfMeasure.valueOf(unitStr);

        ZonedDateTime creationDate = ZonedDateTime.parse(getElementTextContent(element, "creationDate"));

        Element coordElement = (Element) element.getElementsByTagName("coordinates").item(0);
        String xStr = getElementTextContent(coordElement, "x");
        Integer x = xStr.isEmpty() ? null : Integer.parseInt(xStr);

        Long y = Long.parseLong(getElementTextContent(coordElement, "y"));
        Coordinates coordinates = new Coordinates(x, y);

        Person owner = null;
        NodeList ownerNodes = element.getElementsByTagName("owner");
        if (ownerNodes.getLength() > 0) {
            Element ownerElement = (Element) ownerNodes.item(0);
            owner = parsePerson(ownerElement);
        }

        Product product = new Product(name, coordinates, price, unitOfMeasure, owner);
        product.setId(id);
        return product;
    }

    private Person parsePerson(Element element) {
        String name = getElementTextContent(element, "name");
        String birthdayStr = getElementTextContent(element, "birthday");
        LocalDate birthday = birthdayStr.isEmpty() ? null : LocalDate.parse(birthdayStr);

        EyeColor eyeColor = EyeColor.valueOf(getElementTextContent(element, "eyeColor"));
        HairColor hairColor = HairColor.valueOf(getElementTextContent(element, "hairColor"));
        String nationalityStr = getElementTextContent(element, "nationality");
        Country nationality = nationalityStr.isEmpty() ? null : Country.valueOf(nationalityStr);

        return new Person(name, birthday, eyeColor, hairColor, nationality);
    }

    private String getElementTextContent(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) return "";
        return nodeList.item(0).getTextContent();
    }    /**
     * Сериализует коллекцию продуктов в строку XML.
     * @param products Коллекция продуктов.
     * @return Строка с содержимым XML.
     * @throws Exception При ошибках сериализации.
     */
    public String serializeProducts(ArrayDeque<Product> products) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("Products");
        doc.appendChild(root);

        for (Product product : products) {
            Element productElement = doc.createElement("Product");
            root.appendChild(productElement);

            createElementWithText(doc, productElement, "id", String.valueOf(product.getId()));
            createElementWithText(doc, productElement, "name", product.getName());

            // Обработка price
            createElementWithText(doc, productElement, "price",
                    product.getPrice() != null ? product.getPrice().toString() : "");

            createElementWithText(doc, productElement, "creationDate", product.getCreationDate().toString());
            createElementWithText(doc, productElement, "unitOfMeasure",
                    product.getUnitOfMeasure() != null ? product.getUnitOfMeasure().name() : "");

            // Координаты
            Element coordElement = doc.createElement("coordinates");
            productElement.appendChild(coordElement);
            createElementWithText(doc, coordElement, "x",
                    product.getCoordinates().getX() != null ? product.getCoordinates().getX().toString() : "");
            createElementWithText(doc, coordElement, "y", String.valueOf(product.getCoordinates().getY()));

            // Владелец
            if (product.getOwner() != null) {
                Element ownerElement = doc.createElement("owner");
                productElement.appendChild(ownerElement);

                createElementWithText(doc, ownerElement, "name", product.getOwner().getName());
                createElementWithText(doc, ownerElement, "birthday",
                        product.getOwner().getBirthday() != null ? product.getOwner().getBirthday().toString() : "");
                createElementWithText(doc, ownerElement, "eyeColor", product.getOwner().getEyeColor().name());
                createElementWithText(doc, ownerElement, "hairColor", product.getOwner().getHairColor().name());
                createElementWithText(doc, ownerElement, "nationality",
                        product.getOwner().getNationality() != null ? product.getOwner().getNationality().name() : "");
            }
        }

        // Преобразование Document в строку
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformer.transform(source, result);

        return sw.toString();
    }

    private void createElementWithText(Document doc, Element parent, String tagName, String textContent) {
        Element elem = doc.createElement(tagName);
        elem.appendChild(doc.createTextNode(textContent));
        parent.appendChild(elem);
    }
}