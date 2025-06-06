package gui;

import javax.swing.*;
import java.awt.*;

public class GuestForm extends JFrame {
    private final MainFrame mainFrame;
    public GuestForm(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setTitle("Guest Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Guest Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JButton addGuest = new JButton("Add Guest");
        JButton deleteGuest = new JButton("Delete Guest");
        JButton editGuest = new JButton("Edit Guest");
        JButton viewGuest = new JButton("View Guest");

        JPanel panel = new JPanel(new GridLayout(4,1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        panel.add(addGuest);
        panel.add(deleteGuest);
        panel.add(editGuest);
        panel.add(viewGuest);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton back = new JButton("Back");
        JButton exit = new JButton("Exit");
        buttonPanel.add(back);
        buttonPanel.add(exit);
        add(buttonPanel, BorderLayout.SOUTH);

        back.addActionListener(e ->{
            this.dispose();
            this.mainFrame.setVisible(true);
        });
        exit.addActionListener(e ->{
            dispose();
        });

        setVisible(true);

    }
}
