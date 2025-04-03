import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import mypkg.Util;
import java.sql.*;

class SDisplay extends JDialog implements ActionListener{
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int rno, id, i, n;
    String snm, tnm;
    JTable jt;
    JButton b1, b2, b3, b4;
    DefaultTableModel dtm;
    String[] colhed = new String[] {"Roll No", "SName", "ID", "TName"};
    Object[][] arr = null;
    SDisplay(JFrame t, Connection con, String str, boolean state){
        super(t, str, true);
        this.con = con;
        dtm = new DefaultTableModel(arr, colhed);
        JScrollPane jsp;
        jt = new JTable(dtm);
        jsp = new JScrollPane(jt);
        add(jsp, BorderLayout.CENTER);
        b1 = new JButton("Student");
        b2 = new JButton("SName Sort");
        b3 = new JButton("TName Sord");
        b4 = new JButton("Print");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        JPanel p = new JPanel();
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        add(p, BorderLayout.SOUTH);
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        JButton b = (JButton)e.getSource();
        if(b == b4){
            try{
                jt.print();
            }catch(Exception e2){}
        }
        else{
            n = dtm.getRowCount();
            while(--n >= 0)
                dtm.removeRow(n);
            if(b == b1){
                try {
                    ps = con.prepareStatement("SELECT * FROM student");
                    rs = ps.executeQuery();
                    i = 0;
                    while(rs.next())
                        dtm.insertRow(i++, new String[]{"" + rs.getInt(1), rs.getString(2), "" + rs.getInt(3), rs.getString(4)});

                } catch (Exception e2) {
                    // TODO: handle exception
                }
            }

            if(b == b2){
                try {
                    ps = con.prepareStatement("SELECT * FROM student ORDER BY sname");
                    rs = ps.executeQuery();
                    i = 0;
                    while(rs.next())
                        dtm.insertRow(i++, new String[]{"" + rs.getInt(1), rs.getString(2), "" + rs.getInt(3), rs.getString(4)});

                } catch (Exception e2) {
                    // TODO: handle exception
                    System.out.println(" " + e2);
                }
            }
            if(b == b3){
                try {
                    ps = con.prepareStatement("SELECT * FROM student ORDER BY tname");
                    rs = ps.executeQuery();
                    i = 0;
                    while(rs.next())
                        dtm.insertRow(i++, new String[]{"" + rs.getInt(1), rs.getString(2), "" + rs.getInt(3), rs.getString(4)});

                } catch (Exception e2) {
                    // TODO: handle exception
                    System.out.println(" " + e2);
                }
            }
        }
    }
}

public class WStuAMD extends JFrame implements ActionListener,FocusListener
{
    Connection con;
    JPanel p1,p2;
    JLabel l1,l2,l3,l4;
    JTextField t1,t2,t3,t4;
    JButton b1,b2,b3,b4;
    int no,id;
    String snm,tnm;
    ResultSet rs;
    boolean flg = true;

    WStuAMD(Connection con)
    {
        super("Student");
        this.con = con;

        l1 = new JLabel("Roll no: ");
        l2 = new JLabel("Name: ");
        l3 = new JLabel("Trade ID: ");
        l4 = new JLabel("Trade Name: ");

        t1 = new JTextField(6);
        t2 = new JTextField(30);
        t3 = new JTextField(6);
        t4 = new JTextField(30);

        t1.addFocusListener(this);
        t3.addFocusListener(this);
        t4.setEditable(false);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(4,2,5,5));
        p1.add(l1);
        p1.add(t1);
        p1.add(l2);
        p1.add(t2);
        p1.add(l3);
        p1.add(t3);
        p1.add(l4);
        p1.add(t4);

        b1 = new JButton("Add");
        b2 = new JButton("Mod");
        b3 = new JButton("Delete");
        b4 = new JButton("Display");

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        p2 = new JPanel();
        p2.add(b1);
        p2.add(b2);
        p2.add(b3);
        p2.add(b4);

