
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    JMenuBar mb;
    JMenu m;
    JMenuItem l1,l2,u1,u2;

    MainMenu()
    {
        super("Menu separator");
        m = new JMenu("Menu");

        l1=new JMenuItem("Item 3");
        l2=new JMenuItem("Item 4");

        u1=new JMenuItem("Item 1");
        u2=new JMenuItem("Item 2");

        m.add(u1);
        m.add(u2);
        m.addSeparator();
        m.add(l1);
        m.add(l2);
        mb=new JMenuBar();
        mb.add(m);
        setJMenuBar(mb);
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainMenu a=new MainMenu();
    }
}
