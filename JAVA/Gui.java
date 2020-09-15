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

import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import javax.swing.JTable;
import javax.swing.table.*;

public class Gui{
    //erste Variablen werden initialisiert und der Frame erstellt
    JFrame f = new JFrame();
    BorderLayout Layout = new BorderLayout();
    GridLayout grid=new GridLayout(1,4,0,20);
    GridLayout links=new GridLayout(5,1,0,0);
    JPanel rechteSeite=new JPanel();
    JPanel linkeSeite=new JPanel();
    JPanel linerContent=new JPanel(new BorderLayout());
    JPanel rehterContent=new JPanel(new BorderLayout());
    JPanel Zeilenplatzhalter=new JPanel();
    JPanel North=new JPanel(new BorderLayout());
    JPanel NorthStream=new JPanel(new BorderLayout());
    JPanel ContentPane = new JPanel();
    JLabel Headerlabel = new JLabel("Schulsanitätsdienst Materialmanagement");
    JTextField searchbar = new JTextField();
    JButton search = new JButton("Search");
    JButton printTable = new JButton("Print");
    JButton printMissing = new JButton("Missing Products");
    JScrollPane scroll=new JScrollPane();
    JButton b=new JButton("West");
    JTable tablee=new JTable();

    boolean hookPressed = false;
    JFrame frame = new JFrame();

    String[] choices = new String[10];

    String searched;
    int[] markX = new int[2];
    int[] markY = new int[2];

    ImageIcon image = new ImageIcon("image.png");
    JLabel bild = new JLabel (image);

    Xml xml = new Xml();
    private PrintRequestAttributeSet attr;

    private void print(JTable table) {
        if (attr == null) {
            attr = new HashPrintRequestAttributeSet();
            float leftMargin = 10;
            float rightMargin = 10;
            float topMargin = 10;
            float bottomMargin = 10;
            attr.add(OrientationRequested.PORTRAIT);
            attr.add(MediaSizeName.ISO_A4);
            MediaSize mediaSize = MediaSize.ISO.A4;
            float mediaWidth = mediaSize.getX(Size2DSyntax.MM);
            float mediaHeight = mediaSize.getY(Size2DSyntax.MM);
            attr.add(new MediaPrintableArea(
                    leftMargin, topMargin,
                    (mediaWidth - leftMargin - rightMargin),
                    (mediaHeight - topMargin - bottomMargin), Size2DSyntax.MM));
        }
        try {
            table.print(JTable.PrintMode.FIT_WIDTH, null, null, true, attr, true);
        } catch (PrinterException ex) {
            ex.printStackTrace();
        } catch (HeadlessException ex) {
            ex.printStackTrace();
        }
    }

    //Tabelle wird erstellt
    public JTable TableExample() {
        try {
            String[][] data = new String[xml.length][6];
            for (int i = 0; i < (xml.length); i++) {
                data[i][0] = xml.id[i];
                data[i][1] = xml.name[i];
                data[i][2] = xml.amount[i];
                data[i][3] = xml.amountNeeded[i];
                data[i][4] = xml.category[i];
                data[i][5] = xml.bestbefore[i];
            }

            String[] column = {"ID", "Name", "Amount", "AmountNeeded", "Category", "Bestbefore"};

            DefaultTableModel tableModel = new DefaultTableModel(data, column) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }
            };

