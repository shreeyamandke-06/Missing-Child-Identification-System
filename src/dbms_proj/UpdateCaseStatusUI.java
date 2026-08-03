package dbms_proj;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateCaseStatusUI extends JFrame {

    private JTextField caseIdField;
    private JComboBox<String> statusCombo;
    private JButton updateButton;

    public UpdateCaseStatusUI() {

        setTitle("Update Case Status");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        caseIdField = new JTextField();

        statusCombo = new JComboBox<>(
                new String[]{
                        "Open",
                        "Closed",
                        "Found",
                        "Missing"
                }
        );

        updateButton = new JButton("Update");

        panel.add(new JLabel("Case ID"));
        panel.add(caseIdField);

        panel.add(new JLabel("Status"));
        panel.add(statusCombo);

        panel.add(new JLabel(""));
        panel.add(updateButton);

        add(panel);

        updateButton.addActionListener(
                e -> updateStatus()
        );
    }

    private void updateStatus() {

        String sql = """
                UPDATE lost_case
                SET CaseStatus = ?
                WHERE CaseID = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql)) {

            ps.setString(
                    1,
                    statusCombo.getSelectedItem().toString()
            );

            ps.setInt(
                    2,
                    Integer.parseInt(caseIdField.getText())
            );

            int rows = ps.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Status updated successfully."
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Case not found."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}