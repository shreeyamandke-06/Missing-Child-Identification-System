package dbms_proj;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class ReportMissingUI extends JFrame {

    private JComboBox<PersonItem> childCombo;
    private JComboBox<PersonItem> parentCombo;
    private JComboBox<PersonItem> locationCombo;
    private JComboBox<String> statusCombo;

    private JTextArea descriptionArea;
    private JTextField dateField;
    private JButton btnRegister;

    public ReportMissingUI() {

        setTitle("Report Missing Child");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        childCombo = new JComboBox<>();
        parentCombo = new JComboBox<>();
        locationCombo = new JComboBox<>();
        statusCombo = new JComboBox<>(new String[]{"Open", "Closed"});

        descriptionArea = new JTextArea(4, 20);

        dateField = new JTextField(LocalDate.now().toString());

        btnRegister = new JButton("Register Case");

        panel.add(new JLabel("Child"));
        panel.add(childCombo);

        panel.add(new JLabel("Parent"));
        panel.add(parentCombo);

        panel.add(new JLabel("Location"));
        panel.add(locationCombo);

        panel.add(new JLabel("Case Status"));
        panel.add(statusCombo);

        panel.add(new JLabel("Date Reported"));
        panel.add(dateField);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        add(btnRegister, BorderLayout.SOUTH);

        loadChildren();
        loadParents();
        loadLocations();

        btnRegister.addActionListener(e -> registerCase());
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadParents() {

        String sql = """
                SELECT p.ParentID,
                       pe.FirstName,
                       pe.LastName
                FROM parent p
                JOIN person pe
                ON p.PersonID = pe.PersonId
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                parentCombo.addItem(
                        new PersonItem(
                                rs.getInt("ParentID"),
                                rs.getString("FirstName")
                                        + " "
                                        + rs.getString("LastName")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadLocations() {

        String sql = "SELECT LocationID, PlaceName FROM location";

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerCase() {

        String sql = """
                INSERT INTO lost_case
                (CaseStatus, Description, DateReported,
                 ChildID, ParentID, LocationID)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            PersonItem child =
                    (PersonItem) childCombo.getSelectedItem();

            PersonItem parent =
                    (PersonItem) parentCombo.getSelectedItem();

            PersonItem location =
                    (PersonItem) locationCombo.getSelectedItem();

            ps.setString(
                    1,
                    statusCombo.getSelectedItem().toString()
            );

            ps.setString(
                    2,
                    descriptionArea.getText()
            );

            ps.setDate(
                    3,
                    Date.valueOf(dateField.getText())
            );

            ps.setInt(4, child.getId());
            ps.setInt(5, parent.getId());
            ps.setInt(6, location.getId());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Case registered successfully!"
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