package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import user.User;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserInfo_page extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User statUser;

	/**
	 * Create the frame.
	 */
	public UserInfo_page(User user) {
		
		statUser = user;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Game Statictics of " + user.getUsername());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(93, 10, 518, 52);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Games: " + user.getGames());
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(93, 75, 518, 52);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Wins: " + user.getWins());
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(93, 140, 518, 52);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Loses: " + user.getLoses());
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_3.setBounds(93, 205, 518, 52);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Total Score: : " + user.getTotalScore());
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_4.setBounds(93, 265, 518, 52);
		contentPane.add(lblNewLabel_4);
		
		int scp = 0;
		
		if(user.getGames() != 0) {
			scp = user.getTotalScore() / user.getGames();
		}
		
		JLabel lblNewLabel_5 = new JLabel("Average score per game: " + scp);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_5.setBounds(93, 330, 518, 52);
		contentPane.add(lblNewLabel_5);
		
		double wgr = 0;
		
		if(user.getGames() != 0) {
			wgr = user.getWins() / user.getGames() * 100;
		}
		
		JLabel lblNewLabel_6 = new JLabel("Win/Game Ratio: " + wgr + "%");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_6.setBounds(93, 395, 518, 52);
		contentPane.add(lblNewLabel_6);
		
		JButton btnNewButton = new JButton("Go Back");
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        MainMenu_page mainMenuPage = new MainMenu_page(MainMenu_page.thisUser.getUsername());
		        mainMenuPage.setVisible(true);
		        dispose();
		    }
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBounds(294, 457, 135, 21);
		contentPane.add(btnNewButton);

	}
}
