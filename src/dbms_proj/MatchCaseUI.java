package dbms_proj;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MatchCaseUI extends JFrame {

    private JTextField childIdField;
    private JButton searchButton;
    private JButton confirmButton;

    private JTable table;
    private DefaultTableModel model;

    public MatchCaseUI() {

        setTitle("Match Found Child");
        setSize(850, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();

        childIdField = new JTextField(10);

        searchButton = new JButton("Search");
        confirmButton = new JButton("Confirm Match");

        topPanel.add(new JLabel("Child ID"));
        topPanel.add(childIdField);
        topPanel.add(searchButton);
        topPanel.add(confirmButton);

        add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel();

        model.addColumn("Case ID");
        model.addColumn("Child Name");
        model.addColumn("Parent Name");
        model.addColumn("Location");
        model.addColumn("Status");
        model.addColumn("Date");

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchCases());

        confirmButton.addActionListener(e -> confirmMatch());
    }

    private void searchCases() {

        model.setRowCount(0);

        String sql = """
                SELECT
                    lc.CaseID,
                    CONCAT(c.FirstName,' ',c.LastName) AS ChildName,
                    CONCAT(p.FirstName,' ',p.LastName) AS ParentName,
                    l.PlaceName,
                    lc.CaseStatus,
                    lc.DateReported

                FROM lost_case lc

                JOIN child c
                ON lc.ChildID = c.ChildID

                JOIN parent pa
                ON lc.ParentID = pa.ParentID

                JOIN person p
                ON pa.PersonID = p.PersonId

                JOIN location l
                ON lc.LocationID = l.LocationID

                WHERE lc.ChildID = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(
                    1,
                    Integer.parseInt(childIdField.getText())
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("CaseID"),
                        rs.getString("ChildName"),
                        rs.getString("ParentName"),
                        rs.getString("PlaceName"),
                        rs.getString("CaseStatus"),
                        rs.getDate("DateReported")
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void confirmMatch() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a case."
            );

            return;
        }

        int caseId = (int) model.getValueAt(row, 0);

        String sql = """
                UPDATE lost_case
                SET CaseStatus = 'Closed'
                WHERE CaseID = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, caseId);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Case updated successfully."
            );

            model.removeRow(row);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}