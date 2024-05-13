package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
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
import game.AIPlayer;
import game.Game;
import game.Player;
import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class GameSession_page extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ArrayList<Card> drawCards;
	private ArrayList<Card> discardCards;
	private ArrayList<AIPlayer> players;
	private Player user;
	private String currentColor;
	private String currentSign;
	private JScrollPane scrollPane_1;
	private JPanel panel_1;
	private JScrollPane scrollPane_2;
	private boolean isUserTurn = true;
	private boolean direction = false;
	private Game game;

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
		
		game = new Game();
		drawCards = game.generateCards();
		game.shuffleCards(drawCards);
		
		players = new ArrayList<>();
		
		for (int i = 0; i < numPlayer - 1; i++) {
            ArrayList<Card> playerCards = game.dealCards(7, drawCards);
            String name = "Player " + String.valueOf(i + 1);
            players.add(new AIPlayer(name, playerCards));
        }
		user = new Player("You", game.dealCards(7, drawCards));
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
        panel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(isUserTurn) {
        			Game.drawCard(user, drawCards, discardCards);
        			displayPlayerCards(user.getPlayerCards(), scrollPane_2);
        		}
        	}
        });

        
        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(752, 309, 123, 181);
        getContentPane().add(scrollPane_1, BorderLayout.CENTER);
        
        panel_1 = panelMaker(discardCards.get(discardCards.size() - 1));
        currentColor = discardCards.get(discardCards.size() - 1).getColor();
        currentSign = discardCards.get(discardCards.size() - 1).getSign();

        scrollPane_1.setViewportView(panel_1);
        
        scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(170, 689, 1134, 191);
        contentPane.add(scrollPane_2);
        displayPlayerCards(user.getPlayerCards(), scrollPane_2);
		addPlayers(players);
		
	}

	
	private void displayPlayerCards(ArrayList<Card> playerCards, JScrollPane scrollPane) {
		GridLayout gridLayout = new GridLayout(1, 3, 10, 10);
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new FlowLayout());
	    for (Card card : playerCards) {
	        JPanel cardPanel = panelMaker(card);
	        cardPanel.setLayout(gridLayout);
	        cardPanel.setPreferredSize(new Dimension(120, 175));
	        cardPanel.setBounds(EXIT_ON_CLOSE, ABORT, 120, 175);
	        cardPanel.addMouseListener(new MouseAdapter() {
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		Component clickedComponent = e.getComponent();
	        		if (clickedComponent instanceof CardPanel && isUserTurn) {
	        			CardPanel clickedPanel = (CardPanel) clickedComponent;
	        			Card clickedCard = clickedPanel.getCard();
	        			if (clickedCard != null && Game.isValidMove(clickedCard, currentSign, currentColor)) {
	        				currentSign = clickedCard.getSign();
	        			    currentColor = clickedCard.getColor();
	        			    panel_1.removeAll();
	        			    panel_1 = panelMaker(clickedCard);
	        			    scrollPane_1.revalidate();
	        			    scrollPane_1.repaint();
	        			    scrollPane_1.setViewportView(panel_1);
	        		        user.getPlayerCards().remove(clickedCard);
	        		        discardCards.add(clickedCard);
	        		        isUserTurn = false;
	        		        if(clickedCard instanceof WildCard) {
	        		        	String[] options = {"Red", "Yellow", "Blue", "Green"};
	        			        int choice = JOptionPane.showOptionDialog(e.getComponent(), "Choose a color", "Colors",
	        			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	        			        if (choice != JOptionPane.CLOSED_OPTION) {
	        			        	currentColor = options[choice];
	        			        }
	        		        }
	        		        
	        		        displayPlayerCards(user.getPlayerCards(), scrollPane_2);
	        		        if (currentSign == ActionCard.actionTypes[2]) {
	        		        	startTour(true);
	        		        }
	        		        else {
	        		        	startTour(false);
	        		        }
	        			}
	        		}  
	            }
	        });
	        playerPanel.add(cardPanel);
	    }
	    scrollPane.setViewportView(playerPanel);

	}
	private JPanel panelMaker(Card card) {
	    
	    ImageIcon image = card.getImage();
	    CardPanel cardPanel = new CardPanel(card) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
	        }
	    };
	    cardPanel.setLayout(new BorderLayout());
	    
	    if(card instanceof NumberCard) {
	        int number = ((NumberCard)card).getNumber();
	        JLabel label = new JLabel(String.valueOf(number));
	        label.setBounds(0,0,30,30);
	        label.setHorizontalAlignment(JLabel.CENTER); 
	        label.setVerticalAlignment(JLabel.CENTER);
	        label.setFont(new Font("Tahoma", Font.BOLD, 50));
	        cardPanel.add(label, BorderLayout.CENTER);
	    }
	    else if(card instanceof ActionCard) {
	        ImageIcon actionImage = ((ActionCard)card).getActionImage();
	        JLabel label = new JLabel(actionImage);
	        label.setBounds(0,0,30,30);
	        label.setHorizontalAlignment(JLabel.CENTER); 
	        label.setVerticalAlignment(JLabel.CENTER);
	        cardPanel.add(label, BorderLayout.CENTER);
	    }

	    return cardPanel;
	}
	private void startTour(boolean skip) {
		if (!direction && !skip) {
			startTourHelper(0);
		}
		else if(direction && !skip){
			startTourHelper(players.size() - 1);
		}
		else if(!direction && skip) {
			startTourHelper(1);
		}
		else {
			startTourHelper(players.size() - 2);
		}
		isUserTurn = true;
	}
	private void startTourHelper(int current) {
		int version = 0;
		int last = 0;
		if (!direction) {
			for(int i = current; i < players.size(); i++) {
				
				Card choosenCard = players.get(i).choosedCard(currentSign, currentColor, drawCards, discardCards);
				currentColor = choosenCard.getColor();
				currentSign = choosenCard.getSign();
				panel_1.removeAll();
				panel_1 = panelMaker(choosenCard);
				scrollPane_1.revalidate();
				scrollPane_1.repaint();
			    scrollPane_1.setViewportView(panel_1);
				discardCards.add(choosenCard);
				try {
				    players.get(i).getPlayerCards().remove(choosenCard);
				} catch (Exception e) {
				    System.err.println("Kart kaldırılırken bir hata oluştu: " + e.getMessage());
				    e.printStackTrace();
				}
				addPlayers(players);
				if(choosenCard instanceof ActionCard) {
					if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[1]) {
						direction = true;
						last = i - 1;
						version = 1;
						break;
					}
					else if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[2]) {
						if(i == players.size() - 1) {
							last = 0;
							version = 1;
							break;
						}
						last = i + 2;
						version = 1;
						break;
					}
					else if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[0]) {
						if(i == players.size() - 1) {
							
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							displayPlayerCards(user.getPlayerCards(), scrollPane_2);
							break;
						}
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
					}
				}
				else if(choosenCard instanceof WildCard) {
					currentColor = players.get(i).makeChoose();
					JOptionPane.showMessageDialog(this, currentColor + " is new color", "Warning", JOptionPane.WARNING_MESSAGE);
					if(((WildCard) choosenCard).getType() == WildCard.types[1]) {
						if(i == players.size() - 1) {
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							displayPlayerCards(user.getPlayerCards(), scrollPane_2);
							break;
						}
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
					}
				}
			}
		}
		else {
			for(int i = current; i >=0; i--) {
				
				Card choosenCard = players.get(i).choosedCard(currentSign, currentColor, drawCards, discardCards);
				currentColor = choosenCard.getColor();
				currentSign = choosenCard.getSign();
				panel_1.removeAll();
				panel_1 = panelMaker(choosenCard);
				scrollPane_1.revalidate();
				scrollPane_1.repaint();
			    scrollPane_1.setViewportView(panel_1);
				discardCards.add(choosenCard);
				try {
				    players.get(i).getPlayerCards().remove(choosenCard);
				} catch (Exception e) {
				    System.err.println("Kart kaldırılırken bir hata oluştu: " + e.getMessage());
				    e.printStackTrace();
				}
				addPlayers(players);
				if(choosenCard instanceof ActionCard) {
					if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[1]) {
						direction = false;
						last = i + 1;
						version = 1;
						break;
					}
					else if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[2]) {
						if(i == 0) {
							last = players.size() - 1;
							version = 1;
							break;
						}
						version = 1;
						last = i - 2;
					}
					else if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[0]) {
						if(i == 0) {
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							break;
						}
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
					}
				}
				else if(choosenCard instanceof WildCard) {
					currentColor = players.get(i).makeChoose();
					if(((WildCard) choosenCard).getType() == WildCard.types[1]) {
						if(i == players.size() - 1) {
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							break;
						}
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
					}
					currentColor = players.get(i).makeChoose();
					currentSign = null;
				}
			}
		}
		if(version == 1) {
			startTourHelper(last);
		}
		
	}
	private void addPlayers(ArrayList<AIPlayer> players) {
	    JPanel playerPanel = new JPanel(new GridLayout(3, 3)); 

	    for (int i = 1; i <= players.size(); i++) {
	        ImageIcon playerPhoto = new ImageIcon("img/UNO-Cards-Back.jpg"); 
	        JLabel playerLabel = new JLabel(playerPhoto); 
	        playerPanel.add(playerLabel); 

	        JLabel cardCountLabel = new JLabel("Number of Cards: " + players.get(i-1).getPlayerCards().size()); 
	        playerPanel.add(cardCountLabel);
	    }

	    
	    playerPanel.setBounds(400, 200, 600, 300);
	    contentPane.add(playerPanel); 
	}
	 
}

	
