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
import java.awt.event.*;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.text.*;
import javax.swing.border.*;


public class Gui {
    JFrame f = new JFrame();
    SpringLayout Layout = new SpringLayout();
    JPanel ContentPane = new JPanel();
    JLabel Headerlabel = new JLabel("Schulsanitätsdienst Materialmanagement");
    JTextField searchbar = new JTextField();
    JButton search = new JButton("Search");
    JButton printTable = new JButton("Table to pdf");
    JButton printMissing = new JButton("Missing to pdf");

    boolean hookPressed = false;
    JFrame frame = new JFrame();

    String[] choices = new String[10];

    String searched;
    int rowToMark;

    ImageIcon image = new ImageIcon("image.png");
    JLabel bild = new JLabel (image);

    Xml xml = new Xml();


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

            String[] column = {"ID", "Name", "Amount", "Category", "Bestbefore"};

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
        choices[0] = "Verbandsmaterial";
        choices[1] = "Sauerstoff";
        choices[2] = "Injektion";
        choices[3] = "Hygiene";
        choices[4] = "Sonstiges";
        choices[5] = "Kategorie 6";
        choices[6] = "Kategorie 7";
        choices[7] = "Kategorie 8";
        choices[8] = "Kategorie 9";
        choices[9] = "Kategorie 10";

        ImageIcon pls = new ImageIcon("plus.png");
        JButton plus = new JButton(pls);
        plus.setBackground(Color.green);
        ContentPane.add(plus);
        Layout.putConstraint(SpringLayout.EAST, plus, -80, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, plus, 100, SpringLayout.NORTH, ContentPane);
        plus.setPreferredSize(new Dimension(20,20));
        plus.setToolTipText("Click this button to add a new product");

        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        ImageIcon rew = new ImageIcon("renew.png");
        JButton renew = new JButton(rew);
        renew.setBackground(Color.green);
        ContentPane.add(renew);
        Layout.putConstraint(SpringLayout.EAST, renew, -55, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, renew, 100, SpringLayout.NORTH, ContentPane);
        renew.setPreferredSize(new Dimension(20,20));
        renew.setToolTipText("Click this button renew the table and data");

