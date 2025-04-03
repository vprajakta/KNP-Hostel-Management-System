
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
// import mypkg.Util;
import java.util.ArrayList;

class SDisplay extends JDialog implements ActionListener {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    JTable table;
    JButton btnStudent, btnSortByName, btnPrint;
    DefaultTableModel tableModel;
    String[] columnHeaders = new String[] {"PRN", "Name", "Gender","TradeId", "Trade", "Category", "Year","Email", "Contact_No", "Parents_contact", "Address"};
    Object[][] data = null;

    SDisplay(JFrame parent, Connection con, String title, boolean modal) {
        super(parent, title, modal);
        this.con = con;

        tableModel = new DefaultTableModel(data, columnHeaders);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        btnStudent = new JButton("Student");
        btnSortByName = new JButton("Sort by Student Name");
        btnPrint = new JButton("Print");

        btnStudent.addActionListener(this);
        btnSortByName.addActionListener(this);
        btnPrint.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(btnStudent);
        panel.add(btnSortByName);
        panel.add(btnPrint);
        add(panel, BorderLayout.SOUTH);

        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn == btnPrint) {
            try {
                table.print();
            } catch (Exception ex) {
                System.out.println("Print Error: " + ex);
            }
        } else {
            tableModel.setRowCount(0);
            String query = "";

            if (btn == btnStudent) {
                query = "SELECT * FROM students ORDER BY prn";
            } else if (btn == btnSortByName) {
                query = "SELECT * FROM students ORDER BY name";
            }

            try {
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    tableModel.addRow(new Object[] {rs.getInt("prn"), rs.getString("name"),rs.getString("Gender"),rs.getString("TradeId"),rs.getString("Trade"),rs.getString("CategoryId"),rs.getString("Category"),rs.getString("Year"),rs.getString("Email"),rs.getString("Contact_No"),rs.getString("Parents_contact"),rs.getString("Address")});
                }
            } catch (SQLException ex) {
                System.out.println("SQL Error: " + ex);
            }
        }
    }
}

public class Project_Student1 extends JFrame implements ActionListener , FocusListener {
    Connection con;
    JPanel mainPanel;
    JLabel lblPRN, lblName, lblGender, lblEmail, lblYear, lblContact, lblParentContact, lblAddress,lblTrade,lblCat;
    JTextField txtPRN, txtName, txtEmail, txtContact, txtParentContact, txtAddress;
    JRadioButton rbMale, rbFemale;
    ButtonGroup genderGroup;
    JComboBox<String> cbYear,cbTrade,cbCat;
    JButton btnAdd, btnModify, btnDelete, btnDisplay;
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

    Project_Student1(Connection con) {
        super("Student Management");
        this.con = con;

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        lblPRN = new JLabel("PRN:");
        lblName = new JLabel("Name:");
        lblGender = new JLabel("Gender:");
        lblEmail = new JLabel("Email:");
        lblYear = new JLabel("Year:");
        lblContact = new JLabel("Contact:");
        lblParentContact = new JLabel("Parent's Contact:");
        lblAddress = new JLabel("Address:");
        // lblCatID = new JLabel("Category ID:");
        lblCat = new JLabel("Category:");
        // lblTrdID= new JLabel("Trade ID:");
        lblTrade = new JLabel("Trade:");

        txtPRN = new JTextField(10);
        txtName = new JTextField(20);
        txtEmail = new JTextField(20);
        txtContact = new JTextField(10);
        txtParentContact = new JTextField(10);
        txtAddress = new JTextField(30);
        

        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);

