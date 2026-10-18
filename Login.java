package examportal;

import javax.swing.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;  

public class Login {
	
	
	
	Login(String db ,String sroll,String sname,String sdept){
		
		JFrame frame = new JFrame("Login Page");  
        frame.setSize(400, 300);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setLayout(null); 

        
        JLabel titleLabel = new JLabel("Login Page");
        titleLabel.setBounds(160, 20, 100, 30);

        JLabel emailLabel = new JLabel("Enter Email:");  
        emailLabel.setBounds(50, 80, 100, 30);  
        JTextField emailField = new JTextField();  
        emailField.setBounds(150, 80, 180, 30);  

     
        JLabel passwordLabel = new JLabel("Enter Password:");  
        passwordLabel.setBounds(50, 120, 100, 30);  
        JPasswordField passwordField = new JPasswordField();  
        passwordField.setBounds(150, 120, 180, 30);  

        JButton loginButton = new JButton("Login");  
        loginButton.setBounds(150, 160, 100, 30);  

        JLabel registerLabel = new JLabel("Register first (if not registered)");  
        registerLabel.setBounds(110, 200, 200, 30);  

     
        loginButton.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) { 
            	
            	boolean res = false;
                String email = emailField.getText();  
                String password = new String(passwordField.getPassword());  

                //System.out.println("user email:"+email);
                //System.out.println("user pwd:"+password);
                
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                    	    "jdbc:mysql://localhost:3306/examportal",
                    	    "root",
                    	    "SL061@sal"
                    	);
                    
                    Statement stmt = con.createStatement();
                   // String query2 = "USE `" + db + "`";
                  //  stmt.executeUpdate(query2);
                    
                    
                    ResultSet rs = stmt.executeQuery("select * from student");
                    
                    while(rs.next()) {
                    	
                    	String email1 = rs.getString(5);
                    	String password1 = rs.getString(7);
                    	if(email1.equals(email) && password.equals(password1)) {
                    		res=true;
                    	}
                    	//System.out.println(""+email1);
                    	//System.out.println(password1);
                    	
                    }

                }
                catch(Exception e1) {
                	System.out.println(e1);
                }

                if (res==true) {  
     
                		new SelectPaper(sroll,sname,sdept);
                		//new CExam(sroll,sname,sdept);
                } 
            }  
        });  

       
        frame.add(titleLabel);
        frame.add(emailLabel);  
        frame.add(emailField);  
        frame.add(passwordLabel);  
        frame.add(passwordField);  
        frame.add(loginButton);  
        frame.add(registerLabel);  
frame.setVisible(true);  }
   /* public static void main(String[] args) {  
        
    		new Login(db);
    }*/  
}