        renew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                restartProgram();
            }
        });

        ImageIcon repair= new ImageIcon("change.png");
        JButton change = new JButton(repair);
        change.setBackground(Color.orange);
        ContentPane.add(change);
        Layout.putConstraint(SpringLayout.EAST, change, -30, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, change, 100, SpringLayout.NORTH, ContentPane);
        change.setPreferredSize(new Dimension(20,20));
        change.setToolTipText("Click this button to change the data of a product");

        change.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeProduct();
            }
        });

        ImageIcon trash = new ImageIcon("trash.png");
        JButton delete = new JButton(trash);
        delete.setBackground(Color.red);
        ContentPane.add(delete);
        Layout.putConstraint(SpringLayout.EAST, delete, -5, SpringLayout.EAST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, delete, 100, SpringLayout.NORTH, ContentPane);
        delete.setPreferredSize(new Dimension(20,20));
        delete.setToolTipText("Click this button to delete a product");

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String deleteRowNr;
                deleteRowNr = JOptionPane.showInputDialog("Select - Type in Nr to delete:");
                int deleteRowNrInt = Integer.parseInt(deleteRowNr)+1;

                //xml.removeProduct(deleteRowNr);
                JOptionPane.showMessageDialog(frame, "Dieser Button hat keine oder eine fehlerhafte Funktion","Error", JOptionPane.ERROR_MESSAGE);

                xml.testPrintOut(deleteRowNrInt);
                restartProgram();
            }
        });

        //End of Testing area - Normal Code

        ContentPane.setLayout(Layout);
        ContentPane.setBackground(Color.WHITE);
        xml.overWrite();

        searchbar.setText(" ");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searched = searchbar.getText();
                if (searched == "" || searched == null || searched == " ") {
                    JOptionPane.showMessageDialog(frame, "Du hast nichts eingegeben!","Error", JOptionPane.ERROR_MESSAGE); //Doesn't work
                } else {
                    JOptionPane.showMessageDialog(frame, "Das hätte Marcel programmieren sollen, hat er aber nicht. Du hast folgendes gesucht: " + searched,"Info", JOptionPane.INFORMATION_MESSAGE);
                    for (int i = 0; i < (xml.length); i++) {
                        if (xml.id[i]==searched||xml.name[i]==searched||xml.amount[i]==searched||xml.category[i]==searched||xml.bestbefore[i]==searched) {
                            rowToMark = i;
                        }
                        //System.out.println(rowToMark);
                    }
                    searchbar.setText(" ");
                }
            }
        });

        TableExample();

        printTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Tabelle ausdrucken
                //f.print();
                JOptionPane.showMessageDialog(frame, "Dieser Button hat keine oder eine fehlerhafte Funktion","Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        printMissing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Fehlendes Material ausdrucken
                JOptionPane.showMessageDialog(frame, "Dieser Button hat keine oder eine fehlerhafte Funktion","Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        search.setBackground(Color.white);
        search.setToolTipText("Search for Product Name");
        printTable.setBackground(Color.YELLOW);
        printMissing.setBackground(Color.YELLOW);

        Headerlabel.setBackground(Color.red);
        Headerlabel.setFont(new Font("Sans", Font.BOLD, 30));

        ContentPane.add(searchbar);
        ContentPane.add(search);
        ContentPane.add(printTable);
        ContentPane.add(printMissing);
        ContentPane.add(bild);
        ContentPane.add(Headerlabel);

        f.setSize(1150, 720);

        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Headerlabel, 0, SpringLayout.HORIZONTAL_CENTER, ContentPane);
        Layout.putConstraint(SpringLayout.WEST, searchbar, 30, SpringLayout.WEST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, searchbar, 100, SpringLayout.NORTH, ContentPane);
        searchbar.setPreferredSize(new Dimension(130, 20));
        Layout.putConstraint(SpringLayout.WEST, search, 84, SpringLayout.WEST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, search, 120, SpringLayout.NORTH, ContentPane);
        Layout.putConstraint(SpringLayout.WEST, printTable, 30, SpringLayout.WEST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, printTable, 160, SpringLayout.NORTH, ContentPane);
        Layout.putConstraint(SpringLayout.WEST, printMissing, 30, SpringLayout.WEST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, printMissing, 185, SpringLayout.NORTH, ContentPane);
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

    public void addProduct() {
        JFrame j = new JFrame("Produkt hinzufügen");
        JPanel p1 = new JPanel();
        JLabel header = new JLabel("Add Product");
        JLabel sth = new JLabel("                     ");
        JLabel l0 = new JLabel("ID");
        JLabel l1 = new JLabel("Name");
        JLabel l2 = new JLabel("Amount");
        JLabel l4 = new JLabel("Category");
        JLabel l3 = new JLabel("Bestbefore");
        JLabel f0 = new JLabel("");
        JTextField f1 = new JTextField();
        JTextField f2 = new JTextField();
        JTextField f3 = new JTextField();
        JComboBox f4 = new JComboBox(choices);

        JButton b1 = new JButton("Add");
        JButton b2 = new JButton("Abbrechen");

        p1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.VERTICAL;

        c.gridx = 1;
        c.gridy = 0;
        p1.add(header, c);

        c.gridx = 1;
        c.gridy = 1;
        p1.add(sth, c);

        c.gridx = 0;
        c.gridy = 2;
        p1.add(l0, c);

        c.gridx = 1;
        c.gridy = 2;
        p1.add(f0, c);

        c.gridx = 0;
        c.gridy = 3;
        p1.add(l1, c);

        c.gridx = 1;
        c.gridy = 3;
        p1.add(f1, c);

        c.gridx = 0;
        c.gridy = 4;
        p1.add(l2, c);

        c.gridx = 1;
        c.gridy = 4;
        p1.add(f2, c);


        c.gridx = 0;
        c.gridy = 5;
        p1.add(l4, c);

        c.gridx = 1;
        c.gridy = 5;
        p1.add(f4, c);

        c.gridx = 0;
        c.gridy = 6;
        p1.add(l3, c);


        c.gridx = 1;
        c.gridy = 6;
        p1.add(f3, c);

        c.gridx = 0;
        c.gridy = 7;
        p1.add(b1, c);

        c.gridx = 1;
        c.gridy = 7;
        p1.add(b2, c);

        String s = String.valueOf(xml.length-1);
        f0.setText("Nr. "+s);
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

    public void changeProduct() {
        JFrame j = new JFrame("Produkt ändern");
        JPanel p1 = new JPanel();
        JLabel header = new JLabel("Change Product");
        JLabel sth = new JLabel("                     ");
        JLabel l0 = new JLabel("ID");
        JLabel l1 = new JLabel("Name");
        JLabel l2 = new JLabel("Amount");
        JLabel l3 = new JLabel("Category");
        JLabel l4 = new JLabel("Bestbefore");
        JComboBox f1 = new JComboBox(xml.id);
        JTextField f2 = new JTextField();
        JTextField f3 = new JTextField();
        JLabel f4 = new JLabel("");
        JTextField f5 = new JTextField();

        JButton b1 = new JButton("Change");
        JButton b2 = new JButton("Cancel");
        ImageIcon hook = new ImageIcon("hook.png");
        JButton b3 = new JButton(hook);

        p1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.VERTICAL;

        c.gridx = 1;
        c.gridy = 0;
        p1.add(header, c);

        c.gridx = 1;
        c.gridy = 1;
        p1.add(sth, c);

        c.gridx = 0;
        c.gridy = 2;
        p1.add(l0, c);

        c.gridx = 1;
        c.gridy = 2;
        p1.add(f1, c);

        c.gridx = 0;
        c.gridy = 3;
        p1.add(l1, c);

        c.gridx = 1;
        c.gridy = 3;
        p1.add(f2, c);


        c.gridx = 0;
        c.gridy = 4;
        p1.add(l2, c);

        c.gridx = 1;
        c.gridy = 4;
        p1.add(f3, c);

        c.gridx = 0;
        c.gridy = 5;
        p1.add(l3, c);


        c.gridx = 1;
        c.gridy = 5;
        p1.add(f4, c);

        c.gridx = 0;
        c.gridy = 6;
        p1.add(l4, c);


        c.gridx = 1;
        c.gridy = 6;
        p1.add(f5, c);

        c.gridx = 0;
        c.gridy = 7;
        p1.add(b1, c);

        c.gridx = 1;
        c.gridy = 7;
        p1.add(b2, c);

        c.gridx = 2;
        c.gridy = 2;
        p1.add(b3, c);

        f1.setPreferredSize(new Dimension(130, 20));
        f1.setSelectedItem(xml.id[2]);
        f2.setPreferredSize(new Dimension(130, 20));
        f2.setText(xml.name[2]);
        f3.setPreferredSize(new Dimension(130, 20));
        f3.setText(xml.amount[2]);
        f4.setPreferredSize(new Dimension(130, 20));
        f4.setBackground(Color.white);
        f4.setText(xml.category[2]);
        f5.setPreferredSize(new Dimension(130, 20));
        f5.setBackground(Color.white);
        f5.setText(xml.bestbefore[2]);
        b1.setBackground(Color.green);
        b1.setPreferredSize(new Dimension(130, 25));
        b2.setBackground(Color.red);
        b2.setPreferredSize(new Dimension(130, 25));
        b3.setBackground(Color.white);
        b3.setPreferredSize(new Dimension(20, 20));

        j.add(p1);
        p1.setVisible(true);
        j.setSize(500, 500);
        j.setVisible(true);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (hookPressed==true) {
                    Xml xml = new Xml();

                    xml.changeProduct("" + f1.getSelectedItem(), f2.getText(), f3.getText(), f4.getText());
                    xml.overWrite();

                    p1.setVisible(false);
                    j.setSize(0, 0);
                    j.setVisible(false);

                    restartProgram();
                } else {
                    JOptionPane.showMessageDialog(frame , "You have to press the hook button first","Error",JOptionPane.ERROR_MESSAGE);
                }
            }

        });


        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                p1.setVisible(false);
                j.setSize(0, 0);
                j.setVisible(false);
                restartProgram();
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            try {
                int guiID = Integer.parseInt(""+f1.getSelectedItem())+1;
                System.out.println(guiID);
                f2.setText(xml.name[guiID]);
                f3.setText(xml.amount[guiID]);
                f4.setText(xml.category[guiID]);
                f5.setText(xml.bestbefore[guiID]);
                hookPressed = true;
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(frame , "Du musst eine gültige Id auswählen!","Error",JOptionPane.ERROR_MESSAGE);
            }
            }
        });
    }

    public void restartProgram() {
        xml.overWrite();

        f.setSize(0,0);
        f.setVisible(false);

        Gui g1 = new Gui();
        g1.Gui();
    }
}
