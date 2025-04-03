
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
//import mypkg.Util;

// class SDisplayh extends JDialog implements ActionListener {
//     Connection con;
//     PreparedStatement ps;
//     ResultSet rs;
//     JTable jt;
//     JButton b1;
//     DefaultTableModel dtm;
//     String[] colhed = new String[] {"Room no.", "Occupied", "Available"};
//     Object[][] arr = null;

//     SDisplayh(JFrame t, Connection con, String str, boolean state) {
//         super(t, str, true);
//         this.con = con;

//         dtm = new DefaultTableModel(arr, colhed);
//         JScrollPane jsp;
//         jt = new JTable(dtm);
//         jsp = new JScrollPane(jt);
//         add(jsp, BorderLayout.CENTER);

//         b1 = new JButton("Hostel");

//         b1.addActionListener(this);

//         JPanel p = new JPanel();
//         p.add(b1);
//         add(p, BorderLayout.SOUTH);

//         setSize(400, 400);
//         // setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//         setVisible(true);
//     }

//     public void actionPerformed(ActionEvent e) 
//     {
//         JButton b=(JButton)e.getSource();
//         if(b==b1)
//         {
//             int hid=
//         }
//     }
    
// }

public class Project_Allocation extends JFrame implements ActionListener {
    Connection con;
    JPanel p1, p2;
    JLabel lyr,lprn,lhid,lrid;
    JTextField tyr,tprn,thid,trid;
    JTable t1,table;
    DefaultTableModel dtm;
    JButton b1, b2;
    int no;
    String tnm;
    ResultSet rs;

