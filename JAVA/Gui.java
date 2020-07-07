import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class Gui {
    static String name;
    static String amount;
    static String bestbefore;
    static String category;
    public static void main(String args[]) {
        JFrame f = new JFrame();
        SpringLayout Layout = new SpringLayout();
        JPanel ContentPane = new JPanel();
        ContentPane.setLayout(Layout);
        ContentPane.setBackground(Color.WHITE);
        JLabel Headerlabel = new JLabel("Schulsanitätsdienst Materialmanegement");
        JButton Create_New = new JButton("Produkte hinzufügen");
        Create_New.setBackground(Color.WHITE);

        ImageIcon image = new ImageIcon("C:\\Users\\Meneer\\Pictures\\image.png");
        JLabel imageLabel = new JLabel(image);

        Create_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Wurde gedrückt");
                add_product();
            }
        });

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

        f.setLayout(null);
        f.setVisible(true);

    }
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
        JTextField f4 = new JTextField();
        JButton b1 = new JButton("Fertig");
        JButton b2 = new JButton("Abbrechen");

        p1.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        c.gridx=0;
        c.gridy=0;
        p1.add(l1, c);

        c.gridx=1;
        c.gridy=0;
        p1.add(f1, c);

        c.gridx=0;
        c.gridy=1;
        p1.add(l2, c);

        c.gridx=1;
        c.gridy=1;
        p1.add(f2, c);

        c.gridx=0;
        c.gridy=2;
        p1.add(l3, c);

        c.gridx=1;
        c.gridy=2;
        p1.add(f3, c);

        c.gridx=0;
        c.gridy=3;
        p1.add(l4, c);

        c.gridx=1;
        c.gridy=3;
        p1.add(f4, c);

        c.gridx=0;
        c.gridy=4;
        p1.add(b1, c);

        c.gridx=1;
        c.gridy=4;
        p1.add(b2, c);

        f1.setPreferredSize(new Dimension(100, 20));
        f2.setPreferredSize(new Dimension(100, 20));
        f3.setPreferredSize(new Dimension(100, 20));
        f4.setPreferredSize(new Dimension(100, 20));

        b1.setBackground(Color.green);
        b2.setBackground(Color.red);

        j.add(p1);
        p1.setVisible(true);
        j.setSize(500, 500);
        j.setVisible(true);

        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //xml.addProduct(f1.getText(),f2.getText(),f3.getText(),f4.getText());
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.out.println("Abbrechen");
                p1.setVisible(false);
                j.setSize(0, 0);
                j.setVisible(false);
            }
        });
    }
}
