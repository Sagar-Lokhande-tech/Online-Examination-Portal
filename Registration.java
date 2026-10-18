package examportal;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Registration {
	
	Registration(){
		
		JFrame frame = new JFrame("Registration Form");
        frame.setSize(400, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); 

       
        JLabel titleLabel = new JLabel("Registration Form");
        titleLabel.setBounds(150, 20, 200, 30);

        JLabel rollLabel = new JLabel("Enter Roll No:");
        rollLabel.setBounds(50, 80, 100, 30);
        JTextField rollField = new JTextField();
        rollField.setBounds(150, 80, 150, 30);

        JLabel nameLabel = new JLabel("Enter Name:");
        nameLabel.setBounds(50, 120, 100, 30);
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 120, 150, 30);

        JLabel genderLabel = new JLabel("Select Gender:");
        genderLabel.setBounds(50, 160, 100, 30);
        JRadioButton maleRadio = new JRadioButton("Male");
        maleRadio.setBounds(150, 160, 60, 30);
        JRadioButton femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(220, 160, 80, 30);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        JLabel deptLabel = new JLabel("Select Dept:");
        deptLabel.setBounds(50, 200, 100, 30);
        JComboBox<String> deptDropdown = new JComboBox<>(new String[]{"CSE", "ECE", "ME", "IT", "EEE"});
        deptDropdown.setBounds(150, 200, 150, 30);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 240, 100, 30);
        JTextField emailField = new JTextField();
        emailField.setBounds(150, 240, 150, 30);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 280, 100, 30);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 280, 150, 30);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(50, 320, 100, 30);
        JTextField contactField = new JTextField();
        contactField.setBounds(150, 320, 150, 30);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 360, 100, 30);

        
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	
                String roll = rollField.getText();
                
                
                String name = nameField.getText();
                String gender = maleRadio.isSelected() ? "Male" : "Female";
                String department = (String) deptDropdown.getSelectedItem();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String contact = contactField.getText();

               
                /*JOptionPane.showMessageDialog(frame,
                    "Registration Successful!\n"
                    + "Roll No: " + roll + "\n"
                    + "Name: " + name + "\n"
                    + "Gender: " + gender + "\n"
                    + "Department: " + department + "\n"
                    + "Email: " + email + "\n"
                    + "Contact: " + contact);
                */
                
               
                
                try {
                	Class.forName("com.mysql.cj.jdbc.Driver");

                	Connection con = DriverManager.getConnection(
                	        "jdbc:mysql://localhost:3306/examportal",
                	        "root",
                	        "SL061@sal");

                	Statement stmt = con.createStatement();

                	String createTable =
                	    "CREATE TABLE IF NOT EXISTS student (" +
                	    "rollno VARCHAR(20) PRIMARY KEY," +
                	    "name VARCHAR(50) NOT NULL," +
                	    "gender VARCHAR(10) NOT NULL," +
                	    "dept VARCHAR(20) NOT NULL," +
                	    "email VARCHAR(100) NOT NULL," +
                	    "contact VARCHAR(15) NOT NULL," +
                	    "pwd VARCHAR(50) NOT NULL)";

                	stmt.executeUpdate(createTable);

                	String insert =
                	    "INSERT INTO student(rollno,name,gender,dept,email,contact,pwd) VALUES(" +
                	    "'" + roll + "'," +
                	    "'" + name + "'," +
                	    "'" + gender + "'," +
                	    "'" + department + "'," +
                	    "'" + email + "'," +
                	    "'" + contact + "'," +
                	    "'" + password + "')";

                	stmt.executeUpdate(insert);

                	JOptionPane.showMessageDialog(frame,
                	        "Registration Successful!");

                	con.close();
                    JOptionPane.showMessageDialog(frame, "Registarion Successful! proceed to login");  
                    
                    new Login("examportal", roll, name, department);
                    
                } catch (Exception e1) {
                    System.out.println(e1);                }

                
                //create table query
                
                
                // insert record
            }
        });

       
        frame.add(titleLabel);
        frame.add(rollLabel);
        frame.add(rollField);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(genderLabel);
        frame.add(maleRadio);
        frame.add(femaleRadio);
        frame.add(deptLabel);
        frame.add(deptDropdown);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(contactLabel);
        frame.add(contactField);
        frame.add(registerButton);

        frame.setVisible(true);
 
	}
	
    public static void main(String[] args) {
 
    	new Registration();
    	
           }
}