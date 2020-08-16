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
import javax.swing.table.DefaultTableModel;


public class Gui {
    JFrame f = new JFrame();
    SpringLayout Layout = new SpringLayout();
    JPanel ContentPane = new JPanel();
    JLabel Headerlabel = new JLabel("Schulsanitätsdienst Materialmanagement");
    JButton Create_New = new JButton("Neues Product");
    //JButton new_category = new JButton("Neue Kategorie");
    JButton renew = new JButton("Aktualisieren");

    String[] choices = new String[10];

    ImageIcon image = new ImageIcon("image.png");
    JLabel bild = new JLabel (image);

    Xml xml = new Xml();

    public void add_product() {
        JFrame j = new JFrame("Produkt hinzufügen");
        JPanel p1 = new JPanel();
        JLabel l1 = new JLabel("Name");
        JLabel l2 = new JLabel("Amount");
        JLabel l4 = new JLabel("Category");
        JLabel l3 = new JLabel("Bestbefore");
        JTextField f1 = new JTextField();
        JTextField f2 = new JTextField();
        JTextField f3 = new JTextField();

        JComboBox f4 = new JComboBox(choices);
        JButton b1 = new JButton("Aktualisieren");
        JButton b2 = new JButton("Abbrechen");

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
        p1.add(l4, c);

        c.gridx = 1;
        c.gridy = 2;
        p1.add(f4, c);

        c.gridx = 0;
        c.gridy = 3;
        p1.add(l3, c);


        c.gridx = 1;
        c.gridy = 3;
        p1.add(f3, c);

        c.gridx = 0;
        c.gridy = 4;
        p1.add(b1, c);

        c.gridx = 1;
        c.gridy = 4;
        p1.add(b2, c);

        f1.setPreferredSize(new Dimension(130, 20));
        f2.setPreferredSize(new Dimension(130, 20));
        f3.setPreferredSize(new Dimension(130, 20));
        f4.setPreferredSize(new Dimension(130, 20));
        f4.setBackground(Color.white);
        b1.setBackground(Color.green);
        b1.setPreferredSize(new Dimension(130, 25));
        b2.setBackground(Color.red);
        b2.setPreferredSize(new Dimension(130, 25));

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

                restartProgram();
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
                System.out.println(newCategory);
                for (int i = 1; i < 10; i++) {
                    if (choices[i] == "" || choices[i] == null) {
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
            table.setBounds(40, 40, 400, 600);
            table.setPreferredSize(new Dimension(800,10000));
            Layout.putConstraint(SpringLayout.EAST, table, -150, SpringLayout.EAST, ContentPane);
            Layout.putConstraint(SpringLayout.NORTH, table, 100, SpringLayout.NORTH, ContentPane);
            ContentPane.add(table);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void Gui() {
        //Auswahl muss per Hand festgelegt werden
        choices[0] = "Kategorie 1";
        choices[1] = "Kategorie 2";
        choices[2] = "Kategorie 3";
        choices[3] = "Kategorie 4";
        choices[4] = "Kategorie 5";
        choices[5] = "Kategorie 6";
        choices[6] = "Kategorie 7";
        choices[7] = "Kategorie 8";
        choices[8] = "Kategorie 9";
        choices[9] = "Kategorie 10";

        //Testing area for Buttons: add, sub, change and delete - nr is row of data
        ImageIcon pls = new ImageIcon("plus.png");
        JButton plus = new JButton(pls);
        plus.setBackground(Color.green);
        ContentPane.add(plus);
        Layout.putConstraint(SpringLayout.EAST, plus, -80, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, plus, 100, SpringLayout.NORTH, ContentPane);
        plus.setPreferredSize(new Dimension(20,20));

        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String changeRowNR;
                changeRowNR = JOptionPane.showInputDialog("Type in the Nr of the row to change:");
                String changeAmountPlus;
                changeAmountPlus = JOptionPane.showInputDialog("How much do you want to add:");
                int changeRowInt = Integer.parseInt(changeRowNR);
                int changeAmountPlusInt = Integer.parseInt(changeAmountPlus);
            }
        });

        ImageIcon mis = new ImageIcon("minus.png");
        JButton minus = new JButton(mis);
        minus.setBackground(Color.red);
        ContentPane.add(minus);
        Layout.putConstraint(SpringLayout.EAST, minus, -55, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, minus, 100, SpringLayout.NORTH, ContentPane);
        minus.setPreferredSize(new Dimension(20,20));

        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String changeRowNR;
                changeRowNR = JOptionPane.showInputDialog("Type in the Nr of the row to change:");
                String changeAmountMinus;
                changeAmountMinus = JOptionPane.showInputDialog("How much do you want to sub:");
                int changeRowInt = Integer.parseInt(changeRowNR);
                int changeAmountMinusInt = Integer.parseInt(changeAmountMinus);
            }
        });

        ImageIcon repair1= new ImageIcon("change.png");
        JButton change = new JButton(repair1);
        change.setBackground(Color.orange);
        ContentPane.add(change);
        Layout.putConstraint(SpringLayout.EAST, change, -30, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, change, 100, SpringLayout.NORTH, ContentPane);
        change.setPreferredSize(new Dimension(20,20));

        change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String changeRowNR;
                changeRowNR = JOptionPane.showInputDialog("Type in the Nr of the row to change:");
                String changeBestbeforeTo;
                changeBestbeforeTo = JOptionPane.showInputDialog("To what Date you want to change:");
                int changeRowInt = Integer.parseInt(changeRowNR);
                int changeBestbeforeInt = Integer.parseInt(changeBestbeforeTo);
            }
        });

        ImageIcon trash = new ImageIcon("trash.png");
        JButton delete = new JButton(trash);
        delete.setBackground(Color.red);
        ContentPane.add(delete);
        Layout.putConstraint(SpringLayout.EAST, delete, -5, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, delete, 100, SpringLayout.NORTH, ContentPane);
        delete.setPreferredSize(new Dimension(20,20));

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String deletRowNr;
                deletRowNr = JOptionPane.showInputDialog("Type in Row to delete:");
                int changeRowInt = Integer.parseInt(deletRowNr);
            }
        });


        //Normal Code

        ContentPane.setLayout(Layout);
        ContentPane.setBackground(Color.WHITE);
        xml.overWrite();

        Create_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                add_product();
            }
        });

        renew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartProgram();
            }
        });

        /*new_category.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //ToDo
            }
        });*/

        TableExample();

        Create_New.setBackground(Color.orange);
        renew.setBackground(Color.green);
        //new_category.setBackground(Color.orange);

        Headerlabel.setBackground(Color.red);
        Headerlabel.setFont(new Font("Sans", Font.BOLD, 30));

        ContentPane.add(Create_New);
        //ContentPane.add(new_category);
        ContentPane.add(renew);
        ContentPane.add(bild);
        ContentPane.add(Headerlabel);

        f.setSize(1150, 720);

        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Headerlabel, 0, SpringLayout.HORIZONTAL_CENTER, ContentPane);
        Layout.putConstraint(SpringLayout.WEST, Create_New, 50, SpringLayout.WEST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, Create_New, 100, SpringLayout.NORTH, ContentPane);
        //Layout.putConstraint(SpringLayout.WEST, new_category, 180, SpringLayout.WEST, ContentPane);
        //Layout.putConstraint(SpringLayout.NORTH, new_category, 130, SpringLayout.NORTH, ContentPane);
        Layout.putConstraint(SpringLayout.WEST, renew, 50, SpringLayout.WEST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, renew, 130, SpringLayout.NORTH, ContentPane);
        Layout.putConstraint(SpringLayout.EAST, bild, 0, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, bild, 0, SpringLayout.NORTH, ContentPane);

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

    public void restartProgram() {
        f.setSize(0,0);
        f.setVisible(false);

        Gui g1 = new Gui();
        g1.Gui();
    }
}
