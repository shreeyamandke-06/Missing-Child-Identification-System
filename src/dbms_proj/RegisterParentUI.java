package dbms_proj;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterParentUI extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField genderField;
    private JTextField phoneField;
    private JTextField emailField;

    private JButton registerButton;

    public RegisterParentUI() {

        setTitle("Register Parent");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        genderField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        registerButton = new JButton("Register");

        panel.add(new JLabel("First Name"));
        panel.add(firstNameField);

        panel.add(new JLabel("Last Name"));
        panel.add(lastNameField);

        panel.add(new JLabel("Gender"));
        panel.add(genderField);

        panel.add(new JLabel("Phone Number"));
        panel.add(phoneField);

        panel.add(new JLabel("Email"));
        panel.add(emailField);

        panel.add(new JLabel(""));
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(e -> registerParent());
    }

    private void registerParent() {

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            String personSQL = """
                    INSERT INTO person
                    (FirstName, LastName, Gender, Contact_number, PersonType)
                    VALUES (?, ?, ?, ?, ?)
                    """;

            PreparedStatement ps1 = con.prepareStatement(
                    personSQL,
                    Statement.RETURN_GENERATED_KEYS
            );

            ps1.setString(1, firstNameField.getText());
            ps1.setString(2, lastNameField.getText());
            ps1.setString(3, genderField.getText());
            ps1.setDouble(4, Double.parseDouble(phoneField.getText()));
            ps1.setString(5, "Parent");

            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();

            int personId = 0;

            if (rs.next()) {
                personId = rs.getInt(1);
            }

            String parentSQL = """
                    INSERT INTO parent
                    (Email, PersonID)
                    VALUES (?, ?)
                    """;

            PreparedStatement ps2 = con.prepareStatement(parentSQL);

            ps2.setString(1, emailField.getText());
            ps2.setInt(2, personId);

            ps2.executeUpdate();

            con.commit();

            JOptionPane.showMessageDialog(
                    this,
                    "Parent registered successfully!"
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