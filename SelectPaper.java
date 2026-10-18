package examportal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SelectPaper {
	
	SelectPaper(String sroll,String sname,String sdept){
		
		JFrame frame = new JFrame("EXAM PAPER");
        frame.setSize(400, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); 

       
        JLabel deptLabel = new JLabel("Select papaer:");
        deptLabel.setBounds(50, 200, 100, 30);
        JComboBox<String> deptDropdown = new JComboBox<>(new String[]{"CExam","CppExam","JavaExamForm"});
        deptDropdown.setBounds(150, 200, 150, 30);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(150, 360, 100, 30);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String department = (String) deptDropdown.getSelectedItem();
                
                if(department.equals("CExam")) {
                	   
                	new CExam(sroll,sname,sdept);
                	
                }
                
               if(department.equals("CppExam")) {
                	
                		new CppExam(sroll,sname,sdept);
                	
                }

                if(department.equals("JavaExamForm")) {
	
                		new JavaExamform(sroll,sname,sdept);
	
       }
            	
            	
            }});
        
        
        frame.add(deptLabel);
        frame.add(deptDropdown);
        frame.add(registerButton);

        frame.setVisible(true);
 

	}

	
}