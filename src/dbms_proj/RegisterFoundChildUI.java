package dbms_proj;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class RegisterFoundChildUI extends JFrame {

    private JComboBox<PersonItem> childCombo;
    private JComboBox<PersonItem> locationCombo;

    private JTextField dateField;
    private JTextArea descriptionArea;

    private JButton registerButton;

    public RegisterFoundChildUI() {

        setTitle("Register Found Child");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        childCombo = new JComboBox<>();
        locationCombo = new JComboBox<>();

        dateField = new JTextField(LocalDate.now().toString());

        descriptionArea = new JTextArea();

        registerButton = new JButton("Register");

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Child"));
        panel.add(childCombo);

        panel.add(new JLabel("Location"));
        panel.add(locationCombo);

        panel.add(new JLabel("Found Date"));
        panel.add(dateField);

        panel.add(registerButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);

        loadChildren();
        loadLocations();

        registerButton.addActionListener(
                e -> registerFoundChild()
        );
    }

    private void loadChildren() {

        String sql = """
                SELECT ChildID, FirstName, LastName
                FROM child
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                childCombo.addItem(
                        new PersonItem(
                                rs.getInt("ChildID"),
                                rs.getString("FirstName")
                                        + " "
                                        + rs.getString("LastName")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLocations() {

        String sql =
                "SELECT LocationID, PlaceName FROM location";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                locationCombo.addItem(
                        new PersonItem(
                                rs.getInt("LocationID"),
                                rs.getString("PlaceName")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerFoundChild() {

        String sql = """
                INSERT INTO found_child
                (ChildID, FoundDate, FoundLocation, Description)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            PersonItem child =
                    (PersonItem) childCombo.getSelectedItem();

            PersonItem location =
                    (PersonItem) locationCombo.getSelectedItem();

            ps.setInt(1, child.getId());
            ps.setDate(2, Date.valueOf(dateField.getText()));
            ps.setInt(3, location.getId());
            ps.setString(4, descriptionArea.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Found child registered successfully."
            );

            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}