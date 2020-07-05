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
        reset();
    }

    public void reset() {
        file = new File("Database.xml");
        documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch(Exception exception) {}

        try {
            printWriter = new PrintWriter(file, "UTF-8");
        } catch(Exception exception) {}

        length = document.getElementsByTagName("ID").getLength();
        num = 0;

        id = new String[length];
        name = new String[length];
        amount = new String[length];
        category = new String[length];
        bestbefore = new String[length];

        readOut();
    }

    public void readOut() {
        for(int tmp = 0; tmp<length; tmp++) {
            id[tmp] = document.getElementsByTagName("ID").item(tmp).getTextContent();
            name[tmp] = document.getElementsByTagName("Name").item(tmp).getTextContent();
            amount[tmp] = document.getElementsByTagName("Amount").item(tmp).getTextContent();
            category[tmp] = document.getElementsByTagName("Category").item(tmp).getTextContent();
            bestbefore[tmp] = document.getElementsByTagName("BestBefore").item(tmp).getTextContent();
        }
        sortOut();
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

    public void addProduct(String n, String a, String c, String b) {
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
            printWriter.println("\t<Product>");
            printWriter.println("\t\t<ID>" + String.valueOf(length+1) + "</ID>");
            printWriter.println("\t\t<Name>" + n + "</Name>");
            printWriter.println("\t\t<Amount>" + a + "</Amount>");
            printWriter.println("\t\t<Category>" + c + "</Category>");
            printWriter.println("\t\t<BestBefore>" + b + "</BestBefore>");
            printWriter.println("\t</Product>");
            printWriter.println("</Database>");
            printWriter.close();
        } catch(Exception exception) {}
        reset();
    }

    public void removeProduct(String i) {
        try {
            printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            printWriter.println("<Database>");
            for(int tmp = 0; tmp<Integer.parseInt(i)-1; tmp++) {
                printWriter.println("\t<Product>");
                printWriter.println("\t\t<ID>" + id[tmp] + "</ID>");
                printWriter.println("\t\t<Name>" + name[tmp] + "</Name>");
                printWriter.println("\t\t<Amount>" + amount[tmp] + "</Amount>");
                printWriter.println("\t\t<Category>" + category[tmp] + "</Category>");
                printWriter.println("\t\t<BestBefore>" + bestbefore[tmp] + "</BestBefore>");
                printWriter.println("\t</Product>");
            }
            for(int tmp = Integer.parseInt(i); tmp<length; tmp++) {
                printWriter.println("\t<Product>");
                printWriter.println("\t\t<ID>" + String.valueOf(Integer.parseInt(id[tmp])-1) + "</ID>");
                printWriter.println("\t\t<Name>" + name[tmp] + "</Name>");
                printWriter.println("\t\t<Amount>" + amount[tmp] + "</Amount>");
                printWriter.println("\t\t<Category>" + category[tmp] + "</Category>");
                printWriter.println("\t\t<BestBefore>" + bestbefore[tmp] + "</BestBefore>");
                printWriter.println("\t</Product>");
            }
            printWriter.println("</Database>");
            printWriter.close();
        } catch(Exception exception) {}
        reset();
    }

    public static void main(String args[]) { //temporary code - will be removed after implementation
        Xml main = new Xml();
        System.out.println("Categories: " + Arrays.toString(main.categories) + "\n");
        main.printOut();
        System.out.println("...\n");
        try {
            main.amount[1] = String.valueOf(Integer.parseInt(main.amount[1]) - 1);
        } catch(Exception exception) {}
        main.printOut();
        System.out.println("...\n");
        main.addProduct("Neues_Testprodukt", "1234567890", "kategorietest", "1.1.2021");
        main.printOut();
        System.out.println("...\n");
        main.removeProduct("2");
        main.printOut();
        main.overWrite(); //mandatory line to execute when application shuts down
    }
}
