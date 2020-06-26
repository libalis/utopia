import javax.swing.*;
import java.awt.*;

public class Gui {
    public static void main(String args[]){
        JFrame f=new JFrame();
        SpringLayout Layout = new SpringLayout();
        JPanel ContentPane =new JPanel();
        ContentPane.setLayout(Layout);
        ContentPane.setBackground(Color.WHITE);
        JLabel Headerlabel =new JLabel("Schulsanitätsdienst Materialmanegement", SwingConstants.CENTER);
        Headerlabel.setBackground(Color.red);

        Headerlabel.setFont(new Font("Sans", Font.BOLD, 30));


        ContentPane.add(Headerlabel);

        Layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, Headerlabel, 0, SpringLayout.HORIZONTAL_CENTER, ContentPane);
        f.add(ContentPane);
        f.setVisible(true);
        f.setContentPane(ContentPane);
        f.setSize(1000,1000);
        f.setLayout(null);
        f.setVisible(true);
    }
}
