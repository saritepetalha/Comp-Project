package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import card.*;
import game.Player;

public class GameSession_page extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<Card> drawCards;
	private ArrayList<Card> discardCards;
	private ArrayList<Player> Players;
	private ArrayList<Card> userCards;

	/**
	 * Create the frame.
	 */
	public GameSession_page(String seesionName, int numPlayer) {
		getContentPane().setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1441, 952);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		drawCards = generateCards();
		shuffleCards(drawCards);
		
		Players = new ArrayList<>();
		
		for (int i = 0; i < numPlayer - 1; i++) {
            ArrayList<Card> playerCards = dealCards(7);
            String name = "Player " + String.valueOf(i + 1);
            Players.add(new Player(name, playerCards));
        }
		userCards = dealCards(7);
		displayPlayerCards(userCards);
		discardCards = new ArrayList<>();
		discardCards.add(drawCards.remove(0));
		

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(123, 181);
        scrollPane.setLocation(521, 309);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon(ImageResizer.resizeImage("img/backside.png", 120, 175)).getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        scrollPane.setViewportView(panel);

        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(752, 309, 123, 181);
        getContentPane().add(scrollPane_1, BorderLayout.CENTER);
        
        JPanel panel_1 = panelMaker(discardCards.get(discardCards.size() - 1));
        scrollPane_1.setViewportView(panel_1);
        
		
		
	}
	
	public ArrayList<Card> generateCards() {
	
		ArrayList<Card> cards = new ArrayList<>();
		
		
		for (int j = 0; j < 4; j++) {
			String color = card.NormalCard.colors[j];
			cards.add(new NumberCard(color, 0));
			for (int i = 1; i < 10; i++) {
		        Card card = new NumberCard(color, i);
		        cards.add(card);
		        cards.add(card);
			}
			for (int i = 0; i < 3; i++) {
				String actionType = card.ActionCard.colors[i];
				Card card = new ActionCard(color, actionType);
				cards.add(card);
		        cards.add(card);
			}
		}
		for(int i = 0; i < 4; i++) {
			cards.add(new WildCard("Normal"));
			cards.add(new WildCard("Draw"));
		}
		return cards;
		
	}
	public void shuffleCards(ArrayList<Card> cards){
		Collections.shuffle(cards);
	}
	
	private ArrayList<Card> dealCards(int numCards) {
        ArrayList<Card> playerCards = new ArrayList<>();
        for (int j = 0; j < numCards; j++) {
            if (!drawCards.isEmpty()) {
                Card card = drawCards.remove(0);
                playerCards.add(card);
            }
        }
        return playerCards;
    }
	
	private void displayPlayerCards(ArrayList<Card> playerCards) {
        JPanel playerPanel = new JPanel();
        for (Card card : playerCards) {
            JLabel cardLabel = new JLabel(card.toString());
            playerPanel.add(cardLabel);
        }
        getContentPane().add(playerPanel);
    }
	private JPanel panelMaker(Card card) {
		
		ImageIcon image = card.getImage();
		JPanel largePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        largePanel.setLayout(new BorderLayout());
		
		if(card instanceof NumberCard) {
			int number = ((NumberCard)card).getNumber();
			JLabel label = new JLabel(String.valueOf(number));
			label.setBounds(0,0,30,30);
			label.setHorizontalAlignment(JLabel.CENTER); 
	        label.setVerticalAlignment(JLabel.CENTER);
	        label.setFont(new Font("Tahoma", Font.BOLD, 50));
			largePanel.add(label, BorderLayout.CENTER);
		}
		else if(card instanceof ActionCard) {
			ImageIcon actionImage = ((ActionCard)card).getActionImage();
			JPanel smallPanel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                g.drawImage(actionImage.getImage(), 0, 0, getWidth(), getHeight(), this);
	            }
	        };
	        largePanel.add(smallPanel, BorderLayout.CENTER);
		}
        
        return largePanel;
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
        		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
	
	
