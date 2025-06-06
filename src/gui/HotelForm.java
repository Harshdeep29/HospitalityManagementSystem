package gui;

import javax.swing.*;
import java.awt.*;

public class HotelForm extends JFrame {
    private final MainFrame mainFrame;
    public HotelForm(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setTitle("Hotel Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Hotel Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JButton addHotel = new JButton("Add Hotel");
        JButton deleteHotel = new JButton("Delete Hotel");
        JButton editHotel = new JButton("Edit Hotel");
        JButton viewHotel = new JButton("View Hotel");

        JPanel panel = new JPanel(new GridLayout(4,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,100,20,100));
        panel.add(addHotel);
        panel.add(deleteHotel);
        panel.add(editHotel);
        panel.add(viewHotel);
        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel= new JPanel(new FlowLayout());
        JButton back = new JButton("Back");
        JButton exit = new JButton("Exit");
        buttonPanel.add(back);
        buttonPanel.add(exit);
        add(buttonPanel, BorderLayout.SOUTH);

        back.addActionListener(e -> {
            this.dispose();
            this.mainFrame.setVisible(true);
        });
        exit.addActionListener(e -> {
            System.exit(0);
        });

        setVisible(true);

    }
}
