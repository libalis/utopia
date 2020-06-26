import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui {
    public static void main(String args[]){
        JFrame f=new JFrame();
        SpringLayout Layout = new SpringLayout();
        JPanel ContentPane =new JPanel();
        ContentPane.setLayout(Layout);
        ContentPane.setBackground(Color.WHITE);
        JLabel Headerlabel =new JLabel("Schulsanitätsdienst Materialmanegement");


        
        ImageIcon image = new ImageIcon("C:\\Users\\Meneer\\Pictures\\image.png");
        JLabel imageLabel = new JLabel(image);



        Headerlabel.setBackground(Color.red);

        Headerlabel.setFont(new Font("Sans", Font.BOLD, 30));


        ContentPane.add(Headerlabel);
        f.setSize(1000,1000);
        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Headerlabel, 0, SpringLayout.HORIZONTAL_CENTER, ContentPane);
        f.add(ContentPane);
        f.setVisible(true);
        f.setContentPane(ContentPane);

        f.setLayout(null);
        f.setVisible(true);
    }
}
