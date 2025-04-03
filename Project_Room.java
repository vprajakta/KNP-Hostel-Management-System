
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
//import mypkg.Util;

class SDisplayr extends JDialog implements ActionListener {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    JTable jt;
    JButton b1, b2, b4;
    DefaultTableModel dtm;
    String[] colhed = new String[] {"Room ID", "Room Name", "Capacity", "Fee"};
    Object[][] arr = null;

    SDisplayr(JFrame t, Connection con, String str, boolean state) {
        super(t, str, true);
        this.con = con;

        dtm = new DefaultTableModel(arr, colhed);
        JScrollPane jsp;
        jt = new JTable(dtm);
        jsp = new JScrollPane(jt);
        add(jsp, BorderLayout.CENTER);

        b1 = new JButton("Room Id");
        b2 = new JButton("Sort by Room Name");
        b4 = new JButton("Print");

        b1.addActionListener(this);
        b2.addActionListener(this);
        b4.addActionListener(this);

        JPanel p = new JPanel();
        p.add(b1);
        p.add(b2);
        p.add(b4);
        add(p, BorderLayout.SOUTH);

        setSize(400, 400);
        // setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton)e.getSource();
        if (b == b4) {
            try {
                jt.print();
            } catch (Exception e2) {
                System.out.println("Print Error: " + e2);
            }
        } else {
            dtm.setRowCount(0);  // Clear the table before populating
            String query = "";

            if (b == b1) {
                query = "SELECT * FROM Room ORDER BY Roomid";
            } else if (b == b2) {
                query = "SELECT * FROM Room ORDER BY Roomname";
            }

            try {
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    dtm.addRow(new Object[]{rs.getString("Roomid"), rs.getString("Roomname"),rs.getInt("Roomid"), rs.getInt("Roomname")});
                }
            } catch (SQLException e2) {
                System.out.println("SQL Error: " + e2);
            }
        }
    }
}

public class Project_Room extends JFrame implements ActionListener, FocusListener {
    Connection con;
    JPanel p1, p2;
    JLabel l3, l4, l5,l6;
    JTextField t3, t4, t5, t6;
    JButton b1, b2, b3, b4;
    int no;
    String tnm;
    ResultSet rs;

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

    Project_Room(Connection con) {
        super("Room Management");
        this.con = con;

        l3 = new JLabel("Room ID: ");
        l3.setBounds(40, 50, 100, 40);

        l4 = new JLabel("Room Name: ");
        l4.setBounds(40, 100, 100, 40);

        l5 = new JLabel("Capacity: ");
        l5.setBounds(40, 150, 100, 40);

        l6 = new JLabel("Fee/Candidate: ");
        l6.setBounds(40, 200, 100, 40);

        t3 = new JTextField(6);
        t3.setBounds(140, 50, 250, 30);

        t4 = new JTextField(30);
        t4.setBounds(140, 100, 250, 30);

        t5 = new JTextField(6);
        t5.setBounds(140, 150, 250, 30);

        t6 = new JTextField(30);
        t6.setBounds(140, 200, 250, 30);

        t3.addFocusListener(this);
        t4.addFocusListener(this);
        t5.addFocusListener(this);
        t6.addFocusListener(this);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.add(l3);
        p1.add(t3);
        p1.add(l4);
        p1.add(t4);
        p1.add(l5);
        p1.add(t5);
        p1.add(l6);
        p1.add(t6);
        b1 = new JButton("Add");
        b1.setBounds(35, 280, 80, 24);

        b2 = new JButton("Modify");
        b2.setBounds(125, 280, 80, 24);

        b3 = new JButton("Delete");
        b3.setBounds(215, 280, 80, 24);

        b4 = new JButton("Display");
        b4.setBounds(305, 280, 80, 24);



        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        
        b1.setBackground(Color.decode("#d9650b"));
        b2.setBackground(Color.decode("#d9650b"));
        b3.setBackground(Color.decode("#d9650b"));
        b4.setBackground(Color.decode("#d9650b"));

        b1.setForeground(Color.WHITE);
        b2.setForeground(Color.WHITE);
        b3.setForeground(Color.WHITE);
        b4.setForeground(Color.WHITE);

        // p2 = new JPanel();
        p1.add(b1);
        p1.add(b2);
        p1.add(b3);
        p1.add(b4);

        p1.setBackground(Color.decode("#f9e8c3"));

        add(p1, BorderLayout.CENTER);
        // add(p2, BorderLayout.SOUTH);

        setSize(430, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void focusGained(FocusEvent e) {}

    public void focusLost(FocusEvent e) {
        JTextField t = (JTextField)e.getSource();
        if (t == t3) {
            try {
                String str = t3.getText();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Room WHERE Roomid=?");
                ps.setString(1, str);
                rs = ps.executeQuery();

                if (rs.next()) {
                    tnm = rs.getString("RoomName");
                    t4.setText(tnm);
                    t5.setText(rs.getString("capacity"));
                    t6.setText(rs.getString("fee"));
                    b1.setEnabled(false);  // Disable Add
                    b2.setEnabled(true);   // Enable Modify
                    b3.setEnabled(true);   // Enable Delete
                } else {
                    b1.setEnabled(true);   // Enable Add
                    b2.setEnabled(false);  // Disable Modify
                    b3.setEnabled(false);  // Disable Delete
                }
            } catch (Exception e1) {
                System.out.println("Error: " + e1);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();

        if (b == b1) {
            addRecord();
        } else if (b == b2) {
            modifyRecord();
        } else if (b == b3) {
            deleteRecord();
        } else if (b == b4) {
            new SDisplayr(this, con, "Room Display", true);
        }
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t6.setText("");
        t3.requestFocus();
    }

    void addRecord() {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Room (Roomid, Roomname, capacity, fee) VALUES (?, ?, ?, ?)");
            ps.setString(1, (t3.getText()));
            ps.setString(2, (t4.getText()));
            ps.setInt(3, Integer.parseInt(t5.getText()));
            ps.setInt(4, Integer.parseInt(t6.getText()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add Error: " + e);
        }
    }

    void modifyRecord() {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE Room SET Roomname=?, capacity=?, fee=? WHERE Roomid=?");
            ps.setString(1, t4.getText());
            ps.setInt(2, Integer.parseInt(t5.getText()));
            ps.setInt(3, Integer.parseInt(t6.getText()));
            ps.setString(4, (t3.getText()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Modify Error: " + e);
        }
    }

    void deleteRecord() {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Room WHERE Roomid=?");
            ps.setString(1, (t3.getText()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete Error: " + e);
        }
    }



    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hostel management system", "root", "");
        new Project_Room(con);
    }
}


 

