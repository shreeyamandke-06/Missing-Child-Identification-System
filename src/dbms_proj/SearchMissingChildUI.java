package dbms_proj;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class SearchMissingChildUI extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JTable table;
    private DefaultTableModel model;

    public SearchMissingChildUI() {

        setTitle("Search Missing Child");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel();

        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        topPanel.add(new JLabel("Child Name"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

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

        searchButton.addActionListener(e -> searchChild());
    }

    private void searchChild() {

        model.setRowCount(0);

        String sql = """
                SELECT
                    lc.CaseID,
                    CONCAT(c.FirstName,' ',c.LastName) AS ChildName,
                    CONCAT(p.FirstName,' ',p.LastName) AS ParentName,
                    l.PlaceName,
                    lc.CaseStatus,
                    lc.DateReported,
                    lc.Description

                FROM lost_case lc

                JOIN child c
                ON lc.ChildID = c.ChildID

                JOIN parent pa
                ON lc.ParentID = pa.ParentID

                JOIN person p
                ON pa.PersonID = p.PersonID

                JOIN location l
                ON lc.LocationID = l.LocationID

                WHERE CONCAT(c.FirstName,' ',c.LastName) LIKE ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + searchField.getText() + "%");

            ResultSet rs = ps.executeQuery();

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

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}