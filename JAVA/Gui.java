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
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;


public class Gui{
    //erste Variablen werden initialisiert und der Frame erstellt
    JFrame f = new JFrame();
    GridLayout grid=new GridLayout(1,4,0,20);
    GridLayout links=new GridLayout(6,1,0,0);
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
    String lastDate ="-";
    JLabel lastChange = new JLabel("<html>Letzte Änderung:<br/>"+lastDate+"</html>");
    JScrollPane scroll=new JScrollPane();
    JTable tablee=new JTable();
    JMenuBar bar=new JMenuBar();
    JMenu file=new JMenu("File");
    JMenuItem exportXml=new JMenuItem("export xml");
    JMenuItem importXml=new JMenuItem("import xml");

    JFrame frame = new JFrame();

    String searched;
    String[] categories = new String[7];
    String[] units = new String[5];
    ImageIcon image = new ImageIcon("image.png");
    JLabel bild = new JLabel (image);

    Xml xml = new Xml();
    private PrintRequestAttributeSet attr;

    //Tabelle wird erstellt
    public JTable TableExample() {
        try {
            String[][] data = new String[xml.length][7];
            for (int i = 0; i < (xml.length); i++) {
                data[i][0] = xml.id[i];
                data[i][1] = xml.name[i];
                data[i][2] = xml.amount[i];
                data[i][3] = xml.amountNeeded[i];
                data[i][4] = xml.unit[i];
                data[i][5] = xml.category[i];
                data[i][6] = xml.bestbefore[i];
            }

            String[] column = {"ID", "Name", "Anzahl", "Benötigt","Einheit", "Kategorie", "Ablaufdatum"};//-

            DefaultTableModel tableModel = new DefaultTableModel(data, column) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    return false;
                }
            };

            JTable table = new JTable(tableModel);
            table.setSelectionBackground(Color.yellow);

            ListSelectionModel cellSelectionModel = table.getSelectionModel();
            cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            cellSelectionModel.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    String selectedData = null;


                    int[] selectedRow = table.getSelectedRows();
                    int[] selectedColumns = table.getSelectedColumns();
                    int dataRow = 0;