        add(p1,BorderLayout.CENTER);
        add(p2,BorderLayout.SOUTH);
        setSize(400,400);

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e)
            {
                try
                {
                    con.close();
                }
                catch(Exception e1) {}
            }
        });

        setVisible(true);

    }    

    public void focusGained(FocusEvent e)
    {
        JTextField t = (JTextField)e.getSource();

        if(t == t1)
        {
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
        }
        else
        {
            t4.setText(null);
        }

    }

    public void focusLost(FocusEvent e)
    {
        JTextField t = (JTextField)e.getSource();
        if(t==t1)
        {
            try{
                no = Integer.parseInt(t1.getText());
            }
            catch(Exception e1) 
            {
                t1.requestFocus();
                return;
            }
            System.out.println(no);
            PreparedStatement ps = null;

            try
            {
                ps = con.prepareStatement("select * from student where rno=?");
                ps.setInt(1, no);

                rs = ps.executeQuery();
                boolean result = rs.next();

                if(result==false)       // record not found
                {
                    b1.setEnabled(true); 
                    b2.setEnabled(false);
                    b3.setEnabled(false);

                }else{
                    snm = rs.getString(2);  //Student Name
                    id = rs.getInt(3);
                    tnm = rs.getString(4);
                    t2.setText(snm);        
                    t3.setText("" + id);
                    t4.setText(tnm);
                    b1.setEnabled(false);
                    b2.setEnabled(true);
                    b3.setEnabled(true);
                }
            }
            catch(Exception e2) {
                System.out.println("" + e2);
            }
        }
        if(t == t3){
            
            try {
                id = Integer.parseInt(t3.getText());
            } catch (Exception e2) {
                // TODO: handle exception
                t3.requestFocus();
                return;
            }
            PreparedStatement ps = null;
            try{
                ps = con.prepareStatement("SELECT * FROM trade");
                rs = ps.executeQuery();
                while(rs.next()){
                    int id1 = rs.getInt(1);
                    if(id1 == id){
                        flg = false;
                        try{
                            tnm = rs.getString(2);
                            t4.setText(tnm);
                        }catch(Exception e3){}
                    }  
                }
                if(flg){
                t3.setText("");
                t3.requestFocus();
                return;
                }
            }catch(Exception e2){
                System.out.println("" + e2);
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        JButton b = (JButton)e.getSource();
        if(b == b1){
            add();
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t1.requestFocus();
        }
        if(b == b2){
            mod();
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t1.requestFocus();
        }
        if(b == b3){
            del();
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t1.requestFocus();
        }
        if(b == b4){
            display();
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t1.requestFocus();
        }
    }

    void mod(){
        PreparedStatement ps = null;
        try {
            snm = t2.getText();
            ps = con.prepareStatement("UPDATE student SET sname=?, tid=?, tname=? WHERE rno=?");
            ps.setString(1, snm);
            ps.setInt(2, id);
            ps.setString(3, tnm);
            ps.setInt(4, no);
            ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("" + e);
        }
        Util.display("Record Modified");
    }
 
    void del(){
        PreparedStatement ps = null;
        try {
            snm = t2.getText();
            ps = con.prepareStatement(" DELETE FROM student WHERE rno=?");
            ps.setInt(1, no);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("" + e);
        }
        Util.display("Record Inserted");
    }

    void display(){
        new SDisplay(this, this.con, "Table", true);

    }

    public void add(){
        PreparedStatement ps = null;
        try {
            snm = t2.getText();
            ps = con.prepareStatement("INSERT INTO student VALUES (?, ?, ?, ?)");
            ps.setInt(1, no);
            ps.setString(2, snm);
            ps.setInt(3, id);
            ps.setString(4, tnm);
            ps.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("" + e);
        }
        Util.display("Record Inserted");
    }
    public static void main(String [] args) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/wce","root","");
        WStuAMD a = new WStuAMD(con);
    }
}