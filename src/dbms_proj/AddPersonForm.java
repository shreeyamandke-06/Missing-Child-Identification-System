package dbms_proj;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddPersonForm extends JFrame {

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtGender;
    private JTextField txtContact;
    private JComboBox<String> cmbPersonType;
    private JButton btnSave;

    public AddPersonForm() {

        setTitle("Add Person");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("First Name"));
        txtFirstName = new JTextField();
        panel.add(txtFirstName);

        panel.add(new JLabel("Last Name"));
        txtLastName = new JTextField();
        panel.add(txtLastName);

        panel.add(new JLabel("Gender"));
        txtGender = new JTextField();
        panel.add(txtGender);

        panel.add(new JLabel("Contact Number"));
        txtContact = new JTextField();
        panel.add(txtContact);

        panel.add(new JLabel("Person Type"));
        cmbPersonType = new JComboBox<>(
                new String[]{"Child", "Parent", "Guardian"});
        panel.add(cmbPersonType);

        btnSave = new JButton("Save");
        panel.add(new JLabel());
        panel.add(btnSave);

        add(panel);

        btnSave.addActionListener(e -> savePerson());
    }

    private void savePerson() {

        String sql =
                "INSERT INTO person(FirstName, LastName, Gender, Contact_number, PersonType) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtFirstName.getText());
            ps.setString(2, txtLastName.getText());
            ps.setString(3, txtGender.getText());
            ps.setDouble(4, Double.parseDouble(txtContact.getText()));
            ps.setString(5, cmbPersonType.getSelectedItem().toString());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Person added successfully!");

            dispose();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "Please enter a valid contact number.");
        }

    }
}