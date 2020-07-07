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

public class gui{
    public static void main(String args[]){
        JFrame f=new JFrame();
        SpringLayout Layout = new SpringLayout();
        JPanel ContentPane =new JPanel();
        ContentPane.setLayout(Layout);
        ContentPane.setBackground(Color.WHITE);
        JLabel Headerlabel =new JLabel("Schulsanitätsdienst Materialmanegement");
        JButton Create_New = new JButton("Produkte hinzufügen");



        ImageIcon image = new ImageIcon("C:\\Users\\Meneer\\Pictures\\image.png");
        JLabel imageLabel = new JLabel(image);

        Create_New.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    System.out.println("Wurde gedrückt");
            }
        });

        Headerlabel.setBackground(Color.red);

        Headerlabel.setFont(new Font("Sans", Font.BOLD, 30));

        ContentPane.add(Create_New);
        ContentPane.add(Headerlabel);
        f.setSize(1280,720);
        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Headerlabel, 0, SpringLayout.HORIZONTAL_CENTER, ContentPane);
        Layout.putConstraint(SpringLayout.WEST, Create_New, 100, SpringLayout.WEST, ContentPane);
        Layout.putConstraint(SpringLayout.NORTH, Create_New, 50, SpringLayout.NORTH, ContentPane);
        f.add(ContentPane);
        f.setVisible(true);
        f.setContentPane(ContentPane);

        f.setLayout(null);
        f.setVisible(true);
    }
}
