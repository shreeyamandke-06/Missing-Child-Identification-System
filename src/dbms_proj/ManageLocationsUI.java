package dbms_proj;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ManageLocationsUI extends JFrame {

    private JTextField placeField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField countryField;
    private JTextField latitudeField;
    private JTextField longitudeField;

    private JButton addButton;


    public ManageLocationsUI() {

        setTitle("Manage Locations");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));


        placeField = new JTextField();
        cityField = new JTextField();
        stateField = new JTextField();
        countryField = new JTextField();
        latitudeField = new JTextField();
        longitudeField = new JTextField();

        addButton = new JButton("Add Location");


        panel.add(new JLabel("Place Name"));
        panel.add(placeField);

        panel.add(new JLabel("City"));
        panel.add(cityField);

        panel.add(new JLabel("State"));
        panel.add(stateField);

        panel.add(new JLabel("Country"));
        panel.add(countryField);

        panel.add(new JLabel("Latitude"));
        panel.add(latitudeField);

        panel.add(new JLabel("Longitude"));
        panel.add(longitudeField);

        panel.add(addButton);


        add(panel);


        addButton.addActionListener(e -> addLocation());

    }



    private void addLocation() {

        String sql = """
                INSERT INTO location
                (PlaceName, City, State, Country, Latitude, Longitude)
                VALUES (?, ?, ?, ?, ?, ?)
                """;


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {


            ps.setString(1, placeField.getText());
            ps.setString(2, cityField.getText());
            ps.setString(3, stateField.getText());
            ps.setString(4, countryField.getText());

            ps.setDouble(5,
                    Double.parseDouble(latitudeField.getText()));

            ps.setDouble(6,
                    Double.parseDouble(longitudeField.getText()));


            ps.executeUpdate();


            JOptionPane.showMessageDialog(
                    this,
                    "Location Added Successfully!"
            );


            dispose();


        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );

        }
    }
}