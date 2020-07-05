import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.util.Arrays;
import java.io.File;

import java.io.PrintWriter;
import java.io.IOException;

public class Xml { //temporary code - switch from arrays to lists planned
    public String[] id;
    public String[] name;
    public String[] amount;
    public String[] category;
    public String[] bestbefore;

    private PrintWriter printWriter;
    private File file;

    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    public String[] categories;
    public int length;
    public int num;

    public Xml() {
            file = new File("Database.xml");
            documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);

            file = new File("Output.xml");
            printWriter = new PrintWriter("Output.xml", "UTF-8");
        } catch(Exception exception) {}

        length = document.getElementsByTagName("ID").getLength();
        num = 0;

        id = new String[length];
        name = new String[length];
        amount = new String[length];
        category = new String[length];
        bestbefore = new String[length];

        readOut();
        sortOut();
    }

    public void readOut() {
        for(int tmp = 0; tmp<length; tmp++) {
            id[tmp] = document.getElementsByTagName("ID").item(tmp).getTextContent();
            name[tmp] = document.getElementsByTagName("Name").item(tmp).getTextContent();
            amount[tmp] = document.getElementsByTagName("Amount").item(tmp).getTextContent();
            category[tmp] = document.getElementsByTagName("Category").item(tmp).getTextContent();
            bestbefore[tmp] = document.getElementsByTagName("BestBefore").item(tmp).getTextContent();
        }
    }

    public void sortOut() {
        String[] bid = new String[length];
        for(int tmp = 0; tmp<length; tmp++) {
            if(!Arrays.asList(bid).contains(category[tmp])) {
                bid[num] = category[tmp];
                num++;
            } else {}
        }
        categories = new String[num];
        for(int tmp = 0; tmp<num; tmp++) {
            categories[tmp] = bid[tmp];
        }
    }

    public void printOut() {
        for(int tmp = 0; tmp<length; tmp++) {
            System.out.println("ID: " + id[tmp]);
            System.out.println("Name: " + name[tmp]);
            System.out.println("Amount: " + amount[tmp]);
            System.out.println("Category: " + category[tmp]);
            System.out.println("BestBefore: " + bestbefore[tmp]);
            System.out.println();
        }
    }

    public void overWrite() {
        try {
            printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            printWriter.println("<Database>");
            for(int tmp = 0; tmp<length; tmp++) {
                printWriter.println("\t<Product>");
                printWriter.println("\t\t<ID>" + id[tmp] + "</ID>");
                printWriter.println("\t\t<Name>" + name[tmp] + "</Name>");
                printWriter.println("\t\t<Amount>" + amount[tmp] + "</Amount>");
                printWriter.println("\t\t<Category>" + category[tmp] + "</Category>");
                printWriter.println("\t\t<BestBefore>" + bestbefore[tmp] + "</BestBefore>");
                printWriter.println("\t</Product>");
            }
            printWriter.println("</Database>");
            printWriter.close();
        } catch(Exception exception) {}
    }

    public static void main(String args[]) throws InterruptedException { //temporary code - will be removed after implementation
        Xml main = new Xml();
        main.printOut();
        System.out.println("...");
        Thread.sleep(3000);
        System.out.println();
        System.out.println("Categories: " + Arrays.toString(main.categories));
        System.out.println();
        System.out.println("...");
        Thread.sleep(3000);
        System.out.println();
        try {
            main.amount[1] = String.valueOf(Integer.parseInt(main.amount[1]) - 1);
        } catch(Exception exception) {}
        main.overWrite();
        System.out.println();
        main.printOut();
        main.overWrite();
    }
}
