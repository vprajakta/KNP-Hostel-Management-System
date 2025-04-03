import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Project_HostelStructure extends JFrame {

    private JTable roomTable;
    private DefaultTableModel tableModel;
    private JButton allocateButton;

    public Project_HostelStructure() {
        setTitle("Table-Based Room Allocation");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table columns: Room Number, Room Type, Status, Allocate (checkbox)
        String[] columnNames = {"Room Number", "Room Type", "Status", "Allocate"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                // Make the "Allocate" column use checkboxes
                return (column == 3) ? Boolean.class : String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow editing (checking) the checkbox in the "Allocate" column
                return column == 3;
            }
        };

        roomTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomTable);
        add(scrollPane, BorderLayout.CENTER);

        // Allocate button to allocate the selected rooms
        allocateButton = new JButton("Allocate Selected Rooms");
        allocateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allocateSelectedRooms();
            }
        });
        add(allocateButton, BorderLayout.SOUTH);

        // Load room data into the table
        loadRoomData();

        setVisible(true);
    }

    private void loadRoomData() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/hostel_structure", "root", "");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM rooms");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String roomNumber = resultSet.getString("room_number");
                String roomType = resultSet.getString("room_type");
                boolean isOccupied = resultSet.getBoolean("occupied");
                String status = isOccupied ? "Occupied" : "Available";

                // Add room details to the table
                Object[] row = new Object[4];
                row[0] = roomNumber;
                row[1] = roomType;
                row[2] = status;
                row[3] = !isOccupied; // Checkbox is checked only if the room is available

                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading room data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void allocateSelectedRooms() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean isSelected = (Boolean) tableModel.getValueAt(i, 3); // Check if the room is selected
            String roomNumber = (String) tableModel.getValueAt(i, 0);

            if (Boolean.TRUE.equals(isSelected)) {
                // Allocate the room by updating the database and UI
                allocateRoom(roomNumber, i);
            }
        }
    }

    private void allocateRoom(String roomNumber, int rowIndex) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/hostel_structure", "root", "");
             PreparedStatement statement = connection.prepareStatement("UPDATE rooms SET occupied = ? WHERE room_number = ?")) {

            // Update the room status to occupied
            statement.setBoolean(1, true);
            statement.setString(2, roomNumber);
            statement.executeUpdate();

            // Update the table UI
            tableModel.setValueAt("Occupied", rowIndex, 2); // Update status to "Occupied"
            tableModel.setValueAt(false, rowIndex, 3); // Uncheck the checkbox and disable it

            JOptionPane.showMessageDialog(this, "Room " + roomNumber + " allocated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error allocating room: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Project_HostelStructure());
    }
}