        try 
        {
    // Query to fetch all tradenames from the trade table
    PreparedStatement ps3 = con.prepareStatement("SELECT tradename FROM trade");
    ResultSet rs = ps3.executeQuery();

    // Use an ArrayList to store tradenames dynamically
    ArrayList<String> tradeList = new ArrayList<>();

    while (rs.next()) {
        tradeList.add(rs.getString("tradename"));
    }

    // Convert the ArrayList to an array for JComboBox
    String[] tradeArray = tradeList.toArray(new String[0]);

    // Initialize JComboBox with trade names from the database
    cbTrade = new JComboBox<>(tradeArray);

    } catch (SQLException e) {
    System.out.println("Error fetching trade names: " + e);
    }
    try 
        {
    PreparedStatement ps4 = con.prepareStatement("SELECT Cname FROM Category");
    ResultSet rs1 = ps4.executeQuery();

    ArrayList<String> CategoryList = new ArrayList<>();

    while (rs1.next()) {
        CategoryList.add(rs1.getString("Cname"));
    }

    String[] CategoryArray = CategoryList.toArray(new String[0]);

    cbCat = new JComboBox<>(CategoryArray);

    } catch (SQLException e) {
    System.out.println("Error fetching Category names: " + e);
    }

        cbYear = new JComboBox<>(new String[] {"I", "II", "III", "IV"});
        //cbTrdID = new JComboBox<>(new String[] {"01","02","03"});

        //cbCatID = new JComboBox<>(new String[] {"01","02","03","04","05"});

        btnAdd = new JButton("Add");
        btnModify = new JButton("Modify");
        btnDelete = new JButton("Delete");
        btnDisplay = new JButton("Display");

        btnAdd.addActionListener(this);
        btnModify.addActionListener(this);
        btnDelete.addActionListener(this);
        btnDisplay.addActionListener(this);

        txtPRN.addFocusListener(this);
        txtName.addFocusListener(this);
        txtEmail.addFocusListener(this);
        txtContact.addFocusListener(this);
        txtParentContact.addFocusListener(this);
        txtAddress.addFocusListener(this);

        btnAdd.setBackground(Color.decode("#d9650b"));
        btnModify.setBackground(Color.decode("#d9650b"));
        btnDelete.setBackground(Color.decode("#d9650b"));
        btnDisplay.setBackground(Color.decode("#d9650b"));

        btnAdd.setForeground(Color.WHITE);
        btnDelete.setForeground(Color.WHITE);
        btnModify.setForeground(Color.WHITE);
        btnDisplay.setForeground(Color.WHITE);

        // Positioning Components
        lblPRN.setBounds(50, 30, 120, 30);
        txtPRN.setBounds(180, 30, 200, 30);

        lblName.setBounds(50, 70, 120, 30);
        txtName.setBounds(180, 70, 200, 30);

        lblGender.setBounds(50, 110, 120, 30);
        rbMale.setBounds(180, 110, 80, 30);
        rbFemale.setBounds(260, 110, 80, 30);

        lblTrade.setBounds(50, 150, 120, 30);
        cbTrade.setBounds(180, 150, 200, 30);

        lblCat.setBounds(50, 190, 120, 30);
        cbCat.setBounds(180, 190, 200, 30);

        lblEmail.setBounds(50, 230, 120, 30);
        txtEmail.setBounds(180, 230, 200, 30);

        lblYear.setBounds(50, 270, 120, 30);
        cbYear.setBounds(180, 270, 200, 30);

        lblContact.setBounds(50, 310, 120, 30);
        txtContact.setBounds(180, 310, 200, 30);

        lblParentContact.setBounds(50, 350, 120, 30);
        txtParentContact.setBounds(180, 350, 200, 30);

        lblAddress.setBounds(50, 390, 120, 30);
        txtAddress.setBounds(180, 390, 200, 30);


        btnAdd.setBounds(50, 450, 80, 30);
        btnModify.setBounds(140, 450, 80, 30);
        btnDelete.setBounds(230, 450, 80, 30);
        btnDisplay.setBounds(320, 450, 80, 30);