                    for (int i = 0; i < selectedRow.length; i++) {
                        for (int j = 0; j < selectedColumns.length; j++) {
                            selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);
                            dataRow = Integer.valueOf(xml.id[selectedRow[i]].trim());
                        }
                    }
                    //changeProduct(dataRow);
                }
            });



            table.setAutoCreateRowSorter(true);
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);
            ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();

            int columnIndexToSort = 0;
            sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
            int columnIndexForJob = 0;
            sortKeys.add(new RowSorter.SortKey(columnIndexForJob, SortOrder.ASCENDING));

            int columnIndexForName = 4;
            sortKeys.add(new RowSorter.SortKey(columnIndexForName, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);
            sorter.sort();

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
        try {
            File file = new File("date.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            lastDate = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastChange.setText("<html>Letzte Änderung:<br/>"+lastDate+"</html>");

        bar.add(file);
        file.add(exportXml);
        file.add(importXml);

        //Auswahl der Kategorien - wird per Hand bearbeitet
        categories[0] = "Verbandsmaterial";
        categories[1] = "Sauerstoff";
        categories[2] = "Injektion";
        categories[3] = "Hygiene";
        categories[4] = "Sonstiges";
        categories[5] = "Kategorie 6";
        categories[6] = "Kategorie 7";

        units[0] = "Stück/e";
        units[1] = "Packung/en";
        units[2] = "Kiste/n";
        units[3] = "Sonstiges";

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

        exportXml.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("export");
                JFileChooser chooser = new JFileChooser();
                // Dialog zum Oeffnen von Dateien anzeigen
                int rueckgabeWert = chooser.showSaveDialog(f);

                /* Abfrage, ob auf "Öffnen" geklickt wurde */
                if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
                {


                    ArrayList<String> allLines = new ArrayList<String>();
                    try {
                        allLines=(ArrayList) Files.readAllLines(Paths.get("Database.xml"));
                    }
                    catch(Exception q){}
                    File filename;
                    if(chooser.getSelectedFile().getName().endsWith(".xml"))
                    {try(FileWriter fw = new FileWriter(chooser.getSelectedFile())) {
                        BufferedWriter bw = new BufferedWriter(fw);
                        for (int i = 0; i < allLines.size(); i++) {
                            bw.write(allLines.get(i).toString());
                        }
                        bw.flush();
                        bw.close();
                    }
                    catch(Exception ex){}}
                    else{try(FileWriter fw = new FileWriter(chooser.getSelectedFile()+".xml")) {
                        BufferedWriter bw = new BufferedWriter(fw);
                        for (int i = 0; i < allLines.size(); i++) {
                            bw.write(allLines.get(i).toString());
                        }
                        bw.flush();
                        bw.close();
                    }
                    catch(Exception ex){}}


                }
            }
        });

        importXml.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("import");
                JFileChooser chooser = new JFileChooser();
                // Dialog zum Oeffnen von Dateien anzeigen
                int rueckgabeWert = chooser.showOpenDialog(f);

                /* Abfrage, ob auf "Öffnen" geklickt wurde */
                if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
                {


                    ArrayList<String> allLines = new ArrayList<String>();
                    try {
                        allLines=(ArrayList) Files.readAllLines(Paths.get(chooser.getCurrentDirectory().getAbsolutePath()+"/"+chooser.getSelectedFile().getName()));
                        System.out.println(Paths.get(chooser.getCurrentDirectory().getAbsolutePath()+"/"+chooser.getSelectedFile().getName()));
                    }
                    catch(Exception q){}
                    {
                        File file = new File("Database.xml");
                        try {
                            PrintWriter printWriter = new PrintWriter(file, "UTF-8");
                            for(int i=0;i<allLines.size();i++) {
                                printWriter.println(allLines.get(i));
                            }
                            printWriter.close();
                            restartProgram();
                        }
                        catch(Exception ex){}

                    }
                }
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
                changeProduct(1);
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
                searched = searchbar.getText();
                if (searched == "" || searched == null || searched == " ") {
                    JOptionPane.showMessageDialog(frame, "Du hast nichts eingegeben!","Error", JOptionPane.ERROR_MESSAGE); //Doesn't work
                } else {
                    tablee.clearSelection();
                    SortJTableBeforeSearch();

                    for (int i = 0; i < (xml.length); i++) {
                        String tmp2 = xml.id[i].trim();
                        String tmp3=xml.name[i].trim();
                        String tmp4=xml.amount[i].trim();
                        String tmp5=xml.amountNeeded[i].trim();
                        String tmp6=xml.category[i].trim();
                        String tmp7=xml.bestbefore[i].trim();
                        String tmp8=xml.unit[i].trim();
                        String tmp1=searched.trim();
                        if (tmp2.contains(tmp1)) {
                            tablee.addRowSelectionInterval(i, i);
                        }
                        if(tmp3.contains(tmp1)) {
                            tablee.addRowSelectionInterval(i,i);
                        }
                        if(tmp4.contains(tmp1)){
                            tablee.addRowSelectionInterval(i,i);
                        }
                        if(tmp5.contains(tmp1)){
                            tablee.addRowSelectionInterval(i,i);
                        }
                        if(tmp6.contains(tmp1)){
                            tablee.addRowSelectionInterval(i,i);
                        }
                        if(tmp7.contains(tmp1)){
                            tablee.addRowSelectionInterval(i,i);
                        }
                        if(tmp8.contains(tmp1)){
                            tablee.addRowSelectionInterval(i, i);
                        }
                    }
                    searchbar.setText("");
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
            }
        });

        search.setBackground(Color.white);
        search.setToolTipText("Search for Product Name");
        printTable.setBackground(Color.YELLOW);
        printMissing.setBackground(Color.YELLOW);
        lastChange.setBackground(Color.white);
        lastChange.setFont(new Font("Sans",Font.PLAIN,12));

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
        linkeSeite.add(lastChange);
        linerContent.setBackground(Color.WHITE);
        linerContent.setSize(new Dimension(170,1000));
        linerContent.add(linkeSeite, BorderLayout.NORTH);
        rehterContent.setBackground(Color.WHITE);
        rehterContent.setSize(new Dimension(120, 20));
        rehterContent.add(rechteSeite, BorderLayout.NORTH);
        ContentPane.add(linerContent, BorderLayout.WEST);
        ContentPane.add(rehterContent, BorderLayout.EAST);
        ContentPane.add(scroll, BorderLayout.CENTER);
        linkeSeite.setLayout(links);

        f.setJMenuBar(bar);
        f.add(ContentPane);
        f.setVisible(true);
        f.setContentPane(ContentPane);

        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                renewTextFile();
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
        JFrame frameAdd = new JFrame("Produkt hinzufügen");
        JPanel panelAdd = new JPanel();
        JLabel header = new JLabel("Add Product");
        JLabel spacer = new JLabel("                     ");
        JLabel labelID = new JLabel("ID");
        JLabel labelName = new JLabel("Name");
        JLabel labelAmount = new JLabel("Amount");
        JLabel labelAmountNeeded = new JLabel("AmountNeeded");
        JLabel labelUnit = new JLabel("Unit");
        JLabel labelCategory = new JLabel("Category");
        JLabel labelBestBefore = new JLabel("Bestbefore");
        JLabel labelStaticID = new JLabel("");
        JTextField fieldName = new JTextField();
        JTextField fieldAmount = new JTextField();
        JTextField fieldAmountNeeded = new JTextField();
        JTextField fieldBestBefore = new JTextField();
        JComboBox boxCategories = new JComboBox(categories);
        JComboBox boxUnits = new JComboBox(units);

        JButton buttonAdd = new JButton("Hinzufügen");
        JButton buttonCancel = new JButton("Abbrechen");

        //Layout wird festgelegt
        panelAdd.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.VERTICAL;

        c.gridx = 1;
        c.gridy = 0;
        panelAdd.add(header, c);

        c.gridx = 1;
        c.gridy = 1;
        panelAdd.add(spacer, c);

        c.gridx = 0;
        c.gridy = 2;
        panelAdd.add(labelID, c);

        c.gridx = 1;
        c.gridy = 2;
        panelAdd.add(labelStaticID, c);

        c.gridx = 0;
        c.gridy = 3;
        panelAdd.add(labelName, c);

        c.gridx = 1;
        c.gridy = 3;
        panelAdd.add(fieldName, c);

        c.gridx = 0;
        c.gridy = 4;
        panelAdd.add(labelAmount, c);

        c.gridx = 1;
        c.gridy = 4;
        panelAdd.add(fieldAmount, c);

        c.gridx = 0;
        c.gridy = 5;
        panelAdd.add(labelAmountNeeded, c);

        c.gridx = 1;
        c.gridy = 5;
        panelAdd.add(fieldAmountNeeded, c);

        c.gridx = 0;
        c.gridy = 6;
        panelAdd.add(labelUnit, c);

        c.gridx = 1;
        c.gridy = 6;
        panelAdd.add(boxUnits, c);

        c.gridx = 0;
        c.gridy = 7;
        panelAdd.add(labelCategory, c);

        c.gridx = 1;
        c.gridy = 7;
        panelAdd.add(boxCategories, c);

        c.gridx = 0;
        c.gridy = 8;
        panelAdd.add(labelBestBefore, c);


        c.gridx = 1;
        c.gridy = 8;
        panelAdd.add(fieldBestBefore, c);

        c.gridx = 0;
        c.gridy = 9;
        panelAdd.add(buttonAdd, c);

        c.gridx = 1;
        c.gridy = 9;
        panelAdd.add(buttonCancel, c);

        String s = String.valueOf(xml.length+1);
        labelStaticID.setText("Nr. "+s);
        fieldName.setPreferredSize(new Dimension(130, 20));
        fieldAmount.setPreferredSize(new Dimension(130, 20));
        fieldAmountNeeded.setPreferredSize(new Dimension(130, 20));
        boxUnits.setPreferredSize(new Dimension(130, 20));
        boxUnits.setBackground(Color.white);
        fieldBestBefore.setPreferredSize(new Dimension(130, 20));
        boxCategories.setPreferredSize(new Dimension(130, 20));
        boxCategories.setBackground(Color.white);
        buttonAdd.setBackground(Color.green);
        buttonAdd.setPreferredSize(new Dimension(130, 25));
        buttonCancel.setBackground(Color.red);
        buttonCancel.setPreferredSize(new Dimension(130, 25));

        frameAdd.add(panelAdd);
        panelAdd.setVisible(true);
        frameAdd.setSize(500, 500);
        frameAdd.setVisible(true);

        //Button funktionen werden eingefügt
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Xml xml=new Xml();
                if(fieldBestBefore.getText().matches("\\d{2}.\\d{2}.\\d{4}")&&fieldAmountNeeded.getText().matches("\\d+")&&fieldAmount.getText().matches("\\d+")&&(fieldName.getText() != null && fieldName.getText().length()!=0)) {
                xml.addProduct(fieldName.getText(),fieldAmount.getText(),fieldAmountNeeded.getText(),""+boxUnits.getSelectedItem(),""+boxCategories.getSelectedItem(), fieldBestBefore.getText());

                panelAdd.setVisible(false);
                frameAdd.setSize(0, 0);
                frameAdd.setVisible(false);
                xml.overWrite();

                restartProgram();
                }
                else{
                JOptionPane.showMessageDialog(frameAdd,"Bitte geben sie das Datum im Format DD.MM.JJJJ ein und in die Felder Amount und Amount Needed jeweils eine Zahl, sowie in das Feld Name einen Wert.","Aaaalaaarm", JOptionPane.ERROR_MESSAGE);
                xml.overWrite();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelAdd.setVisible(false);
                frameAdd.setSize(0, 0);
                frameAdd.setVisible(false);
            }
        });
    }

    //Funktion für die GUI zum ändern von Produkten wird erstellt
    public void changeProduct(int Nr) {
        //Bestandteile werden initialisiert
        JFrame changeFrame = new JFrame("Produkt ändern");
        JPanel changePanel = new JPanel();
        JLabel header = new JLabel("Change Product");
        JLabel spacer = new JLabel("                     ");
        JLabel labelID = new JLabel("ID");
        JLabel labelName = new JLabel("Name");
        JLabel labelAmount = new JLabel("Amount");
        JLabel labelAmountNeeded = new JLabel("AmountNeeded");
        JLabel labelUnit = new JLabel("Unit");
        JLabel labelCategory = new JLabel("Category");
        JLabel labelBestbefore = new JLabel("Bestbefore");
        JComboBox boxID = new JComboBox(xml.id);
        JTextField fieldName = new JTextField();
        JTextField fieldAmount = new JTextField();
        JTextField fieldAmountNeeded = new JTextField();
        JLabel labelFieldCategory = new JLabel("");
        JTextField fieldBestBefore = new JTextField();
        JComboBox boxUnits = new JComboBox(units);

        JButton buttonChange = new JButton("Change");
        JButton buttonCancel = new JButton("Cancel");

        //Layout wird kreiert
        changePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.VERTICAL;

        c.gridx = 1;
        c.gridy = 0;
        changePanel.add(header, c);

        c.gridx = 1;
        c.gridy = 1;
        changePanel.add(spacer, c);

        c.gridx = 0;
        c.gridy = 2;
        changePanel.add(labelID, c);

        c.gridx = 1;
        c.gridy = 2;
        changePanel.add(boxID, c);

        c.gridx = 0;
        c.gridy = 3;
        changePanel.add(labelName, c);

        c.gridx = 1;
        c.gridy = 3;
        changePanel.add(fieldName, c);

        c.gridx = 0;
        c.gridy = 4;
        changePanel.add(labelAmount, c);

        c.gridx = 1;
        c.gridy = 4;
        changePanel.add(fieldAmount, c);

        c.gridx = 0;
        c.gridy = 5;
        changePanel.add(labelAmountNeeded, c);

        c.gridx = 1;
        c.gridy = 5;
        changePanel.add(fieldAmountNeeded, c);

        c.gridx = 0;
        c.gridy = 6;
        changePanel.add(labelUnit, c);

        c.gridx = 1;
        c.gridy = 6;
        changePanel.add(boxUnits, c);

        c.gridx = 0;
        c.gridy = 7;
        changePanel.add(labelCategory, c);


        c.gridx = 1;
        c.gridy = 7;
        changePanel.add(labelFieldCategory, c);

        c.gridx = 0;
        c.gridy = 8;
        changePanel.add(labelBestbefore, c);


        c.gridx = 1;
        c.gridy = 8;
        changePanel.add(fieldBestBefore, c);

        c.gridx = 0;
        c.gridy = 9;
        changePanel.add(buttonChange, c);

        c.gridx = 1;
        c.gridy = 9;
        changePanel.add(buttonCancel, c);


        boxID.setPreferredSize(new Dimension(130, 20));
        boxID.setSelectedItem(xml.id[Nr-1]);
        fieldName.setPreferredSize(new Dimension(130, 20));
        fieldName.setText(xml.name[Nr-1]);
        fieldAmount.setPreferredSize(new Dimension(130, 20));
        fieldAmount.setText(xml.amount[Nr-1]);
        fieldAmountNeeded.setPreferredSize(new Dimension(130, 20));
        fieldAmountNeeded.setText(xml.amountNeeded[Nr-1]);
        boxUnits.setPreferredSize(new Dimension(130, 20));
        boxUnits.setSelectedItem(xml.unit[Nr-1]);
        labelFieldCategory.setPreferredSize(new Dimension(130, 20));
        labelFieldCategory.setBackground(Color.white);
        labelFieldCategory.setText(xml.category[Nr-1]);
        fieldBestBefore.setPreferredSize(new Dimension(130, 20));
        fieldBestBefore.setBackground(Color.white);
        fieldBestBefore.setText(xml.bestbefore[Nr-1]);
        buttonChange.setBackground(Color.green);
        buttonChange.setPreferredSize(new Dimension(130, 25));
        buttonCancel.setBackground(Color.red);
        buttonCancel.setPreferredSize(new Dimension(130, 25));

        changeFrame.add(changePanel);
        changePanel.setVisible(true);
        changeFrame.setSize(500, 500);
        changeFrame.setVisible(true);

        //Button funktionen werden eingefügt
        buttonChange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Xml xml = new Xml();
                if(fieldBestBefore.getText().matches("\\d{2}.\\d{2}.\\d{4}")&&fieldAmountNeeded.getText().matches("\\d+")&&fieldAmount.getText().matches("\\d+")&&(fieldName.getText() != null && fieldName.getText().length()!=0)) {
                xml.changeProduct("" + boxID.getSelectedItem(), fieldName.getText(), fieldAmount.getText(), fieldAmountNeeded.getText(),""+boxUnits.getSelectedItem(), fieldBestBefore.getText(), labelFieldCategory.getText());
                xml.overWrite();

                changePanel.setVisible(false);
                changeFrame.setSize(0, 0);
                changeFrame.setVisible(false);

                restartProgram();
                }
                else{
                xml.overWrite();
                JOptionPane.showMessageDialog(changeFrame,"Bitte geben sie das Datum im Format DD.MM.JJJJ ein und in die Felder Amount und Amount Needed jeweils eine Zahl, sowie in das Feld Name einen Wert.","Aaaalaaarm", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePanel.setVisible(false);
                changeFrame.setSize(0, 0);
                changeFrame.setVisible(false);
            }
        });

        boxID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int guiID = boxID.getSelectedIndex();
                    fieldName.setText(xml.name[guiID]);
                    fieldAmount.setText(xml.amount[guiID]);
                    fieldAmountNeeded.setText(xml.amountNeeded[guiID]);
                    labelFieldCategory.setText(xml.category[guiID]);
                    fieldBestBefore.setText(xml.bestbefore[guiID]);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(frame , "Du musst eine gültige Id auswählen!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    //Restart-Funktion
    public void restartProgram() {
        renewTextFile();
        xml.overWrite();

        f.setSize(0,0);
        f.setVisible(false);

        Gui g1 = new Gui();
        g1.GuiInit();
    }

    //Extra Stuff - in Bearbeitung
    public void missingProducts () {
        JFrame frameMissing = new JFrame();
        JPanel links = new JPanel(new BorderLayout());
        JPanel ContentPane1 = new JPanel(new BorderLayout());
        JLabel Headerlabel1 = new JLabel("Benötigtes Material");
        JButton print = new JButton("Print");
        JScrollPane p = new JScrollPane();
        JPanel head = new JPanel();

        int AnzahlZuwenig=0;
        for (int tmp = 0; tmp < xml.length; tmp++) {
            int x = Integer.parseInt(xml.amount[tmp]);
            int y = Integer.parseInt(xml.amountNeeded[tmp]);
            if (x > y) {
                AnzahlZuwenig++;
            }
        }

        String[][] missing = new String[xml.length-AnzahlZuwenig][3];
        int geloeschtCounter=0;
        for (int tmp = 0; tmp < xml.length; tmp++) {
            int x = Integer.parseInt(xml.amount[tmp]);
            int y = Integer.parseInt(xml.amountNeeded[tmp]);
            int diff;
            if (x < y) {
                diff = x - y;
                //diff =diff-2*diff;
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
            for (int i = 0; i < (xml.length-AnzahlZuwenig); i++) {
                data[i][0] = missing[i][0];
                data[i][1] = missing[i][1];
                data[i][2] = missing[i][2];
            }

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

            p.setViewportView(table1);
            p.setPreferredSize(new Dimension(800,10000));

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

        print.setBackground(Color.YELLOW);

        head.add(Headerlabel1, BorderLayout.CENTER);
        print.setSize(new Dimension(130,25));
        links.add(print, BorderLayout.NORTH);
        links.setSize(130,25);
        links.setBackground(Color.WHITE);
        head.setBackground(Color.WHITE);
        ContentPane1.add(links, BorderLayout.WEST);
        ContentPane1.add(p, BorderLayout.CENTER);
        ContentPane1.add(head, BorderLayout.NORTH);
        ContentPane1.setBackground(Color.WHITE);


        Headerlabel1.setBackground(Color.red);
        Headerlabel1.setFont(new Font("Sans", Font.BOLD, 30));


        frameMissing.setSize(1150, 720);
        frameMissing.setBackground(Color.white);


        frameMissing.add(ContentPane1);
        frameMissing.setVisible(true);
        frameMissing.setContentPane(ContentPane1);

        frameMissing.setLayout(null);
        frameMissing.setVisible(true);
    }


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

    public String printTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd.MM.yyyy - HH:mm ");
        Date currentTime = new Date();
        return formatter.format(currentTime)+"Uhr";
    }

    public void renewTextFile() {
        try {
            File textFile = new File("date.txt");
            textFile.delete();
            FileWriter writer = new FileWriter(textFile, true);
            writer.write(printTime());
            writer.write("\r\n");
            writer.close();
        } catch (IOException e) {
        }
    }

    public void SortJTableBeforeSearch(){
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tablee.getModel());
        tablee.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> s = new ArrayList<>();

        int columnIndexToSort = 0;
        s.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys(s);
        sorter.sort();
    }

}

