package dbms_proj;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StatusHistoryUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public StatusHistoryUI() {

        setTitle("Case Status History");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        model = new DefaultTableModel();

        model.addColumn("Log ID");
        model.addColumn("Case ID");
        model.addColumn("Old Status");
        model.addColumn("New Status");
        model.addColumn("Changed At");

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadHistory();
    }

    private void loadHistory() {

        String sql = """
                SELECT *
                FROM case_status_log
                ORDER BY ChangedAt DESC
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                model.addRow(new Object[] {
                        rs.getInt("LogID"),
                        rs.getInt("CaseID"),
                        rs.getString("OldStatus"),
                        rs.getString("NewStatus"),
                        rs.getTimestamp("ChangedAt")
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