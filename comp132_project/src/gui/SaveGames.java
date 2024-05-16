package gui;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SaveGames extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JList<String> list;
    private JButton btnNewButton;

    /**
     * Create the frame.
     */
    public SaveGames(String username) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 847, 680);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        File userDirectory = new File("/Users/muham/git/Comp-Project/comp132_project/src/txts/" + username);
        File[] files = userDirectory.listFiles();
        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String fileName : fileNames) {
            listModel.addElement(fileName);
        }

        list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(10, 10, 800, 500);
        contentPane.add(scrollPane);
        
        btnNewButton = new JButton("Go Back");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		MainMenu_page mainMenuPage = new MainMenu_page(MainMenu_page.thisUser.getUsername());
		        mainMenuPage.setVisible(true);
		        dispose();
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton.setBounds(327, 550, 177, 51);
        contentPane.add(btnNewButton);

        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JList<?> list = (JList<?>) evt.getSource();
                if (evt.getClickCount() == 2) { 
                    int index = list.locationToIndex(evt.getPoint());
                    String selectedFileName = listModel.getElementAt(index);
                    startSavedGame(userDirectory + "/" + selectedFileName);
                }
            }
        });
    }
    /**
     * Starts a saved game session with the specified file name.
     *
     * @param fileName The name of the file containing the saved game data.
     */
    private void startSavedGame(String fileName) {
        GameSession_page gameSession_page = new GameSession_page(fileName);
        gameSession_page.setVisible(true);
        dispose(); 
    }    
}
