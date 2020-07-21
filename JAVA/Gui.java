/*
MIT License

Copyright (c) 2020 Marcel Rose and Julius Fels

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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Gui {

    static String name;

    static String amount;

    static String bestbefore;

    static String category;

    JFrame f = new JFrame();

    SpringLayout Layout = new SpringLayout();

    JPanel ContentPane = new JPanel();


    JLabel Headerlabel = new JLabel("Schulsanitätsdienst Materialmanegement");

    JButton Create_New = new JButton("Produkte hinzufügen");

    JPanel panel = new JPanel();

    Xml xml = new Xml();

    public static void add_product() {


        JFrame j = new JFrame("Produkt hinzufügen");


        JPanel p1 = new JPanel();


        JLabel l1 = new JLabel("Name");


        JLabel l2 = new JLabel("Amount");


        JLabel l3 = new JLabel("Bestbefore");


        JLabel l4 = new JLabel("Category");


        JTextField f1 = new JTextField();


        JTextField f2 = new JTextField();


        JTextField f3 = new JTextField();


        //JTextField f4 = new JTextField();


        String[] choices = new String[10];


        int amountOfChoices = choices.length;


        System.out.println(amountOfChoices);


        choices[0] = "Verbandsmaterial";


        choices[1] = "Sauerstoff";


        JComboBox f4 = new JComboBox(choices);


        JButton b1 = new JButton("Fertig");


        JButton b2 = new JButton("Abbrechen");


        //JButton b3 = new JButton("Add");


        p1.setLayout(new GridBagLayout());


        GridBagConstraints c = new GridBagConstraints();


        c.fill = GridBagConstraints.VERTICAL;


        c.gridx = 0;


        c.gridy = 0;


        p1.add(l1, c);


        c.gridx = 1;


        c.gridy = 0;


        p1.add(f1, c);


        c.gridx = 0;


        c.gridy = 1;


        p1.add(l2, c);


        c.gridx = 1;


        c.gridy = 1;


        p1.add(f2, c);


        c.gridx = 0;


        c.gridy = 2;


        p1.add(l3, c);


        c.gridx = 1;


        c.gridy = 2;


        p1.add(f3, c);


        c.gridx = 0;


        c.gridy = 3;


        p1.add(l4, c);


        c.gridx = 1;


        c.gridy = 3;


        p1.add(f4, c);


        c.gridx = 0;


        c.gridy = 4;


        p1.add(b1, c);


        c.gridx = 1;


        c.gridy = 4;


        p1.add(b2, c);


        c.gridx = 2;


        c.gridy = 3;


        //p1.add(b3, c);


        f1.setPreferredSize(new Dimension(130, 20));


        f2.setPreferredSize(new Dimension(130, 20));


        f3.setPreferredSize(new Dimension(130, 20));


        f4.setPreferredSize(new Dimension(130, 20));


        f4.setBackground(Color.white);


        b1.setBackground(Color.green);


        b1.setPreferredSize(new Dimension(130, 25));


        b2.setBackground(Color.red);


        b2.setPreferredSize(new Dimension(130, 25));


        //b3.setBackground(Color.white);


        j.add(p1);


        p1.setVisible(true);


        j.setSize(500, 500);


        j.setVisible(true);


        b1.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {
                Xml xml=new Xml();

                xml.addProduct(f1.getText(),f2.getText(),f3.getText(),"" + f4.getSelectedItem());
                xml.overWrite();

                System.out.println(f4.getSelectedItem());


                p1.setVisible(false);


                j.setSize(0, 0);


                j.setVisible(false);


            }


        });


        b2.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {


                //System.out.println("Abbrechen");


                p1.setVisible(false);


                j.setSize(0, 0);


                j.setVisible(false);


            }


        });


        /*b3.addActionListener(new ActionListener() {


            public void actionPerformed(ActionEvent e) {


                String newCategory;


                newCategory = JOptionPane.showInputDialog("Please input new Category: ");


                for (int i = 0; i < amountOfChoices; i++) {


                    if (choices[i] != "" && choices[i] != null) {


                        choices[i] = newCategory;


                    }


                }


            }


        });*/
    }

        public void TableExample() {

            try {

                String[][] data = new String[xml.length][5];

                for (int i = 0; i < (xml.length); i++) {

                    data[i][0] = xml.id[i];

                    data[i][1] = xml.name[i];

                    data[i][2] = xml.amount[i];

                    data[i][3] = xml.category[i];

                    data[i][4] = xml.bestbefore[i];

                }

                String[] column = {"ID", "NAME", "AMOUNT", "Category", "Bestbefore"};

                
                DefaultTableModel tableModel = new DefaultTableModel(data, column) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //all cells false
                        return false;
                    }
                };
                JTable table = new JTable(tableModel);

                table.setBounds(30, 40, 200, 300);

                Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, table, 0, SpringLayout.HORIZONTAL_CENTER, ContentPane);

                Layout.putConstraint(SpringLayout.NORTH, table, 100, SpringLayout.NORTH, ContentPane);

                ContentPane.add(table);

            } catch (Exception e) {
                System.out.println(e);
            }

        }


    public void Gui() {

        ContentPane.setLayout(Layout);

        ContentPane.setBackground(Color.WHITE);

        xml.overWrite();


        ImageIcon image = new ImageIcon("C:\\Users\\Meneer\\Pictures\\image.png");

        JLabel imageLabel = new JLabel(image);


        Create_New.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                add_product();

            }

        });


        TableExample();


        Headerlabel.setBackground(Color.red);


        Headerlabel.setFont(new Font("Sans", Font.BOLD, 30));


        ContentPane.add(Create_New);

        ContentPane.add(Headerlabel);

        f.setSize(1280, 720);

        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Headerlabel, 0, SpringLayout.HORIZONTAL_CENTER, ContentPane);

        Layout.putConstraint(SpringLayout.WEST, Create_New, 100, SpringLayout.WEST, ContentPane);

        Layout.putConstraint(SpringLayout.NORTH, Create_New, 50, SpringLayout.NORTH, ContentPane);

        f.add(ContentPane);

        f.setVisible(true);

        f.setContentPane(ContentPane);

        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                xml.overWrite();
                f.dispose();
                System.exit(0);
            }
        });

        f.setLayout(null);

        f.setVisible(true);

    }
}
