package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import user.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Register_page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register_page frame = new Register_page();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Register_page() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 731);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Choose an username");
		lblNewLabel.setBounds(46, 251, 158, 13);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(240, 240, 240, 130));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Choose a pasword");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(240, 240, 240, 130));
		lblNewLabel_1.setBounds(46, 306, 140, 13);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(219, 301, 286, 27);
		contentPane.add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(219, 243, 286, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Show");
		tglbtnNewToggleButton.setBounds(542, 303, 74, 21);
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (passwordField.getEchoChar() == '\u2022') { 
		            passwordField.setEchoChar((char) 0); 
		            tglbtnNewToggleButton.setText("Hide"); 
		        } 
		        else { 
		            passwordField.setEchoChar('\u2022'); 
		            tglbtnNewToggleButton.setText("Show"); 
		        }
		    }
		});
		contentPane.add(tglbtnNewToggleButton);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = textField.getText();
				if (!isValidUsername(username)) {
                    JOptionPane.showMessageDialog(Register_page.this, "Your username must contain only letters and numbers.");
                    return;
                }
				
				String password = new String(passwordField.getPassword());
                if (!isValidPassword(password)) {
                    JOptionPane.showMessageDialog(Register_page.this, "Your password must be at least 8 characters and contain at least one letter, one number and one special character.");
                    return;
                }
                
                if(isValidUsername(username) && isValidPassword(password) && isUniqueUser(username)) {
                	
                	try {
                		BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/muham/git/Comp-Project/comp132_project/src/txts/users.txt", true));
                		writer.write(username + "," + password + "," + "0"+ "," + "0"+ "," + "0"+ "," + "0" + "\n");
						writer.close();
						JOptionPane.showMessageDialog(Register_page.this, "Registration successful! User added.");
                	}
                	catch (IOException a) {
						a.printStackTrace();
					}
                }
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setBounds(301, 368, 126, 27);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Go Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Login_page login_page = new Login_page();
				login_page.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(301, 420, 126, 27);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(0, 0, 720, 693);
		lblNewLabel_2.setBackground(new Color(255, 255, 255));
		lblNewLabel_2.setIcon(new ImageIcon(ImageResizer.resizeImage("img/unorsz.png", 720, 693)));

		contentPane.add(lblNewLabel_2);
		
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, lblNewLabel_1, textField, passwordField, btnNewButton, tglbtnNewToggleButton, lblNewLabel_2, btnNewButton_1}));
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, lblNewLabel_1, textField, passwordField, btnNewButton, btnNewButton_1, tglbtnNewToggleButton, contentPane, lblNewLabel_2}));
	}
	
	/**
     * Validates the format of the entered username.
     *
     * @param username The username to be validated.
     * @return True if the username is valid, false otherwise.
     */
    private boolean isValidUsername(String username) {
    	String usernameRegex = "^[a-zA-Z0-9]+$";
    	Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
    
    /**
     * Validates the format of the entered password.
     *
     * @param password The password to be validated.
     * @return True if the password is valid, false otherwise.
     */
    private boolean isValidPassword(String password) {
    	String passwordRegex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=.,:;~¨€!'?<>|]).{8,}$";
    	Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    
    /**
     * Checks if the entered username is unique in the user database.
     *
     * @param username The entered username.
     * @return True if the username is unique, false otherwise.
     */
    private boolean isUniqueUser(String username) {
    	File file = new File("/Users/muham/git/Comp-Project/comp132_project/src/txts/users.txt");
    	
    	if (!file.exists()) {
            return true;
        }
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
    		String line;
            while ((line = reader.readLine()) != null) {
            	String[] lst = line.split(",");
            	if (lst[0].equals(username)) {
            		JOptionPane.showMessageDialog(Register_page.this, "This user already exists. Please change your username");
            		return false;
            	}
            }
            return true;
                
    	}
    	catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieves a list of all users from the user database.
     *
     * @return An ArrayList of User objects representing all users.
     */
    public static ArrayList<User> getUsers() {
        ArrayList<User> userList = new ArrayList<>();

        File file = new File("/Users/muham/git/Comp-Project/comp132_project/src/txts/users.txt");

        if (!file.exists()) {
            return userList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lst = line.split(",");
                if (lst.length == 6) {
                    String username = lst[0];
                    String password = lst[1];
                    String games = lst[2];
                    String wins = lst[3];
                    String loses = lst[4];
                    String totalScore = lst[5];
                    
                    int numGames = Integer.parseInt(games);
                    int numWins = Integer.parseInt(wins);
                    int numLoses = Integer.parseInt(loses);
                    int numTotalScore = Integer.parseInt(totalScore);

                    User user = new User(username, password);
                    user.setGames(numGames);
                    user.setWins(numWins);
                    user.setLoses(numLoses);
                    user.setTotalScore(numTotalScore);
                    userList.add(user);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return userList;
    }

}