        // Adding Components
        mainPanel.add(lblPRN);
        mainPanel.add(txtPRN);
        mainPanel.add(lblName);
        mainPanel.add(txtName);
        mainPanel.add(lblGender);
        // mainPanel.add(lblTrdID);
        // mainPanel.add(cbTrdID);
        mainPanel.add(lblTrade);
        mainPanel.add(cbTrade);
        // mainPanel.add(lblCatID);
        // mainPanel.add(cbCatID);
        mainPanel.add(lblCat);
        mainPanel.add(cbCat);
        mainPanel.add(rbMale);
        mainPanel.add(rbFemale);
        mainPanel.add(lblEmail);
        mainPanel.add(txtEmail);
        mainPanel.add(lblYear);
        mainPanel.add(cbYear);
        mainPanel.add(lblContact);
        mainPanel.add(txtContact);
        mainPanel.add(lblParentContact);
        mainPanel.add(txtParentContact);
        mainPanel.add(lblAddress);
        mainPanel.add(txtAddress);
        mainPanel.add(btnAdd);
        mainPanel.add(btnModify);
        mainPanel.add(btnDelete);
        mainPanel.add(btnDisplay);
        mainPanel.setBackground(Color.WHITE);
        
        mainPanel.setBackground(Color.decode("#f9e8c3"));

        add(mainPanel);

