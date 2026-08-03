package dbms_proj;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterChildUI extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField markField;

    private JButton registerButton;

    public RegisterChildUI() {

        setTitle("Register Child");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        dobField = new JTextField();
        markField = new JTextField();

        registerButton = new JButton("Register");

        panel.add(new JLabel("First Name"));
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name"));
        panel.add(lastNameField);

        panel.add(new JLabel("Date of Birth (yyyy-mm-dd)"));
        panel.add(dobField);

        panel.add(new JLabel("Distinguishing Mark"));
        panel.add(markField);

        panel.add(new JLabel(""));
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(e -> registerChild());
    }

    private void registerChild() {

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String personSQL = """
                    INSERT INTO person
                    (FirstName, LastName, Gender, PersonType)
                    VALUES (?, ?, ?, ?)
                    """;

            PreparedStatement ps1 = con.prepareStatement(personSQL);

            ps1.setString(1, firstNameField.getText());
            ps1.setString(2, lastNameField.getText());
            ps1.setString(3, "Unknown");
            ps1.setString(4, "Child");

            ps1.executeUpdate();

            String childSQL = """
                    INSERT INTO child
                    (FirstName, LastName, DOB, DistinguishingMark, Status)
                    VALUES (?, ?, ?, ?, ?)
                    """;

            PreparedStatement ps2 = con.prepareStatement(childSQL);

            ps2.setString(1, firstNameField.getText());
            ps2.setString(2, lastNameField.getText());
            ps2.setDate(3, Date.valueOf(dobField.getText()));
            ps2.setString(4, markField.getText());
            ps2.setString(5, "Missing");

            ps2.executeUpdate();

            con.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Child registered successfully!"
            );

            dispose();

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}