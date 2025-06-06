package gui;

import javax.swing.*;
import java.awt.*;

public class RoomForm extends JFrame{
    private final MainFrame mainFrame;
    public RoomForm(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setTitle("Room Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Hotel Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JButton addRoom = new JButton("Add Room");
        JButton removeRoom = new JButton("Remove Room");
        JButton editRoom = new JButton("Edit Room");
        JButton viewRoom = new JButton("View Room");

        JPanel panel = new JPanel(new GridLayout(4,1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        panel.add(addRoom);
        panel.add(removeRoom);
        panel.add(editRoom);
        panel.add(viewRoom);
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
        exit.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }
}
