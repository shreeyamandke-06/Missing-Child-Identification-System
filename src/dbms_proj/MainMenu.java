package dbms_proj;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {

        setTitle("Missing Child Identification System");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JLabel title = new JLabel(
                "Missing Child Tracking & DNA Matching System",
                SwingConstants.CENTER);

        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JButton addPersonBtn = new JButton("Add Person");
        addPersonBtn.addActionListener(e -> {
            new AddPersonForm().setVisible(true);
        });
        JButton reportBtn = new JButton("Report Missing Child");
        reportBtn.addActionListener(e -> {
            new ReportMissingUI().setVisible(true);
        });
        JButton searchBtn = new JButton("Search Lost Child");
        JButton updateBtn = new JButton("Update Case Status");
        JButton viewBtn = new JButton("View Open Cases");
        JButton exitBtn = new JButton("Exit");

        panel.add(addPersonBtn);
        panel.add(reportBtn);
        panel.add(searchBtn);
        panel.add(updateBtn);
        panel.add(viewBtn);
        panel.add(exitBtn);

        add(panel, BorderLayout.CENTER);

        exitBtn.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}