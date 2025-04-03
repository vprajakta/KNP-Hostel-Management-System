
import java.sql.*;
// import mypkg.Util;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class Project_Menu extends JFrame implements ActionListener
{
    JButton bt,bc,bs,bh,br;
    JPanel p1;
    JLabel name;
    Connection con;
    ImageIcon backgroundImage ;
    JTextArea desc;

    class RoundedButton extends JButton {
        private int radius = 30; // Radius for rounded corners

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false); // Prevent the default button background
            setFocusPainted(false); // Remove the focus ring when clicked
            setOpaque(false); // Make it transparent
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isArmed()) {
                g.setColor(Color.decode("#b84d08")); // Color when button is pressed
            } else {
                g.setColor(getBackground()); // Default background color
            }
            g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); // Rounded rectangle shape
            super.paintComponent(g); // Call the parent class to ensure text rendering
        }

        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(Color.decode("#d9650b"));
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius); // Rounded border
        }
    }

    Project_Menu(Connection con){
        super();
        this.con=con;
        p1 = new JPanel();
        
        backgroundImage = new ImageIcon("bgimg.jpg"); // Replace with your image file

        // Create a custom panel with background image
        p1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                
            }
        };
        p1.setLayout(null);

        bt = new RoundedButton("Trade");
        bc = new RoundedButton("Category");
        bs = new RoundedButton("Student");
        bh = new RoundedButton("Hostel");
        br = new RoundedButton("Room");
        
        desc = new JTextArea("A room allocation system that efficiently assigns specific rooms to " + "\n" + " students based on predefined criteria. It ensures optimal" + "\n" +" utilization of available space while maintaining fairness and ease of management. "+ "\n"+ "The system automates the allocation process, reducing manual effort and errors.");
        desc.setFont(new Font("Arial", Font.PLAIN, 18));
        desc.setForeground(Color.decode("#fefaf1"));
        desc.setOpaque(false); // Removes the background
        desc.setBorder(null); // Removes the border

        LineBorder border = new LineBorder(Color.BLACK, 2); // Black border with 2px thickness
        bt.setBorder(border);
        bc.setBorder(border);
        bs.setBorder(border);
        bh.setBorder(border);
        br.setBorder(border);

        
        bt.setBounds(25, 460, 85, 30);

        bc.setBounds(125, 460, 85, 30);

        bs.setBounds(225, 460, 85, 30);

        bh.setBounds(325, 460, 85, 30);

        br.setBounds(425, 460, 85, 30);

        desc.setBounds(50,280,500,160);

        p1.add(desc);
        bt.addActionListener(this);
        br.addActionListener(this);
        bc.addActionListener(this);
        bh.addActionListener(this);
        bs.addActionListener(this);

        bt.setBackground(Color.decode("#d9650b"));
        br.setBackground(Color.decode("#d9650b"));
        bc.setBackground(Color.decode("#d9650b"));
        bh.setBackground(Color.decode("#d9650b"));
        bs.setBackground(Color.decode("#d9650b"));


        bt.setForeground(Color.WHITE);
        br.setForeground(Color.WHITE);
        bc.setForeground(Color.WHITE);
        bh.setForeground(Color.WHITE);
        bs.setForeground(Color.WHITE);


        // name=new JLabel("KNP Hostel Management System");
        // name.setFont(new Font("Elephant", Font.PLAIN, 28));
        // name.setForeground(Color.DARK_GRAY);
        // name.setBounds(20, 80, 650, 60);
        // name.setForeground(Color.decode("#d9650b"));
        // p1.add(name);

        p1.add(bt);
        p1.add(bc);
        p1.add(br);
        p1.add(bh);
        p1.add(bs);


        add(p1);
        p1.setBackground(Color.decode("#f9e8c3"));
        
        setSize(600, 600);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {
        JButton b = (JButton) e.getSource();
        if(b==bs)
        {
            Project_Student1 p=new Project_Student1(con);
            p.setVisible(true);
        }
        else if(b==bc)
        {
            Project_Category c=new Project_Category(con);
            c.setVisible(true);
        }
        else if(b==br)
        {
            Project_Room r=new Project_Room(con);
            r.setVisible(true);
        }
        else if(b==bt)
        {
            Project_Trade p=new Project_Trade(con);
            p.setVisible(true);
        }
        else if(b==bh)
        {
            Project_Hostel p=new Project_Hostel(con);
            p.setVisible(true);
        }
    }
    public static void main(String[] args)  throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hostel management system", "root", "");
        new Project_Menu(con);
        
    }
}