        setSize(500, 550);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // getContentPane().setBackground(Color.BLACK);
        setVisible(true);
    }
    private void resetFields() {
        txtPRN.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        txtParentContact.setText("");
        txtAddress.setText("");
        
        rbMale.setSelected(false);
        rbFemale.setSelected(false);
        
        cbTrade.setSelectedItem(null);
        cbCat.setSelectedItem(null);
        cbYear.setSelectedItem(null);
    
        btnAdd.setEnabled(true);
        btnModify.setEnabled(true);
        btnDelete.setEnabled(true);
    }
    public void focusGained(FocusEvent e) {}
    public void focusLost(FocusEvent e) {
        JTextField t = (JTextField) e.getSource();
    
        if (t == txtPRN) {
            System.out.println("focusLost triggered");
            try {
                int prn = Integer.parseInt(txtPRN.getText());
    
                PreparedStatement ps5 = con.prepareStatement(
                    "SELECT name, gender, trade, category, year, email, contact_no, parents_contact, address FROM students WHERE prn=?"
                );
                ps5.setInt(1, prn);
                ResultSet rs1 = ps5.executeQuery();
    
                if (rs1.next()) {
                    // No need to set txtPRN here, since the user already entered it.
                    txtName.setText(rs1.getString("name"));
                    txtEmail.setText(rs1.getString("email"));
                    txtContact.setText(rs1.getString("contact_no"));
                    txtParentContact.setText(rs1.getString("parents_contact"));
                    txtAddress.setText(rs1.getString("address"));
    
                    cbTrade.setSelectedItem(rs1.getString("trade"));
                    cbCat.setSelectedItem(rs1.getString("category"));
                    cbYear.setSelectedItem(rs1.getString("year"));
    
                    String gender = rs1.getString("gender");
                    if ("Male".equalsIgnoreCase(gender)) {
                        rbMale.setSelected(true);
                    } else {
                        rbFemale.setSelected(true);
                    }
                    txtPRN.setEditable(false);
                    btnAdd.setEnabled(false);
                    btnModify.setEnabled(true);
                    btnDelete.setEnabled(true);
                } else {
                    System.out.println("PRN not found in database");
                    btnAdd.setEnabled(true);
                    btnModify.setEnabled(false);
                    btnDelete.setEnabled(false);
                    
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid PRN: " + ex.getMessage());
            } catch (SQLException ex) {
                System.out.println("Database Error: " + ex.getMessage());
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (btn == btnAdd) {
            addRecord();
        } else if (btn == btnModify) {
            modifyRecord();
        } else if (btn == btnDelete) {
            deleteRecord();
        } else if (btn == btnDisplay) {
            new SDisplay(this, con, "Student Records", true);
        }
    }

    void addRecord() {
        try {
            PreparedStatement ps1 = con.prepareStatement("Select tradeid from trade where tradename=?");
            ps1.setString(1,(String) cbTrade.getSelectedItem());
            ResultSet rs = ps1.executeQuery();
        
            int tradeid = -1; // Default value in case no result is found
            if (rs.next()) {
                tradeid = rs.getInt("tradeid");
            } else {
                System.out.println("Trade ID not found for trade name: " + cbTrade.getSelectedItem());
                return; // Exit if tradeid is not found
            }
            PreparedStatement ps2 = con.prepareStatement("Select cid from category where cname=?");
            ps2.setString(1,(String) cbCat.getSelectedItem());

            ResultSet rs2 = ps2.executeQuery();
        
            int categoryid = -1; // Default value in case no result is found
            if (rs2.next()) {
                categoryid = rs2.getInt("cid");
            } else {
                System.out.println("Category ID not found for Category name: " + cbCat.getSelectedItem());
                return; // Exit if categoryid is not found
            }
            PreparedStatement ps = con.prepareStatement("INSERT INTO students (prn, name, gender,tradeid,trade,categoryid,category,  year,email, contact_no, parents_contact, address) VALUES (?, ?, ?, ?,?,?,?,?, ?, ?, ?, ?)");
            ps.setInt(1, Integer.parseInt(txtPRN.getText()));
            ps.setString(2, txtName.getText());
            ps.setString(3, rbMale.isSelected() ? "Male" : "Female");

            ps.setInt(4, tradeid);
            ps.setString(5, (String) cbTrade.getSelectedItem());

           ps.setInt(6, categoryid);
            ps.setString(7, (String) cbCat.getSelectedItem());

            ps.setString(9, txtEmail.getText());
            ps.setString(8, (String) cbYear.getSelectedItem());

            ps.setString(10, (txtContact.getText()));
            ps.setString(11, (txtParentContact.getText()));
            ps.setString(12, txtAddress.getText());
            int rowsUpdated = ps.executeUpdate();
            resetFields();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Record Added Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Unable to add record");
            }
        } catch (SQLException ex) {
            System.out.println("Add Error: " + ex);
        }
    }
    void modifyRecord() {
        try {
            PreparedStatement ps1 = con.prepareStatement("select tradeid from trade where tradename = ?");
            ps1.setString(1,(String) cbTrade.getSelectedItem());
            ResultSet rs1 = ps1.executeQuery();
            int tradeid = -1;
            if (rs1.next()) {
                tradeid = rs1.getInt("tradeid");
            } else {
                System.out.println("Trade ID not found for trade name: " + cbTrade.getSelectedItem());
                return; // Exit if tradeid is not found
            }

            PreparedStatement ps2 = con.prepareStatement("select cid from category where cname = ?");
            ps2.setString(1,(String) cbCat.getSelectedItem());
            ResultSet rs2= ps2.executeQuery();
            int cid = -1;
            if (rs2.next()) {
                cid = rs2.getInt("cid");
            } else {
                System.out.println("Trade ID not found for category name: " + cbCat.getSelectedItem());
                return; // Exit if tradeid is not found
            }

            PreparedStatement ps = con.prepareStatement(
                "UPDATE students SET name=?, gender=?,tradeid=?,trade=?,categoryid=?,category=?, email=?, year=?, contact_no=?, parents_contact=?, address=? WHERE prn=?"
            );
            ps.setString(1, txtName.getText());
    
            String gender = rbMale.isSelected() ? "Male" : "Female";
            ps.setString(2, gender);    
            ps.setInt(3,tradeid);
            
            ps.setString(4, ( String) (cbTrade.getSelectedItem()));
            ps.setInt(5,cid);
            ps.setString(6, ( String) (cbCat.getSelectedItem()));
            ps.setString(7, txtEmail.getText());
            ps.setString(8, cbYear.getSelectedItem().toString());
    
            ps.setString(9, txtContact.getText());
            ps.setString(10, txtParentContact.getText());
            ps.setString(11, txtAddress.getText());
    
            ps.setInt(12, Integer.parseInt(txtPRN.getText()));
    
            int rowsUpdated = ps.executeUpdate();
    
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Record Modified Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No Record Found with the Given PRN.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Modify Error: " + e.getMessage());
        }
    }
    void deleteRecord() {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM students WHERE prn=?");
            ps.setInt(1, Integer.parseInt(txtPRN.getText()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete Error: " + e);
        }
    }
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/hostel management system", "root", "");
        new Project_Student1(con);
    }
}
    
