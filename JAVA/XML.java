import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.io.File;

public class XML { //temporary code - switch from arrays to objects planned
    public String[] id;
    public String[] name;
    public String[] amount;
    public String[] category;
    public String[] bestbefore;

    private File file;
    private DocumentBuilderFactory documentBuilderFactory;
    private DocumentBuilder documentBuilder;
    private Document document;

    public int length;

    public XML() {
        try {
            file = new File("database.xml");
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
        } catch(Exception exception) {}

        length = document.getElementsByTagName("ID").getLength();

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

    public int overWrite(int tag, int item, String content) { //placeholder function - supposed to write files instead
        int recall = 1;
        try {
            switch(tag){
                case 0:
                    id[item] = content;
                    recall = 0;
                    break;
                case 1:
                    name[item] = content;
                    recall = 0;
                    break;
                case 2:
                    amount[item] = content;
                    recall = 0;
                    break;
                case 3:
                    category[item] = content;
                    recall = 0;
                    break;
                case 4:
                    bestbefore[item] = content;
                    recall = 0;
                    break;
            }
        } catch(Exception exception) {}
        return recall;
    }

    public static void main(String args[]) throws InterruptedException { //temporary code - will be removed after implementation
        XML main = new XML();
        main.printOut();
        System.out.println("...");
        Thread.sleep(3000);
        System.out.println();
        System.out.println("Return: " + main.overWrite(1, 0, "Änderungstest_1"));
        System.out.println();
        main.printOut();
    }
}
