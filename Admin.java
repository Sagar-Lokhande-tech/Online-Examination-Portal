package examportal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Admin implements ActionListener {
    // GUI Components
    JFrame frame;
    JButton btnGetRegisteredStudents, btnGetData, btnGetMarks;
    JTextArea txtOutputArea;
    JScrollPane scrollPane;
    JTextField txtRollNoInput;
    JLabel lblEnterRollNo, lblTitle;

    public Admin() {
        // 1. Creating the Main Window Frame
        frame = new JFrame("Admin Control Dashboard");
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Closes just this window, not the whole app
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(240, 244, 248)); // Clean background color

        // Title Label
        lblTitle = new JLabel("Admin Dashboard Panel", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(50, 15, 500, 30);
        frame.add(lblTitle);

        // 2. Button: Get Registered Students (Top Main Button)
        btnGetRegisteredStudents = new JButton("Get Registered Student");
        btnGetRegisteredStudents.setBounds(50, 60, 500, 40);
        btnGetRegisteredStudents.setFont(new Font("Arial", Font.BOLD, 13));
        btnGetRegisteredStudents.addActionListener(this);
        frame.add(btnGetRegisteredStudents);

        // 3. Central Output Console Box (Where database text prints out)
        txtOutputArea = new JTextArea();
        txtOutputArea.setEditable(false); // Keeps it read-only
        txtOutputArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Keeps columns perfectly straight
        
        // Wrapping the text area in a ScrollPane so you can scroll if there are many students
        scrollPane = new JScrollPane(txtOutputArea);
        scrollPane.setBounds(50, 120, 500, 250);
        frame.add(scrollPane);

        // 4. Bottom Search Section: "Enter Roll No"
        lblEnterRollNo = new JLabel("Enter Roll No:");
        lblEnterRollNo.setBounds(50, 400, 100, 30);
        lblEnterRollNo.setFont(new Font("Arial", Font.BOLD, 12));
        frame.add(lblEnterRollNo);

        txtRollNoInput = new JTextField();
        txtRollNoInput.setBounds(150, 400, 100, 30);
        frame.add(txtRollNoInput);

        // 5. Query Buttons: Get Data & Get Marks
        btnGetData = new JButton("Get Data");
        btnGetData.setBounds(270, 400, 120, 30);
        btnGetData.addActionListener(this);
        frame.add(btnGetData);

        btnGetMarks = new JButton("Get Marks");
        btnGetMarks.setBounds(410, 400, 140, 30);
        btnGetMarks.addActionListener(this);
        frame.add(btnGetMarks);

        // Make window visible
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Database connection configurations
    	String url = "jdbc:mysql://localhost:3306/examportal";
    	String user = "root";
    	String pass = "SL061@sal"; // Matches your registration database password

        // --- BUTTON ACTION 1: Get Registered Student ---
        if (e.getSource() == btnGetRegisteredStudents) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                Statement stmt = con.createStatement();
                
                // Runs select * from admin query
                ResultSet rs = stmt.executeQuery("SELECT * FROM student");

                StringBuilder output = new StringBuilder();
                // Column titles header
                output.append(String.format("%-10s %-15s %-10s %-10s %-20s %-15, %-15\n", "Roll No", "Name", "Gender", "Dept", "Email" ,"pwd", "contact"));
                output.append("--------------------------------------------------------------------------------\n");

                // Loop over rows in the database table and append them
                while(rs.next()) {
                    output.append(String.format(
                        "%-10s %-15s %-10s %-10s %-20s %-15s %-15s\n",
                        rs.getString("rollno"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("dept"),
                        rs.getString("email"),
                        rs.getString("pwd"),
                        rs.getString("contact")
                    ));
                }
                txtOutputArea.setText(output.toString()); // Show inside the text area console
                
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error connecting to Database: " + ex.getMessage());
            }
        }

        // --- BUTTON ACTION 2: Get Data (For a single specific student) ---
        else if (e.getSource() == btnGetData) {
            String inputRoll = txtRollNoInput.getText().trim();
            if (inputRoll.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please type a Roll Number first!");
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                
                // Querying filtered profile details based on matching rno
                String query =
                		"SELECT rollno,name,gender,dept,email,pwd,contact " +
                		"FROM student WHERE rollno=?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, inputRoll);
                
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    txtOutputArea.setText("====== INDIVIDUAL STUDENT PROFILE ======\n\n" +
                            "Roll Number : " + rs.getInt("rollno") + "\n" +
                            "Full Name   : " + rs.getString("name") + "\n" +
                            "Department  : " + rs.getString("dept") + "\n" +
                            "Email ID    : " + rs.getString("email") + "\n" +
                            "Contact No  : " + rs.getString("contact") + "\n"+
                            "Gender      : " + rs.getString("gender") + "\n"+
                            "Password    : " + rs.getString("pwd") + "\n");
                } else {
                    txtOutputArea.setText("No student profile found with Roll Number: " + inputRoll);
                }

                rs.close(); pst.close(); con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Data Query Error: " + ex.getMessage());
            }
        }

        // --- BUTTON ACTION 3: Get Marks (For a single student's exam sheet) ---
        else if (e.getSource() == btnGetMarks) {
            String inputRoll = txtRollNoInput.getText().trim();
            if (inputRoll.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please type a Roll Number first!");
                return;
            }

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                
                // Querying subject columns based on matching rno
                String query =
                	    "SELECT rollno,name,mark,dept " +
                	    "FROM marks WHERE rollno=?";

                	PreparedStatement pst = con.prepareStatement(query);
                	pst.setString(1, inputRoll);

                	ResultSet rs = pst.executeQuery();

                	StringBuilder output = new StringBuilder();

                	while(rs.next()) {
                	    output.append("Roll No : ")
                	          .append(rs.getString("rollno"))
                	          .append("\n");

                	    output.append("Name : ")
                	          .append(rs.getString("name"))
                	          .append("\n");

                	    output.append("Department : ")
                	          .append(rs.getString("dept"))
                	          .append("\n");

                	    output.append("Marks : ")
                	          .append(rs.getInt("mark"))
                	          .append(" / 10\n");
                	           

                	    output.append("---------------------------------\n");
                	}

                	if(output.length() > 0) {
                	    txtOutputArea.setText(output.toString());
                	} else {
                	    txtOutputArea.setText("No marks found for Roll Number: " + inputRoll);
                	}
                rs.close(); pst.close(); con.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Marks Query Error: " + ex.getMessage());
            }
        }
    }

    // Testing main runner method to test this specific window instantly
    public static void main(String[] args) {
        new Admin();
    }
}