            //Test1
            class MyRenderer extends DefaultTableCellRenderer
            {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column)
                {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                    if (! table.isRowSelected(row))
                    {
                        if (markX[1]!=0&&markY[1]!=0&&markX[1]!=1&&markY[1]!=1) {
                            if (row == markY[1] && column == markX[1])
                                c.setBackground(Color.yellow);
                            else
                                c.setBackground(Color.white);
                        }
                    }
                    return c;
                }

            }

            JTable table = new JTable(tableModel);
            MyRenderer myRenderer = new MyRenderer();
            table.setDefaultRenderer(Object.class, myRenderer);

            //Test1 - Ende


            table.setBounds(40, 40, 400, 600);
            table.setPreferredSize(new Dimension(800,10000));
            scroll.setPreferredSize(new Dimension(800, 600));
            scroll.setViewportView(table);
            return table;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    //GUI wird erstellt
    public void GuiInit() {

        //Auswahl der Kategorien - wird per Hand bearbeitet
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

        //Buttons auf der rechten Seite werden erstellt
        ImageIcon pls = new ImageIcon("plus.png");
        JButton plus = new JButton(pls);
        plus.setBackground(Color.green);
        rechteSeite.add(plus);
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
        rechteSeite.add(renew);
        renew.setPreferredSize(new Dimension(20,20));
        renew.setToolTipText("Click this button renew the table and data");

        renew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //resetColors();
                restartProgram();
            }
        });

        ImageIcon repair= new ImageIcon("change.png");
        JButton change = new JButton(repair);
        change.setBackground(Color.orange);
        rechteSeite.add(change);
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
        rechteSeite.add(delete);
        delete.setPreferredSize(new Dimension(20,20));
        delete.setToolTipText("Click this button to delete a product");

        rechteSeite.setBackground(Color.white);
        rechteSeite.setLayout(grid);


        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String deleteRowNr;
                deleteRowNr = JOptionPane.showInputDialog("Select - Type in Nr to delete:");

                if (JOptionPane.showConfirmDialog(null, "Sicher, dass du die Reihe "+deleteRowNr+" löschen möchtest?", "WARNING",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    Xml xml=new Xml();

                    xml.removeProduct(deleteRowNr);

                    xml.overWrite();
                    restartProgram();
                } else {
                    // no option
                }
            }
        });


        //Buttons links werden erstellt
        searchbar.setText(" ");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //try {
                searched = searchbar.getText();
                if (searched == "" || searched == null || searched == " ") {
                    JOptionPane.showMessageDialog(frame, "Du hast nichts eingegeben!","Error", JOptionPane.ERROR_MESSAGE); //Doesn't work
                } else {
                    System.out.println("Checkpoint 1: "+markX + " "+ markY);
                    //JOptionPane.showMessageDialog(frame, "Das hätte Marcel programmieren sollen, hat er aber nicht. Du hast folgendes gesucht: " + searched,"Info", JOptionPane.INFORMATION_MESSAGE);
                    for (int i = 2; i < (xml.length); i++) {
                        int tmp1 = Integer.parseInt(xml.id[i].trim());
                        int tmp2 = Integer.parseInt(searched.trim());
                        if (tmp1==tmp2) {
                            System.out.println("Ja");
                            markX[1]=3;
                            markY[1]=3;

                        } else {
                            System.out.println("Nö");
                        }
                        System.out.println(tmp1);
                        System.out.println(tmp2);
                        System.out.println("Checkpoint 2: "+markX + " "+ markY);
                        //||xml.name[i]==searched||xml.amount[i]==searched||xml.category[i]==searched||xml.bestbefore[i]==searched
                        //System.out.println(rowToMark);
                    }
                    restartProgram();
                    searchbar.setText(" ");
                    System.out.println("Checkpoint 3: "+markX + " "+ markY);
                }
            }
        });

        tablee=TableExample();

        printTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Tabelle ausdrucken
                try{
                print(tablee);}
                catch (Exception v){
                JOptionPane.showMessageDialog(frame, "Dieser Button hat keine oder eine fehlerhafte Funktion","Error", JOptionPane.ERROR_MESSAGE);
            }}
        });

        printMissing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Fehlendes Material ausdrucken
                missingProducts();
                //JOptionPane.showMessageDialog(frame, "Dieser Button hat keine oder eine fehlerhafte Funktion","Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        search.setBackground(Color.white);
        search.setToolTipText("Search for Product Name");
        printTable.setBackground(Color.YELLOW);
        printMissing.setBackground(Color.YELLOW);

        //Alles wird zum Layout hinzugefügt
        scroll.setBackground(Color.WHITE);
        ContentPane.setLayout(new BorderLayout());
        ContentPane.setBackground(Color.WHITE);
        xml.overWrite();

        Headerlabel.setBackground(Color.red);
        Headerlabel.setFont(new Font("Sans", Font.BOLD, 30));

        NorthStream.add(Headerlabel, BorderLayout.CENTER);
        North.add(bild, BorderLayout.EAST);
        NorthStream.setBackground(Color.white);
        North.add(NorthStream, BorderLayout.CENTER);
        ContentPane.add(North, BorderLayout.NORTH);

        f.setSize(1150, 720);

        linkeSeite.add(searchbar);
        searchbar.setPreferredSize(new Dimension(130, 20));
        linkeSeite.add(search);
        Zeilenplatzhalter.setSize(new Dimension(130,10));
        linkeSeite.add(Zeilenplatzhalter);
        Zeilenplatzhalter.setBackground(Color.white);
        linkeSeite.add(printTable);
        linkeSeite.add(printMissing);
        linerContent.setBackground(Color.WHITE);
        linerContent.setSize(new Dimension(170,50));
        linerContent.add(linkeSeite, BorderLayout.NORTH);
        rehterContent.setBackground(Color.WHITE);
        rehterContent.setSize(new Dimension(120, 20));
        rehterContent.add(rechteSeite, BorderLayout.NORTH);
        ContentPane.add(linerContent, BorderLayout.WEST);
        ContentPane.add(rehterContent, BorderLayout.EAST);
        ContentPane.add(scroll, BorderLayout.CENTER);
        linkeSeite.setLayout(links);

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
        //f.pack();
        f.setVisible(true);
    }

    //Funktion für die GUI fürs hinzufügen eines neuen Produktes wird erstellt
    public void addProduct() {
        //Bestandteile werden initialisiert
        JFrame j = new JFrame("Produkt hinzufügen");
        JPanel p1 = new JPanel();
        JLabel header = new JLabel("Add Product");
        JLabel sth = new JLabel("                     ");
        JLabel l0 = new JLabel("ID");
        JLabel l1 = new JLabel("Name");
        JLabel l2 = new JLabel("Amount");
        JLabel l21 = new JLabel("AmountNeeded");
        JLabel l4 = new JLabel("Category");
        JLabel l3 = new JLabel("Bestbefore");
        JLabel f0 = new JLabel("");
        JTextField f1 = new JTextField();
        JTextField f2 = new JTextField();
        JTextField f21 = new JTextField();
        JTextField f3 = new JTextField();
        JComboBox f4 = new JComboBox(choices);

        JButton b1 = new JButton("Add");
        JButton b2 = new JButton("Abbrechen");

        //Layout wird festgelegt
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
        p1.add(l21, c);

        c.gridx = 1;
        c.gridy = 5;
        p1.add(f21, c);


        c.gridx = 0;
        c.gridy = 6;
        p1.add(l4, c);

        c.gridx = 1;
        c.gridy = 6;
        p1.add(f4, c);

        c.gridx = 0;
        c.gridy = 7;
        p1.add(l3, c);


        c.gridx = 1;
        c.gridy = 7;
        p1.add(f3, c);

        c.gridx = 0;
        c.gridy = 8;
        p1.add(b1, c);

        c.gridx = 1;
        c.gridy = 8;
        p1.add(b2, c);

        String s = String.valueOf(xml.length-1);
        f0.setText("Nr. "+s);
        f1.setPreferredSize(new Dimension(130, 20));
        f2.setPreferredSize(new Dimension(130, 20));
        f21.setPreferredSize(new Dimension(130, 20));
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

        //Button funktionen werden eingefügt

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Xml xml=new Xml();

                xml.addProduct(f1.getText(),f2.getText(),f21.getText(), f3.getText(),"" + f4.getSelectedItem());
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

    //Funktion für die GUI zum ändern von Produkten wird erstellt
    public void changeProduct() {
        //Bestandteile werden initialisiert
        JFrame j = new JFrame("Produkt ändern");
        JPanel p1 = new JPanel();
        JLabel header = new JLabel("Change Product");
        JLabel sth = new JLabel("                     ");
        JLabel l0 = new JLabel("ID");
        JLabel l1 = new JLabel("Name");
        JLabel l2 = new JLabel("Amount");
        JLabel l21 = new JLabel("AmountNeeded");
        JLabel l3 = new JLabel("Category");
        JLabel l4 = new JLabel("Bestbefore");
        JComboBox f1 = new JComboBox(xml.id);
        JTextField f2 = new JTextField();
        JTextField f21 = new JTextField();
        JTextField f3 = new JTextField();
        JLabel f4 = new JLabel("");
        JTextField f5 = new JTextField();

        JButton b1 = new JButton("Change");
        JButton b2 = new JButton("Cancel");
        ImageIcon hook = new ImageIcon("hook.png");
        JButton b3 = new JButton(hook);

        //Layout wird kreiert
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
        p1.add(l21, c);

        c.gridx = 1;
        c.gridy = 5;
        p1.add(f21, c);

        c.gridx = 0;
        c.gridy = 6;
        p1.add(l3, c);


        c.gridx = 1;
        c.gridy = 6;
        p1.add(f4, c);

        c.gridx = 0;
        c.gridy = 7;
        p1.add(l4, c);


        c.gridx = 1;
        c.gridy = 7;
        p1.add(f5, c);

        c.gridx = 0;
        c.gridy = 8;
        p1.add(b1, c);

        c.gridx = 1;
        c.gridy = 8;
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
        f21.setPreferredSize(new Dimension(130, 20));
        f21.setText(xml.amountNeeded[2]);
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

        //Button funktionen werden eingefügt
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (hookPressed==true) {
                    Xml xml = new Xml();

                    xml.changeProduct("" + f1.getSelectedItem(), f2.getText(), f3.getText(), f21.getText(), f5.getText(), f4.getText());
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
                f21.setText(xml.amountNeeded[guiID]);
                f4.setText(xml.category[guiID]);
                f5.setText(xml.bestbefore[guiID]);
                hookPressed = true;
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(frame , "Du musst eine gültige Id auswählen!","Error",JOptionPane.ERROR_MESSAGE);
            }
            }
        });
    }

    //Restart-Funktion
    public void restartProgram() {
        xml.overWrite();

        f.setSize(0,0);
        f.setVisible(false);

        Gui g1 = new Gui();
        g1.GuiInit();
    }

    public void resetColors() {
        xml.overWrite();

        f.setSize(0,0);
        f.setVisible(false);

        markY[1] = 1;
        markX[1] = 1;

        Gui g1 = new Gui();
        g1.GuiInit();
    }

    //Extra Stuff - in Bearbeitung
    public void missingProducts () {
        JFrame f1 = new JFrame();
        SpringLayout Layout1 = new SpringLayout();
        JPanel ContentPane1 = new JPanel();
        JLabel Headerlabel1 = new JLabel("Benötigtes Material");
        JButton print = new JButton("Print");


        int AnzahlZuwenig=0;
        for (int tmp = 2; tmp < xml.length; tmp++) {
            int x = Integer.parseInt(xml.amount[tmp]);
            int y = Integer.parseInt(xml.amountNeeded[tmp]);
            if (x > y) {
                AnzahlZuwenig++;
            }
        }

        String[][] missing = new String[xml.length-AnzahlZuwenig][3];
        int geloeschtCounter=0;
        for (int tmp = 2; tmp < xml.length; tmp++) {
            int x = Integer.parseInt(xml.amount[tmp]);
            int y = Integer.parseInt(xml.amountNeeded[tmp]);
            int diff;
            if (x < y) {
                diff = x - y;
                diff =diff-2*diff;
                String diffConv = String.valueOf(diff);
                System.out.println(diff);
                missing[tmp-geloeschtCounter][0] = xml.id[tmp];
                missing[tmp-geloeschtCounter][1] = xml.name[tmp];
                missing[tmp-geloeschtCounter][2] = diffConv;
            }
            else
            {
                geloeschtCounter++;
            }
        }
            try {
                String[][] data = new String[xml.length-AnzahlZuwenig][3];
                for (int i = 2; i < (xml.length-AnzahlZuwenig); i++) {
                    data[i][0] = missing[i][0];
                    data[i][1] = missing[i][1];
                    data[i][2] = missing[i][2];
                }
                data[0][0] = "ID";
                data[0][1] = "Name";
                data[0][2] = "Benötigte Anzahl";

                String[] column = {"ID", "Name", "Differenz"};

                DefaultTableModel tableModel = new DefaultTableModel(data, column) {

                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //all cells false
                        return false;
                    }
                };

                JTable table1 = new JTable(tableModel);
                table1.setBounds(40, 40, 400, 600);
                table1.setPreferredSize(new Dimension(800, 10000));
                Layout1.putConstraint(SpringLayout.EAST, table1, 150, SpringLayout.EAST, ContentPane1);
                Layout1.putConstraint(SpringLayout.NORTH, table1, 150, SpringLayout.NORTH, ContentPane1);
                ContentPane1.add(table1);
                print.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try{
                            print(table1);
                        }
                        catch (Exception y){
                            JOptionPane.showMessageDialog(frame, "Dieser Button hat keine oder eine fehlerhafte Funktion","Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

            } catch (Exception e) {
                System.out.println(e);
            }

        Headerlabel1.setBackground(Color.red);
        Headerlabel1.setFont(new Font("Sans", Font.BOLD, 30));

        ContentPane1.add(Headerlabel1);
        ContentPane1.add(print);

        f1.setSize(1150, 720);
        f1.setBackground(Color.white);

        Layout1.putConstraint(SpringLayout.HORIZONTAL_CENTER, Headerlabel1, 0, SpringLayout.HORIZONTAL_CENTER, ContentPane1);
        Layout1.putConstraint(SpringLayout.WEST, print, 0, SpringLayout.WEST, ContentPane1);
        Layout1.putConstraint(SpringLayout.NORTH, print, 150, SpringLayout.NORTH, ContentPane1);

        f1.add(ContentPane1);
        f1.setVisible(true);
        f1.setContentPane(ContentPane1);

        f1.setLayout(null);
        f1.setVisible(true);
        //System.out.println("Checkpoint 4: "+markX[1] + " "+ markY[1]);

    }
}