    Project_Allocation(Connection con) {
        super("Hostel Management");
        this.con = con;

        lyr = new JLabel("Academic year: ");
        lyr.setBounds(40, 50, 100, 40);
        tyr = new JTextField(6);
        tyr.setBounds(140, 50, 250, 30);

        lprn = new JLabel("PRN: ");
        lprn.setBounds(40, 100, 100, 40);
        tprn = new JTextField(30);
        tprn.setBounds(140, 100, 250, 30);

        
        lhid = new JLabel("Hostel Id: ");
        lhid.setBounds(40, 200, 100, 40);
        thid = new JTextField(30);
        thid.setBounds(140, 200, 250, 30);

        lrid = new JLabel("Room Id: ");
        lrid.setBounds(40, 250, 100, 40);
        trid = new JTextField(30);
        trid.setBounds(140, 250, 250, 30);
        
        p1 = new JPanel();
        p1.setLayout(null);
        p1.add(lyr);
        p1.add(tyr);
        p1.add(lprn);
        p1.add(tprn);
        p1.add(lrid);
        p1.add(thid);
        p1.add(lhid);
        p1.add(trid);
        b1 = new JButton("Search");
        b1.setBounds(35, 520, 80, 24);

        b2 = new JButton("Allocate");
        b2.setBounds(125, 520, 80, 24);

        b1.addActionListener(this);
        b2.addActionListener(this);

        b1.setBackground(Color.decode("#d9650b"));
        b2.setBackground(Color.decode("#d9650b"));

        b1.setForeground(Color.WHITE);
        b2.setForeground(Color.WHITE);

        p1.add(b1);
        p1.add(b2);

        String[] columnNames = {"Room ID", "Room Name","Capacity", "Available"};
        dtm = new DefaultTableModel(columnNames, 0);
        table = new JTable(dtm);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(15, 300, 400, 200);
        add(scrollPane);

        add(p1, BorderLayout.CENTER);

        p1.setBackground(Color.decode("#f9e8c3"));

        setSize(450, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    // void addRecord() {
    //     try {
    //         PreparedStatement ps = con.prepareStatement("INSERT INTO category (cid, cname) VALUES (?, ?)");
    //         ps.setInt(1, Integer.parseInt(t3.getText()));
    //         ps.setString(2, t4.getText());
    //         ps.executeUpdate();
    //         // Util.display("Record Inserted");
    //     } catch (SQLException e) {
    //         System.out.println("Add Error: " + e);
    //     }
    // }

    
    

    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();

        if (b == b1) {
            // try {
            //     // System.out.println("calling add");
            //     PreparedStatement ps=con.prepareStatement("Select roomcount from hostel where hostelid=?");
            //     ps.setString(1, thid.getText());
            //     ResultSet rs=ps.executeQuery();
            //     if(rs.next())
            //     {
            //         int cnt=rs.getInt("roomcount");
            //         System.out.println(cnt);
            //     }
                
            // } catch (Exception e1) {
            //     // TODO: handle exception
            // }
            add();
        } 
        if (b == b2) {
            // try {
            //     // System.out.println("calling add");
            //     PreparedStatement ps=con.prepareStatement("Select roomcount from hostel where hostelid=?");
            //     ps.setString(1, thid.getText());
            //     ResultSet rs=ps.executeQuery();
            //     if(rs.next())
            //     {
            //         int cnt=rs.getInt("roomcount");
            //         System.out.println(cnt);
            //     }
                
            // } catch (Exception e1) {
            //     // TODO: handle exception
            // }
            // add();

            AllAllocation();
        } 
        tyr.requestFocus();
    }
    void AllAllocation() {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO stuprn2425 (acadyr, prn, hostelid,roomid) VALUES (?,?,?, ?)");
           
            ps.setString(1, (tyr.getText()));
            ps.setInt(2,Integer.parseInt(tprn.getText()));
            ps.setString(3, thid.getText());
            ps.setString(4, trid.getText());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Add Error: " + e);
        }
    }
    void add()
    {
        try {
            // First query: Get rooms based on hostelid
            PreparedStatement ps5 = con.prepareStatement("SELECT roomid, roomname, capacity FROM room WHERE hostelid=?");
            ps5.setString(1, thid.getText());
            ResultSet rs5 = ps5.executeQuery();
        
            while (rs5.next()) {
                String rid = rs5.getString("roomid");
                System.err.println(rs5.getString("roomid"));
        
                // Second query: Check the occupied count for the room
                PreparedStatement ps6 = con.prepareStatement("SELECT count(*) AS occupied FROM stuprn2425 WHERE hostelid=? AND roomid=? AND acadyr=?");
                ps6.setString(1, thid.getText());
                ps6.setString(2, rid);
                ps6.setString(3, tyr.getText()); // Assuming this is the academic year field
        
                ResultSet rs6 = ps6.executeQuery();
        
                if (rs6.next()) {
                    // If there's a row in rs6, calculate availability
                    int occupied = rs6.getInt("occupied");
                    int capacity = rs5.getInt("capacity");
                    int available = capacity - occupied;
        
                    // Add room data with available count to the table model
                    dtm.addRow(new Object[]{rs5.getString("roomid"), rs5.getString("roomname"), capacity, available});
                } else {
                    // If no result is found for occupied, assume available rooms = capacity
                    int capacity = rs5.getInt("capacity");
                    dtm.addRow(new Object[]{rs5.getString("roomid"), rs5.getString("roomname"), capacity, capacity});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error for debugging
        }
        
    }
     void allTable()
    {
        try {
            
            PreparedStatement ps=con.prepareStatement("Select roomcount from hostel where hostelid=?");
            ps.setString(1, thid.getText());
            ps.executeQuery();
            ResultSet rs=ps.executeQuery();
            int cnt=rs.getInt("roomcount");
            System.out.println("out of loop");

            System.out.println(cnt);
            for(int i=1;i<=cnt;i++)
            {
                System.out.println("in loop");
                PreparedStatement ps1=con.prepareStatement("Select capacity from room where roomid=?");
                ps1.setString(1, "i");
                ResultSet rs1=ps1.executeQuery();
                int cap=rs1.getInt("capacity");
                PreparedStatement ps2=con.prepareStatement("Select count(*) as ctt from stuprn2425 where hostelid=? and roomid=? and acadyr=?");
                ps2.setString(1, thid.getText());
                ps2.setString(2, trid.getText());
                ps2.setString(3, tyr.getText());
                ResultSet rs2=ps2.executeQuery();
                int ava=cap-rs2.getInt("ctt");
                // dtm.addRow(new Object[]{i,rs2.getInt("ctt"),ava});
                System.out.println("Room:"+i+"Occupied:"+rs2.getInt("ctt")+"Avail:"+ava);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    void addRecord() {
        try {
            // System.out.println("calling add");
            PreparedStatement ps=con.prepareStatement("Select roomcount from hostel where hostelid=?");
            ps.setString(1, thid.getText());
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                int cnt=rs.getInt("roomcount");
                System.out.println(cnt);
            }
            PreparedStatement ps3=con.prepareStatement("Select roomcount from hostel where hostelid=?");
            ps3.setString(1, thid.getText());
            ResultSet rs3=ps3.executeQuery();
            int cnt=rs3.getInt("roomcount");
            System.out.println("out of loop");

            System.out.println(cnt);
            for(int i=1;i<=cnt;i++)
            {
                System.out.println("in loop");
                PreparedStatement ps1=con.prepareStatement("Select capacity from room where roomid=?");
                ps1.setString(1, "i");
                ResultSet rs1=ps1.executeQuery();
                int cap=rs1.getInt("capacity");
                PreparedStatement ps2=con.prepareStatement("Select count(*) as ctt from stuprn2425 where hostelid=? and roomid=? and acadyr=?");
                ps2.setString(1, thid.getText());
                ps2.setString(2, trid.getText());
                ps2.setString(3, tyr.getText());
                ResultSet rs2=ps2.executeQuery();
                int ava=cap-rs2.getInt("ctt");
                // dtm.addRow(new Object[]{i,rs2.getInt("ctt"),ava});
                System.out.println("Room:"+i+"Occupied:"+rs2.getInt("ctt")+"Avail:"+ava);
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        // try {
        //     PreparedStatement ps = con.prepareStatement("INSERT INTO stuprn2425 (acadyr, PRN, hostelId, roomId) VALUES (?, ?, ?, ?)");
        //     ps.setString(1, (tyr.getText()));
        //     ps.setInt(2, Integer.parseInt(tprn.getText()));
        //     ps.setString(3, (thid.getText()));
        //     ps.setString(4, (trid.getText()));
        //     ps.executeUpdate();
        // } catch (SQLException e) {
        //     System.out.println("Add Error: " + e);
        // }
    }

    void modifyRecord() {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE stuprn2425 SET acadyr=?, hostelId=?, roomId=? WHERE prn=?");
            ps.setInt(4, Integer.parseInt(tprn.getText()));
            ps.setString(2, (thid.getText()));
            ps.setString(3, (trid.getText()));
            ps.setString(1, (tyr.getText()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Modify Error: " + e);
        }
    }

    void deleteRecord() {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM stuprn2425 WHERE prn=?");
            ps.setInt(1, Integer.parseInt(tprn.getText()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete Error: " + e);
        }
    }



    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hostel management system", "root", "");
        new Project_Allocation(con);
    }
}


 
