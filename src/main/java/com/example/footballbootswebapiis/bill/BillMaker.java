package com.example.footballbootswebapiis.bill;

import com.example.footballbootswebapiis.model.Basket;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import com.google.common.collect.Lists;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BillMaker {
    private static final String ORDER = "Order";
    private static final String CUSTOMER = "Customer";
    private static final String PRODUCTS = "Products";
    private static final String PRODUCT = "Product";
    private static final String NAME = "Name";
    private static final String SIZE = "Size";
    private static final String PRICE = "Price";
    private static final String TOTAL_PRICE = "TotalPrice";

    public static void createXmlBill(final List<Basket> basketList, final int totalPrice, final String userName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement(ORDER);
        document.appendChild(root);

        Element customer = document.createElement(CUSTOMER);

        Element userName2 = document.createElement(NAME);
        userName2.appendChild(document.createTextNode(userName));
        customer.appendChild(userName2);

        root.appendChild(customer);

        Element products = document.createElement(PRODUCTS);
        root.appendChild(products);

        for (Basket basket : basketList) {
            Element product = document.createElement(PRODUCT);
            products.appendChild(product);
            Element name = document.createElement(NAME);
            name.appendChild(document.createTextNode(basket.getName()));
            product.appendChild(name);
            Element size = document.createElement(SIZE);
            size.appendChild(document.createTextNode(String.valueOf(basket.getSize())));
            product.appendChild(size);
            Element price = document.createElement(PRICE);
            price.appendChild(document.createTextNode(String.valueOf(basket.getPrice())));
            product.appendChild(price);
        }

        Element totalPrice2 = document.createElement(TOTAL_PRICE);
        totalPrice2.appendChild(document.createTextNode(String.valueOf(totalPrice)));
        root.appendChild(totalPrice2);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("./xmlBill.xml"));
        transformer.transform(domSource, streamResult);
    }

    public static void createJsonBill(final List<Basket> basketList, final int totalPrice, final String userName) throws JSONException, IOException {

        JSONArray products = new JSONArray();

        for (Basket basket : basketList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(NAME, basket.getName());
            jsonObject.put(SIZE, basket.getSize());
            jsonObject.put(PRICE, basket.getPrice());
            products.put(jsonObject);
        }

        JSONObject name = new JSONObject();
        name.put(NAME, userName);

        JSONObject order = new JSONObject();
        order.put(CUSTOMER, name);
        order.put(PRODUCTS, products);
        order.put(TOTAL_PRICE, totalPrice);

        JSONObject root = new JSONObject();
        root.put(ORDER, order);

        FileWriter file = new FileWriter("./jsonBill.json");
        file.write(root.toString());
        file.close();
    }

    public static void createPdfBill(final List<Basket> basketList, final int totalPrice, final String userName) throws IOException {
        int counter = 1;
        boolean firstPage = true;
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 24);
        contentStream.newLineAtOffset(275, 750);
        contentStream.showText(ORDER);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, 20);
        contentStream.newLineAtOffset(25, 700);
        contentStream.showText(CUSTOMER);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        contentStream.newLineAtOffset(500, 700);
        contentStream.showText(LocalDate.now().toString());
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        contentStream.newLineAtOffset(50, 660);
        contentStream.showText("Name: " + userName);
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(25, 620);
        contentStream.setFont(PDType1Font.TIMES_BOLD, 20);
        contentStream.showText(PRODUCTS);
        contentStream.endText();

        List<List<Basket>> subSets = Lists.partition(basketList, 8);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);
        contentStream.setLeading(14.5f);

        for (int i = 0; i < subSets.size(); i++) {
            if (firstPage) {
                contentStream.newLineAtOffset(50, 580);

                for (Basket basket : subSets.get(i)) {
                    contentStream.showText(counter + ".");
                    contentStream.newLine();
                    contentStream.showText("Name: " + basket.getName());
                    contentStream.newLine();
                    contentStream.showText("Size: " + basket.getSize());
                    contentStream.newLine();
                    contentStream.showText("Price: " + basket.getPrice());
                    contentStream.newLine();
                    contentStream.newLine();
                    counter++;
                }
                if (i == subSets.size() - 1) {
                    contentStream.showText("Total price: " + totalPrice);
                }
                firstPage = false;
                contentStream.endText();
                contentStream.close();
            } else {

                PDPage auxPage = new PDPage();
                document.addPage(auxPage);
                PDPageContentStream auxStream = new PDPageContentStream(document, auxPage);
                auxStream.beginText();
                auxStream.setFont(PDType1Font.TIMES_ROMAN, 16);
                auxStream.setLeading(14.5f);
                auxStream.newLineAtOffset(50, 750);

                for (Basket basket : subSets.get(i)) {
                    auxStream.showText(counter + ".");
                    auxStream.newLine();
                    auxStream.showText("Name: " + basket.getName());
                    auxStream.newLine();
                    auxStream.showText("Size: " + basket.getSize());
                    auxStream.newLine();
                    auxStream.showText("Price: " + basket.getPrice());
                    auxStream.newLine();
                    auxStream.newLine();
                    counter++;
                }
                if (i == subSets.size() - 1) {
                    auxStream.showText("Total price: " + totalPrice);
                }
                auxStream.endText();
                auxStream.close();
            }
        }
        document.save(new File("./pdfBill.pdf"));
        document.close();
    }
}
