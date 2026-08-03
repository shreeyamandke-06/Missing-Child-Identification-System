package dbms_proj;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMissingCasesUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public ViewMissingCasesUI() {

        setTitle("View Missing Cases");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();

        model.addColumn("Case ID");
        model.addColumn("Child Name");
        model.addColumn("Parent Name");
        model.addColumn("Location");
        model.addColumn("Status");
        model.addColumn("Date Reported");
        model.addColumn("Description");

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadCases();
    }

    private void loadCases() {

        String sql = """
                SELECT
                    lc.CaseID,
                    CONCAT(c.FirstName, ' ', c.LastName) AS ChildName,
                    CONCAT(pe.FirstName, ' ', pe.LastName) AS ParentName,
                    l.PlaceName,
                    lc.CaseStatus,
                    lc.DateReported,
                    lc.Description

                FROM lost_case lc

                JOIN child c
                ON lc.ChildID = c.ChildID

                JOIN parent p
                ON lc.ParentID = p.ParentID

                JOIN person pe
                ON p.PersonID = pe.PersonId

                JOIN location l
                ON lc.LocationID = l.LocationID
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                model.addRow(new Object[]{

                        rs.getInt("CaseID"),
                        rs.getString("ChildName"),
                        rs.getString("ParentName"),
                        rs.getString("PlaceName"),
                        rs.getString("CaseStatus"),
                        rs.getDate("DateReported"),
                        rs.getString("Description")

                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}