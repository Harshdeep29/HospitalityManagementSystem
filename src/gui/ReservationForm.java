package gui;

import javax.swing.*;
import java.awt.*;

public class ReservationForm extends JFrame {
    private final MainFrame mainFrame;
    public ReservationForm(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setTitle("Reservation Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel title = new JLabel("Reservation Management", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JButton newReservation = new JButton("New Reservation");
        JButton cancelReservation = new JButton("Cancel Reservation");
        JButton viewReservation = new JButton("View Reservation");

        JPanel panel = new JPanel(new GridLayout(3,1));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        panel.add(newReservation);
        panel.add(cancelReservation);
        panel.add(viewReservation);
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
