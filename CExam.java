package examportal;

//import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.*;

class CExam extends JFrame implements ActionListener {
	

		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		JLabel l;
	    JRadioButton jb[] = new JRadioButton[5];
	    JButton b1, b2;
	    ButtonGroup bg;
	    int count = 0, current = 0, x = 1, y = 1, now = 0;
	    int m[] = new int[10];
	    String[] userAnswers = new String[10]; // Store user answers
	    String[] correctAnswers = {
	        ".c", "bool", "foreach loop", "malloc", "_myVar", "break", "int *ptr", "a", "C is a procedural language", "int arr[5]"
	    };
	    
	    String sroll,sname,sdept,db;
	    
	    
	  CExam(String sroll,String sname,String sdept) {
	  
		  
		  this.sroll=sroll;
		  this.sname=sname;
		  this.sdept=sdept;
	  //        super(s);
	        l = new JLabel();
	        add(l);
	        bg = new ButtonGroup();
	        for (int i = 0; i < 5; i++) {
	            jb[i] = new JRadioButton();
	            add(jb[i]);
	            bg.add(jb[i]);
	        }
	        b1 = new JButton("Next");
	        b2 = new JButton("Bookmark");
	        b1.addActionListener(this);
	        b2.addActionListener(this);
	        add(b1);
	        add(b2);
	        set();
	        l.setBounds(30, 40, 450, 20);
	        jb[0].setBounds(50, 80, 100, 20);
	        jb[1].setBounds(50, 110, 100, 20);
	        jb[2].setBounds(50, 140, 100, 20);
	        jb[3].setBounds(50, 170, 100, 20);
	        b1.setBounds(100, 240, 100, 30);
	        b2.setBounds(270, 240, 100, 30);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(null);
	        setLocation(250, 100);
	        setVisible(true);
	        setSize(600, 350);
  }    

	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() == b1) {
	            if (check()) {
	                count = count + 1;
	            }
	            current++;
	            set();
	            if (current == 9) {
	                b1.setEnabled(false);
	                b2.setText("Result");
	            }
	        }

	        if (e.getActionCommand().equals("Bookmark")) {
	            JButton bk = new JButton("Bookmark" + x);
	            bk.setBounds(480, 20 + 30 * x, 100, 30);
	            add(bk);
	            bk.addActionListener(this);
	            m[x] = current;
	            x++;
	            current++;
	            set();
	            if (current == 9) {
	                b2.setText("Result");
	            }
	            setVisible(false);
	            setVisible(true);
	        }

	        for (int i = 0, y = 1; i < x; i++, y++) {
	            if (e.getActionCommand().equals("Bookmark" + y)) {
	                if (check()) {
	                    count = count + 1;
	                }
	                now = current;
	                current = m[y];
	                set();
	                ((JButton) e.getSource()).setEnabled(false);
	                current = now;
	            }
	        }

	        if (e.getActionCommand().equals("Result")) {
	            if (check()) {
	                count = count + 1;
	            }
	            current++;

	            // Display all answers and the result
	            StringBuilder resultMessage = new StringBuilder("Your results:\n\n");

	            for (int i = 0; i < 10; i++) {
	                resultMessage.append("Question ").append(i + 1).append(": ")
	                        .append("Your answer: ").append(userAnswers[i])
	                        .append("     | Correct answer: ").append(correctAnswers[i])
	                        .append("\n");
	            }

	            resultMessage.append("\nCorrect answers: ").append(count);
	            JOptionPane.showMessageDialog(this, resultMessage.toString());
	           // System.exit(0);
	            
	            System.out.println("roll no:"+sroll);
	            System.out.println("name:"+sname);
	            System.out.println("department:"+sdept);
	            System.out.println("marks:"+count);
	            
	            try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                    	    "jdbc:mysql://localhost:3306/examportal",
                    	    "root",
                    	    "SL061@sal"
                    	);
                    Statement stmt = con.createStatement();

                    
                    //System.out.println("database created successfully");
                    
                    //String query2 = "USE `" + sroll + "`";
                    //stmt.executeUpdate(query2);
                
              
                    
                    System.out.println("database selected");
                    String createMarks =
                    		"CREATE TABLE IF NOT EXISTS marks(" +
                    		"rollno VARCHAR(20)," +
                    		"name VARCHAR(50)," +
                    		"mark INT," +
                    		"dept VARCHAR(20))";

                    		stmt.executeUpdate(createMarks);

                    String query =
                    		"INSERT INTO marks(rollno,name,mark,dept) VALUES('"
                    		+sroll+"','"
                    		+sname+"','"
                    		+count+"','"
                    		+sdept+"')";                    stmt.executeUpdate(query);
                    System.out.println("record inserted");
                    
                    
                    
                    con.close();
                    
	            }
	            catch(Exception e1) {
	            	
	            	System.out.println(e1);
	            }
                    
                    
                    	
	           
	                   

	        }
	    }

	    void set() {
	        jb[4].setSelected(true);
	        if (current == 0) {
	            l.setText("Que1: Which of the following is correct extension of c program file?");
	            jb[0].setText(".cpp");
	            jb[1].setText(".c");
	            jb[2].setText(".java");
	            jb[3].setText(".char");
	        }
	        if (current == 1) {
	            l.setText("Que2: Which of the following is not a valid datatype in C?");
	            jb[0].setText("float");
	            jb[1].setText("double");
	            jb[2].setText("bool");
	            jb[3].setText("char");
	        }
	        if (current == 2) {
	            l.setText("Que3: Which of the following is NOT a valid loop in structure in C?");
	            jb[0].setText("for loop");
	            jb[1].setText("while loop");
	            jb[2].setText("do-while loop");
	            jb[3].setText("foreach loop");
	        }
	        if (current == 3) {
	            l.setText("Que4: Which function is used to allocate memory dynamically in c?");
	            jb[0].setText("malloc");
	            jb[1].setText("alloc");
	            jb[2].setText("new");
	            jb[3].setText("free");
	        }
	        if (current == 4) {
	            l.setText("Que5: Which of the following is a valid C identifier?");
	            jb[0].setText("123var");
	            jb[1].setText("_myVar");
	            jb[2].setText("int");
	            jb[3].setText("float");
	        }
	        if (current == 5) {
	            l.setText("Que6: Which statement correctly terminates a loop in C?");
	            jb[0].setText("break");
	            jb[1].setText("exit(0)");
	            jb[2].setText("return");
	            jb[3].setText("continue");
	        }
	        if (current == 6) {
	            l.setText("Que7: Which is the correct Syntax for declaring a pointer to an integer in C?");
	            jb[0].setText("int ptr");
	            jb[1].setText("int *ptr");
	            jb[2].setText("int &ptr");
	            jb[3].setText("pointer int ptr");
	        }
	        if (current == 7) {
	            l.setText("Que8: which of the following modes is used to append data to a file?");
	            jb[0].setText("r");
	            jb[1].setText("w");
	            jb[2].setText("a");
	            jb[3].setText("rw");
	        }
	        if (current == 8) {
	            l.setText("Que9: which of the following is true for C?");
	            jb[0].setText("C is an object-oriented  language");
	            jb[1].setText("C is a procedural language");
	            jb[2].setText("C is a markup language");
	            jb[3].setText("C is a scripting language");
	        }
	        if (current == 9) {
	            l.setText("Que10: How to correctly declare an array in C?");
	            jb[0].setText("int arr[5]");
	            jb[1].setText("int arr(5)");
	            jb[2].setText("int arr{5}");
	            jb[3].setText("array int[5]");
	        }

	        l.setBounds(30, 40, 450, 20);
	        for (int i = 0, j = 0; i <= 90; i += 30, j++)
	            jb[j].setBounds(50, 80 + i, 200, 20);
	    }

	    boolean check() {
	        String selectedAnswer = "";
	        if (jb[0].isSelected()) {
	            selectedAnswer = jb[0].getText();
	        } else if (jb[1].isSelected()) {
	            selectedAnswer = jb[1].getText();
	        } else if (jb[2].isSelected()) {
	            selectedAnswer = jb[2].getText();
	        } else if (jb[3].isSelected()) {
	            selectedAnswer = jb[3].getText();
	        }

	        // Store the user's answer
	        userAnswers[current] = selectedAnswer;

	        if (current == 0) return jb[1].isSelected();
	        if (current == 1) return jb[2].isSelected();
	        if (current == 2) return jb[3].isSelected();
	        if (current == 3) return jb[0].isSelected();
	        if (current == 4) return jb[1].isSelected();
	        if (current == 5) return jb[0].isSelected();
	        if (current == 6) return jb[1].isSelected();
	        if (current == 7) return jb[2].isSelected();
	        if (current == 8) return jb[1].isSelected();
	        if (current == 9) return jb[0].isSelected();
return false;	}}