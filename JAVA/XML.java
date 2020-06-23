import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.io.File;

public class XML {
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

    public int readOut() {
        try {
            for(int tmp = 0; tmp<length; tmp++) {
                id[tmp] = document.getElementsByTagName("ID").item(tmp).getTextContent();
                name[tmp] = document.getElementsByTagName("Name").item(tmp).getTextContent();
                amount[tmp] = document.getElementsByTagName("Amount").item(tmp).getTextContent();
                category[tmp] = document.getElementsByTagName("Category").item(tmp).getTextContent();
                bestbefore[tmp] = document.getElementsByTagName("BestBefore").item(tmp).getTextContent();
            }
            return 0;
        } catch(Exception exception) {return 1;} //process failed
    }

    public int printOut() {
        try {
            for(int tmp = 0; tmp<length; tmp++) {
                System.out.println("ID: " + id[tmp]);
                System.out.println("Name: " + name[tmp]);
                System.out.println("Amount: " + amount[tmp]);
                System.out.println("Category: " + category[tmp]);
                System.out.println("BestBefore: " + bestbefore[tmp]);
                System.out.println();
            }
            return 0;
        } catch(Exception exception) {return 1;} //process failed
    }

    public static void main(String args[]) {
        XML main = new XML();
        main.printOut();
    }
}
