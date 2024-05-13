package gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.JScrollBar;
import java.awt.Color;
import java.awt.Window.Type;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Dialog.ModalExclusionType;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Canvas;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;

public class Login_page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_page frame = new Login_page();
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
	public Login_page() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 731);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setForeground(new Color(0, 0, 0));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(219, 243, 286, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		Color backgroundColor = new Color(240, 240, 240, 130);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(110, 244, 76, 21);
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(backgroundColor);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(219, 301, 286, 27);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(110, 302, 76, 21);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(backgroundColor);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText();
				char[] passwordChars = passwordField.getPassword();
		        String password = new String(passwordChars);
		        
		        if (validateUser(username, password)) {
		        	MainMenu_page mainMenu_page = new MainMenu_page(username);
		        	mainMenu_page.setVisible(true);
		        	dispose();
		        }
			}
		});
		btnNewButton.setBounds(301, 368, 126, 27);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(btnNewButton);
		
		JToggleButton tglbtnNewToggleButton = new JToggleButton("Show");
		tglbtnNewToggleButton.setBounds(542, 303, 74, 21);
		tglbtnNewToggleButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
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
		
		JButton btnNewButton_1 = new JButton("Register");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Register_page registerPage = new Register_page();
                registerPage.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(301, 436, 126, 27);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("Don't have an account?");
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(backgroundColor);
		lblNewLabel_2.setBounds(301, 405, 126, 21);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(0, 0, 720, 693);
		lblNewLabel_3.setBackground(new Color(255, 255, 255));
		lblNewLabel_3.setIcon(new ImageIcon(ImageResizer.resizeImage("img/unorsz.png", 720, 693)));
		
		contentPane.add(lblNewLabel_3);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, lblNewLabel_1, lblNewLabel_2, textField, passwordField, btnNewButton, btnNewButton_1, tglbtnNewToggleButton, lblNewLabel_3}));
	}
	
	/**
     * Validates the user by checking the entered username and password against stored credentials.
     *
     * @param username The entered username for validation.
     * @param password The entered password for validation.
     * @return True if the user is valid, false otherwise.
     */
	private boolean validateUser(String username, String password) {
        File file = new File("/Users/muham/git/Comp-Project/comp132_project/src/txts/users.txt");

        if (!file.exists()) {
        	JOptionPane.showMessageDialog(this, "There is no account in database please click sign up!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lst = line.split(",");
                if (lst[0].equals(username) && lst[1].equals(password)) {
                    return true;
                }
            }
            JOptionPane.showMessageDialog(this, "Wrong username or password. Please try again!", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
