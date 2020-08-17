/*
MIT License
Copyright (c) 2020 Robert Kagan
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.File;

import java.util.Arrays;

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

    private void reset() {
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

    private void readOut() {
        for(int tmp = 0; tmp<length; tmp++) {
            id[tmp] = document.getElementsByTagName("ID").item(tmp).getTextContent();
            name[tmp] = document.getElementsByTagName("Name").item(tmp).getTextContent();
            amount[tmp] = document.getElementsByTagName("Amount").item(tmp).getTextContent();
            category[tmp] = document.getElementsByTagName("Category").item(tmp).getTextContent();
            bestbefore[tmp] = document.getElementsByTagName("BestBefore").item(tmp).getTextContent();
        }
        sortOut();
    }

    private void sortOut() {
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

    public void testPrintOut(int tmp) {
            System.out.println("ID: " + id[tmp]);
            System.out.println("Name: " + name[tmp]);
            System.out.println("Amount: " + amount[tmp]);
            System.out.println("Category: " + category[tmp]);
            System.out.println("BestBefore: " + bestbefore[tmp]);
            System.out.println();
    }

    private void forProduct(int tmp) {
        printWriter.println("\t<Product>");
        printWriter.println("\t\t<ID>" + id[tmp] + "</ID>");
        printWriter.println("\t\t<Name>" + name[tmp] + "</Name>");
        printWriter.println("\t\t<Amount>" + amount[tmp] + "</Amount>");
        printWriter.println("\t\t<Category>" + category[tmp] + "</Category>");
        printWriter.println("\t\t<BestBefore>" + bestbefore[tmp] + "</BestBefore>");
        printWriter.println("\t</Product>");
    }

    public void addProduct(String n, String a, String b, String c) {
        try {
            printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            printWriter.println("<Database>");
            for(int tmp = 0; tmp<length; tmp++) {
                forProduct(tmp);
            }
            printWriter.println("\t<Product>");
            printWriter.println("\t\t<ID>" + String.valueOf(length-1) + "</ID>");
            printWriter.println("\t\t<Name>" + n + "</Name>");
            printWriter.println("\t\t<Amount>" + a + "</Amount>");
            printWriter.println("\t\t<Category>" + c + "</Category>");
            printWriter.println("\t\t<BestBefore>" + b + "</BestBefore>");
            printWriter.println("\t</Product>");
            printWriter.println("</Database>");
            printWriter.close();
        } catch(Exception exception) {}
        overWrite();
        reset();
    }

    public void changeProduct (String idNew, String nameNew, String amountNew, String bestbeforeNew) {
        testPrintOut(Integer.parseInt(idNew)+1);
        name[Integer.parseInt(idNew)+1] = nameNew;
        amount[Integer.parseInt(idNew)+1] = amountNew;
        bestbefore[Integer.parseInt(idNew)+1] = bestbeforeNew;
        testPrintOut(Integer.parseInt(idNew));
    }
    public void removeProduct(String i) {
        try {
            printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            printWriter.println("<Database>");
            for(int tmp = 0; tmp<(Integer.parseInt(i)-1); tmp++) {
                forProduct(tmp);
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
        overWrite();
        reset();
    }

    public void overWrite() {
        try {
            printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            printWriter.println("<Database>");
            for(int tmp = 0; tmp<length; tmp++) {
                forProduct(tmp);
            }
            printWriter.println("</Database>");
            printWriter.close();
        } catch(Exception exception) {}
    }
}
