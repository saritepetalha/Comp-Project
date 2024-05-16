package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTable;
import javax.swing.JSlider;
import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import user.User;

import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextField;

public class MainMenu_page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	public static User thisUser;
	private JTextField textField;
	

	/**
	 * Create the frame.
	 */
	public MainMenu_page(String username) {
		
		thisUser = 	findUser(username);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1320, 817);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Leaderboard");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(306, 76, 124, 13);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(885, 440
				, 157, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JSpinner spinner = new JSpinner();
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 15));
		spinner.setModel(new SpinnerNumberModel(2, 2, 10, 1));
		spinner.setBounds(1012, 500, 30, 22);
		contentPane.add(spinner);
		
		JButton btnNewButton = new JButton("New Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String sessionName = textField.getText();
				if (sessionName.isEmpty()) {
		            JOptionPane.showMessageDialog(contentPane, "Please enter a Session Name!", "Warning", JOptionPane.WARNING_MESSAGE);
		        }
				else {
					setVisible(false);
					int numPlayer = (int) spinner.getValue();
					GameSession_page gameSession_page = new GameSession_page(sessionName, numPlayer);
					gameSession_page.setVisible(true);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(885, 560, 157, 21);
		contentPane.add(btnNewButton);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				SaveGames saveGames = new SaveGames(username);
				saveGames.setVisible(true);
			}
		});
		btnLoadGame.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLoadGame.setBounds(885, 620, 157, 21);
		contentPane.add(btnLoadGame);
		
		JButton btnNewButton_1 = new JButton("Sign Out");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Login_page loginPage = new Login_page();
				loginPage.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.setBounds(885, 680, 157, 21);
		contentPane.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(28, 114, 600, 588);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setBorder(null);
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"Placement", "Username", "Total Score"}
		) {
			Class[] columnTypes = new Class[] {Object.class, String.class, Integer.class};
			boolean[] columnEditable = new boolean[] { false, false, false };
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(1).setPreferredWidth(286);
		table.getColumnModel().getColumn(2).setPreferredWidth(36);
		
		ArrayList<User> users = Register_page.getUsers();
		Collections.sort(users, Comparator.comparingInt(User::getTotalScore).reversed());
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		int placement = 1;
		for (User user : users) {
		    model.addRow(new Object[]{placement++, user.getUsername(), user.getTotalScore()});
		}
		
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Placement", "Username", "Total Score" }) {
			public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		scrollPane.setViewportView(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		        if (!event.getValueIsAdjusting()) {
		            int selectedRow = table.getSelectedRow();
		            if (selectedRow != -1) {
		                User selectedUser = users.get(selectedRow);
		                UserInfo_page userInfo_page = new UserInfo_page(selectedUser);
		                userInfo_page.setVisible(true);
		                dispose();
		            }
		        }
		    }
		});
		
		JLabel lblNewLabel_1 = new JLabel("Welcome to UNO " + thisUser.getUsername() + "!");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_1.setBounds(819, 143, 290, 21);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Game Statictics:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_2.setBounds(740, 240, 124, 21);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\tGames:" + thisUser.getGames());
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_3.setBounds(740, 270, 124, 21);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\tWins:" + thisUser.getWins());
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_4.setBounds(740, 305, 124, 21);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\tLoses:" + thisUser.getLoses());
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_5.setBounds(740, 340, 124, 21);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("\tTotal Score:" + thisUser.getTotalScore());
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_6.setBounds(740, 375, 124, 21);
		contentPane.add(lblNewLabel_6);
		
		JTextPane txtpnNumberOfPlayer = new JTextPane();
		txtpnNumberOfPlayer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnNumberOfPlayer.setText("Number of Player");
		txtpnNumberOfPlayer.setBounds(885, 500, 129, 21);
		contentPane.add(txtpnNumberOfPlayer);
		
		JLabel lblNewLabel_7 = new JLabel("Session Name:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_7.setBounds(778, 440, 107, 21);
		contentPane.add(lblNewLabel_7);

	}
	/**
	 * Finds a user with the specified username from the list of registered users.
	 *
	 * @param username The username of the user to find.
	 * @return         The User object if found, or null if not found.
	 */
	private User findUser(String username) {
    	Register_page register_page = new Register_page();
        ArrayList<User> users = register_page.getUsers();
        
        for (User u : users) {
        	if ((u.getUsername()).equals(username)) return u;
        }
        return null;
    }
}
