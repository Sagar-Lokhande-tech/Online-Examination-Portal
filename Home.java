package examportal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {

    public Home() {

        JFrame frame = new JFrame("Exam Portal");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Exam Portal", JLabel.CENTER);
        titleLabel.setBounds(100, 20, 200, 30);

        JButton studentButton = new JButton("Student");
        studentButton.setBounds(140, 80, 120, 40);

        JButton adminButton = new JButton("Admin");
        adminButton.setBounds(140, 140, 120, 40);

        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Registration();
                frame.dispose(); // Close Home window
            }
        });

        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Admin();
                frame.dispose(); // Close Home window
            }
        });

        frame.add(titleLabel);
        frame.add(studentButton);
        frame.add(adminButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Home();
    }
}