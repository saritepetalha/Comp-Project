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
import java.awt.ScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.Timer;

import card.*;
import game.AIPlayer;
import game.Game;
import game.Log;
import game.Player;
import user.User;

import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
	private boolean isUserTurn;;
	private boolean direction = false;
	private Game game;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	private Timer timer;
	private String sessionName;
	private Log events;

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public GameSession_page(String sessionName, int numPlayer) {
		
		getContentPane().setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1441, 952);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.sessionName = sessionName;
		events = new Log();
		events.writeEvent("Game Started.");
		
		game = new Game();
		drawCards = game.generateCards();
		game.shuffleCards(drawCards);
		game.shuffleCards(drawCards);
		
		players = new ArrayList<>();
		
		for (int i = 0; i < numPlayer - 1; i++) {
            ArrayList<Card> playerCards = game.dealCards(7, drawCards);
            String name = "Player " + String.valueOf(i + 1);
            players.add(new AIPlayer(name, playerCards));
        }
		user = new Player(MainMenu_page.thisUser.getUsername(), game.dealCards(7, drawCards));
		discardCards = new ArrayList<>();
		discardCards.add(drawCards.remove(0));
		while(discardCards.get(discardCards.size()-1) instanceof WildCard) {
			discardCards.add(drawCards.remove(0));
		}
		isUserTurn = true;
		

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
        panel.setBackground(new Color(255, 255, 255));
        scrollPane.setViewportView(panel);
        panel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(isUserTurn) {
        			Game.drawCard(user, drawCards, discardCards);
        			events.writeEvent(user.getName() + " draw a card");
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
        
        
        
        JButton btnNewButton = new JButton("UNO");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(user.checkStatus() == 1) {
		        	user.UNO(events);
		        	stopTimer();
		        }
        	}
        });
	    btnNewButton.setBounds(654, 357, 88, 41);
	    contentPane.add(btnNewButton);

	    JLabel lblNewLabel = new JLabel(sessionName);
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
	    lblNewLabel.setBounds(540, 30, 352, 30);
	    lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
	    contentPane.add(lblNewLabel);
	    
	    scrollPane_3 = new JScrollPane();
        scrollPane_3.setBounds(170, 100, 1134, 130);
        getContentPane().add(scrollPane_3, BorderLayout.CENTER);
        
        displayPlayerCards(user.getPlayerCards(), scrollPane_2);
		game.addPlayers(players, scrollPane_3);
		
		JButton btnPenalize = new JButton("Penalize");
		btnPenalize.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		stopTimer();
        	}
        });
		btnPenalize.setBounds(654, 418, 88, 41);
		contentPane.add(btnPenalize);
		
		JButton btnNewButton_1 = new JButton("Load Game");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isUserTurn) {
					saveGameToFile();
					game.saveLog(events, sessionName);
				}
			}
		});
		btnNewButton_1.setBounds(919, 367, 106, 21);
		contentPane.add(btnNewButton_1);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(301, 339, 195, 120);
		getContentPane().add(scrollPane_4, BorderLayout.CENTER);
		showInfo(scrollPane_4);
		
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Would you like to save the game before exiting?", "Exit Game", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    saveGameToFile();
                } else if (option == JOptionPane.NO_OPTION) {
                }
                MainMenu_page mainMenuPage = new MainMenu_page(MainMenu_page.thisUser.getUsername());
                mainMenuPage.setVisible(true);
                dispose();
			}
		});
		btnNewButton_2.setBounds(919, 416, 106, 21);
		contentPane.add(btnNewButton_2);
		
		
	}
	
	public GameSession_page(String file) {
		
		getContentPane().setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1441, 952);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		game = new Game();
		loadGame(file);
		String logFile = Game.convertPath(file, "/Users/muham/git/Comp-Project/comp132_project/src",  "/Users/muham/git/Comp-Project/comp132_project/src/txts/logs");
		events.setEvents(game.loadLog(logFile));
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
        			events.writeEvent(user.getName() + " draw a card.");
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
        
        
        
        JButton btnNewButton = new JButton("UNO");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(user.checkStatus() == 1) {
		        	user.UNO(events);
		        	timer.stop();
		        }
        	}
        });
	    btnNewButton.setBounds(654, 357, 88, 41);
	    contentPane.add(btnNewButton);

	    JLabel lblNewLabel = new JLabel(sessionName);
	    lblNewLabel.setBounds(598, 10, 155, 30);
	    lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
	    contentPane.add(lblNewLabel);
	    
	    scrollPane_3 = new JScrollPane();
        scrollPane_3.setBounds(170, 100, 1134, 130);
        getContentPane().add(scrollPane_3, BorderLayout.CENTER);
        
        displayPlayerCards(user.getPlayerCards(), scrollPane_2);
		game.addPlayers(players, scrollPane_3);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(342, 309, 123, 181);
		contentPane.add(scrollPane_4);
		showInfo(scrollPane_4);
		
		JButton btnPenalize = new JButton("Penalize");
		btnPenalize.setBounds(654, 418, 88, 41);
		btnPenalize.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	stopTimer();
	            }
	        });
		contentPane.add(btnPenalize);
		
		JButton btnNewButton_1 = new JButton("Load Game");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isUserTurn) {
					saveGameToFile();
					game.saveLog(events, sessionName);
				}
			}
		});
		btnNewButton_1.setBounds(919, 367, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Would you like to save the game before exiting?", "Exit Game", JOptionPane.YES_NO_CANCEL_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    saveGameToFile(); 
                } 
                else if (option == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
                MainMenu_page mainMenuPage = new MainMenu_page(MainMenu_page.thisUser.getUsername());
                mainMenuPage.setVisible(true);
                dispose();
			}
		});
		btnNewButton_2.setBounds(919, 416, 85, 21);
		contentPane.add(btnNewButton_2);
		
		
	}

	/**
	 * Displays the player's cards on the GUI, allowing the player to select a card to play.
	 * 
	 * @param playerCards The list of cards held by the player.
	 * @param scrollPane  The scroll pane component to display the cards within.
	 */
	private void displayPlayerCards(ArrayList<Card> playerCards, JScrollPane scrollPane) {
		GridLayout gridLayout = new GridLayout(1, 3, 10, 10);
		JPanel playerPanel = new JPanel();
		playerPanel.setBackground(new Color(255, 255, 255));
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
	        		        events.writeEvent(user.getName() + " played " + clickedCard.getName());
	        		        if(user.checkStatus() == 1) {
	        		        	user.setUNO(true);
	        		            UNOTimer();
	        		        }
	        		        else if(user.checkStatus() == 2) {
	        		        	game.endGame(true,game.calculateScore(players));
	        					game.saveLog(events, sessionName);
	        		        	events.writeEvent(user.getName() + " won!" );
	        					JOptionPane.showMessageDialog(null, user.getName() + " won " + sessionName, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
	        					MainMenu_page mainMenuPage = new MainMenu_page(MainMenu_page.thisUser.getUsername());
	        		            mainMenuPage.setVisible(true);
	        					dispose();
	        				}
	        		        else {
	        		        	user.setUNO(false);
	        		        }
	        		        
	        		        
	        		        if(user.getUNO()) {
	        					SecureRandom secureRandom = new SecureRandom();
	        					int randomNumber = secureRandom.nextInt(4);
	        					if (randomNumber < 3) {
	        						String player = "Player " + String.valueOf(secureRandom.nextInt(1,players.size() + 1));
	        						Game.drawCard(user, drawCards, discardCards);
	        						events.writeEvent(player + " penalized " + user.getName());
	        						JOptionPane.showMessageDialog(null, player + " penalized " + user.getName(), "Penalty", JOptionPane.INFORMATION_MESSAGE);
	        					}
	        		        }
	        		        
	        		        if(clickedCard instanceof WildCard) {
	        		        	String[] options = {"Red", "Yellow", "Blue", "Green"};
	        			        int choice = JOptionPane.showOptionDialog(e.getComponent(), "Choose a color", "Colors",
	        			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	        			        if (choice != JOptionPane.CLOSED_OPTION) {
	        			        	currentColor = options[choice];
	        			        	events.writeEvent(user.getName() + " changed color to " + currentColor );
	        			        }
	        			        if(((WildCard) clickedCard).getType().equals(WildCard.types[1]) ) {
	        			        	events.writeEvent(user.getName() + " played Draw+4 Card.");
	        						if(direction) {
	        							game.drawCard(players.get(players.size()-1), drawCards, discardCards);
	        							game.drawCard(players.get(players.size()-1), drawCards, discardCards);
	        							game.drawCard(players.get(players.size()-1), drawCards, discardCards);
	        							game.drawCard(players.get(players.size()-1), drawCards, discardCards);
	        						}
	        						else {
	        							game.drawCard(players.get(0), drawCards, discardCards);
	        							game.drawCard(players.get(0), drawCards, discardCards);
	        							game.drawCard(players.get(0), drawCards, discardCards);
	        							game.drawCard(players.get(0), drawCards, discardCards);
	        						}
	        			        }
	        		        }
	        		        else if(clickedCard instanceof ActionCard) {
	        		        	if(((ActionCard) clickedCard).getAction().equals(ActionCard.actionTypes[1]) ) {
	        						events.writeEvent(user.getName() + " cahnged the direction");
	        						direction = !direction;
	        					}
	        					else if(((ActionCard) clickedCard).getAction().equals(ActionCard.actionTypes[0]) ) {
	        						events.writeEvent(user.getName() + " played Draw Card.");
	        						if(direction) {
	        							game.drawCard(players.get(players.size()-1), drawCards, discardCards);
	        							game.drawCard(players.get(players.size()-1), drawCards, discardCards);
	        						}
	        						else {
	        							game.drawCard(players.get(0), drawCards, discardCards);
	        							game.drawCard(players.get(0), drawCards, discardCards);
	        						}
	        					}
	        		        }
	        		        
	        		        displayPlayerCards(user.getPlayerCards(), scrollPane_2);
	        		        showInfo(scrollPane_4);
	        		        
	        		        if (currentSign == ActionCard.actionTypes[2]) {
	        		        	startTour(true);
	        		        	events.writeEvent(user.getName() + " played Skip Card.");
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
	
	/**
	 * Creates a JPanel for the given card, with appropriate image and layout based on the card type.
	 * 
	 * @param card The card object for which the panel is created.
	 * @return JPanel representing the card.
	 */
	private JPanel panelMaker(Card card) {
	    
	    ImageIcon image = card.getImage();
	    CardPanel cardPanel = new CardPanel(card) {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
	        }
	    };
	    cardPanel.setBackground(new Color(255, 255, 255));
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
	
	/**
	 * Displays game information such as current color, sign, number of draw cards, and direction in a JScrollPane.
	 * 
	 * @param scrollPane The JScrollPane to display the information.
	 */
	private void showInfo(JScrollPane scrollPane) {
		
		scrollPane.setViewportView(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel color = new JLabel("Current Color: " + currentColor);
		color.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(color);
		
		JLabel sign = new JLabel("Current Sign: " + currentSign);
		sign.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(sign);
		
		JLabel num = new JLabel("Number of DrawCards: " + drawCards.size());
		num.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(num);
		
		String d;
		if(direction) {
			d = "Clock-Wise";
		}
		else {
			d = "Counter-Clock-Wise";
		}
		
		JLabel direct = new JLabel("Direction: " + d);
		direct.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(direct);
		
		panel.setBounds(342, 309, 1134, 191);
		scrollPane.setViewportView(panel);
	    scrollPane.revalidate();
	    scrollPane.repaint();
	}
	
	/**
	 * Starts the game tour based on the current direction and whether to skip players.
	 * 
	 * @param skip If true, skips the next player in the tour.
	 */
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
		showInfo(scrollPane_4);
		isUserTurn = true;
	}
	/**
	 * Helper method to start the game tour from the specified current player index.
	 * 
	 * @param current The index of the current player in the players list.
	 */
	
	private void startTourHelper(int current) {
		int version = 0;
		int last = 0;
		if (!direction) {
			for(int i = current; i < players.size(); i++) {
				
				Card choosenCard = players.get(i).choosedCard(currentSign, currentColor, drawCards, discardCards, events);
				currentColor = choosenCard.getColor();
				currentSign = choosenCard.getSign();
				panel_1.removeAll();
				panel_1 = panelMaker(choosenCard);
				scrollPane_1.revalidate();
				scrollPane_1.repaint();
			    scrollPane_1.setViewportView(panel_1);
				discardCards.add(choosenCard);
				events.writeEvent(players.get(i).getName() + " played " + choosenCard.getName());
				displayPlayerCards(user.getPlayerCards(), scrollPane_2);
		       
				try {
				    players.get(i).getPlayerCards().remove(choosenCard);
				} catch (Exception e) {
				    System.err.println("Kart kaldırılırken bir hata oluştu: " + e.getMessage());
				    e.printStackTrace();
				}
				if(players.get(i).checkStatus() == 1) {
					players.get(i).UNO(events);
				}
				else if(players.get(i).checkStatus() == 2) {
					game.endGame(false,0);
					game.saveLog(events, sessionName);
					MainMenu_page mainMenuPage = new MainMenu_page(MainMenu_page.thisUser.getUsername());
		            mainMenuPage.setVisible(true);
		            events.writeEvent(players.get(i).getName() + " won!");
					JOptionPane.showMessageDialog(this, players.get(i).getName() + " won " + sessionName, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				else {
					players.get(i).setUNO(false);
				}
				if(players.get(i).getUNO()) {
					SecureRandom secureRandom = new SecureRandom();
					int randomNumber = secureRandom.nextInt(4);
					if (randomNumber < 3 && players.size() == 1) {
						int num = secureRandom.nextInt(1,players.size() + 1);
						if(num == i) {
							if (num == 1) {
								num = 2;
							}
							if(num == players.size()) {
								num = players.size() - 1;
							}
						}
						String player = "Player " + String.valueOf(num);
						JOptionPane.showMessageDialog(this, player + " penalized " + players.get(i).getName(), "Penalty", JOptionPane.INFORMATION_MESSAGE);
						Game.drawCard(players.get(i),drawCards, discardCards);
					}
					else {
						startTimer(players.get(i));
					}
				}
				
				if(choosenCard instanceof ActionCard) {
					if(((ActionCard) choosenCard).getAction().equals(ActionCard.actionTypes[1]) ) {
						events.writeEvent(players.get(i).getName() + " cahnged the direction");
						direction = true;
						last = i - 1;
						version = 1;
						break;
					}
					else if(((ActionCard) choosenCard).getAction().equals(ActionCard.actionTypes[2]) ) {
						events.writeEvent(players.get(i).getName() + " played Skip Card.");
						if(i == players.size() - 1) {
							last = 0;
							version = 1;
							break;
						}
						last = i + 2;
						version = 1;
						break;
					}
					else if(((ActionCard) choosenCard).getAction().equals(ActionCard.actionTypes[0]) ) {
						events.writeEvent(players.get(i).getName() + " played Draw Card.");
						if(i == players.size() - 1) {
							
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							events.writeEvent(user.getName() + " draw two Card!");
							displayPlayerCards(user.getPlayerCards(), scrollPane_2);
							break;
						}
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						events.writeEvent(players.get(i + 1).getName() + " draw two Card!");
					}
				}
				else if(choosenCard instanceof WildCard) {
					currentColor = players.get(i).makeChoose();
					JOptionPane.showMessageDialog(this, currentColor + " is new color", "Color", JOptionPane.WARNING_MESSAGE);
					if(((WildCard) choosenCard).getType() == WildCard.types[1]) {
						if(i == players.size() - 1) {
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							events.writeEvent(user.getName() + " draw four Card!");
							break;
						}
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						events.writeEvent(players.get(i + 1).getName() + " draw four Card!");
						
					}
				}
				game.addPlayers(players, scrollPane_3);
				showInfo(scrollPane_4);
			}
		}
		else {
			for(int i = current; i >=0; i--) {
				
				Card choosenCard = players.get(i).choosedCard(currentSign, currentColor, drawCards, discardCards, events);
				currentColor = choosenCard.getColor();
				currentSign = choosenCard.getSign();
				panel_1.removeAll();
				panel_1 = panelMaker(choosenCard);
				scrollPane_1.revalidate();
				scrollPane_1.repaint();
			    scrollPane_1.setViewportView(panel_1);
				discardCards.add(choosenCard);
				events.writeEvent(players.get(i).getName() + " played " + choosenCard.getName());
				displayPlayerCards(user.getPlayerCards(), scrollPane_2);
		        
				try {
				    players.get(i).getPlayerCards().remove(choosenCard);
				} catch (Exception e) {
				    System.err.println("Kart kaldırılırken bir hata oluştu: " + e.getMessage());
				    e.printStackTrace();
				}
				if(players.get(i).checkStatus() == 1) {
					players.get(i).UNO(events);
				}
				else if(players.get(i).checkStatus() == 2) {
					game.endGame(false,0);
					game.saveLog(events, sessionName);
					MainMenu_page mainMenuPage = new MainMenu_page(MainMenu_page.thisUser.getUsername());
		            mainMenuPage.setVisible(true);
		            events.writeEvent(players.get(i).getName() + " won!");
					JOptionPane.showMessageDialog(this, players.get(i).getName() + " won " + sessionName, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				else {
					players.get(i).setUNO(false);
				}
				if(players.get(i).getUNO()) {
					SecureRandom secureRandom = new SecureRandom();
					int randomNumber = secureRandom.nextInt(4);
					if (randomNumber < 3 && players.size() == 1) {
						int num = secureRandom.nextInt(1,players.size() + 1);
						if(num == i) {
							if (num == 1) {
								num = 2;
							}
							if(num == players.size()) {
								num = players.size() - 1;
							}
						}
						String player = "Player " + String.valueOf(num);
						JOptionPane.showMessageDialog(this, player + " penalized " + players.get(i).getName(), "Penalty", JOptionPane.INFORMATION_MESSAGE);
						Game.drawCard(players.get(i),drawCards, discardCards);
					}
					else {
						startTimer(players.get(i));
					}
				}
				game.addPlayers(players, scrollPane_3);
				if(choosenCard instanceof ActionCard) {
					if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[1]) {
						events.writeEvent(players.get(i).getName() + " cahnged the direction");
						direction = false;
						last = i + 1;
						version = 1;
						break;
					}
					else if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[2]) {
						events.writeEvent(players.get(i).getName() + " played Skip Card.");
						if(i == 0) {
							last = players.size() - 1;
							version = 1;
							break;
						}
						version = 1;
						last = i - 2;
					}
					else if(((ActionCard) choosenCard).getAction() == ActionCard.actionTypes[0]) {
						events.writeEvent(players.get(i).getName() + " played Draw Card.");
						if(i == 0) {
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							events.writeEvent(user.getName() + " draw two Card!");
							break;
							
						}
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						events.writeEvent(players.get(i - 1).getName() + " draw two Card!");
	        			
					}
				}
				else if(choosenCard instanceof WildCard) {
					currentColor = players.get(i).makeChoose();
					JOptionPane.showMessageDialog(this, currentColor + " is new color", "Color", JOptionPane.WARNING_MESSAGE);
					if(((WildCard) choosenCard).getType() == WildCard.types[1]) {
						if(i == 0) {
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							events.writeEvent(user.getName() + " draw four Card!");

							break;
						}
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						events.writeEvent(players.get(i - 1).getName() + " draw four Card!");
						
					}
					currentColor = players.get(i).makeChoose();
					currentSign = null;
				}
				game.addPlayers(players, scrollPane_3);
				showInfo(scrollPane_4);
			}
		}
		if(version == 1) {
			startTourHelper(last);
		}
		
	}
	
	
	/**
	 * Starts a timer to enforce a player to draw a card within a specified duration.
	 * The timer sets a 2-second duration for the player passed as a parameter.
	 * If the player fails to draw a card within the specified time, a penalty is applied to the player.
	 * This penalty is enforced through the game.drawCard() function and displayed to the user as an informational message.
	 *
	 * @param player The player who will draw the card.
	 */
	public void startTimer(Player player) {
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
	            game.drawCard(player, drawCards, discardCards);
	            events.writeEvent(user.getName() + " penalized " + player.getName());
	            JOptionPane.showMessageDialog(null, user.getName() + "penalized" + player.getName(), "Penalty", JOptionPane.WARNING_MESSAGE);
            }
        });
        timer.setRepeats(false);
        timer.start(); 
    }
	
	/**
	 * Starts a timer to enforce a penalty if a player fails to call UNO within a specified duration.
	 * The timer sets a 2-second duration for the player.
	 * If the player fails to call UNO within the specified time, a penalty is applied.
	 * The penalty involves the user drawing a card and is recorded in the game events.
	 * Additionally, an informational message is displayed to inform the user about the penalty.
	 */
	public void UNOTimer() {
		timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SecureRandom secureRandom = new SecureRandom();
				int randomNumber = secureRandom.nextInt(4);
				if (randomNumber < 3) {
					String player = "Player " + String.valueOf(secureRandom.nextInt(1,players.size() + 1));
					Game.drawCard(user, drawCards, discardCards);
		            events.writeEvent(player + " penalized " + user.getName());
					
					JOptionPane.showMessageDialog(null, player + " penalized " + user.getName(), "Penalty", JOptionPane.INFORMATION_MESSAGE);
				}
            }
		});
		timer.setRepeats(false);
        timer.start();
	}
	
	/**
	 * Stops the timer if it is currently running.
	 * Checks if the timer instance is not null and is currently running before stopping it.
	 */
	public void stopTimer() {
	        if (timer != null && timer.isRunning()) {
	            timer.stop(); 
	        } 
	    }
	
	/**
	 * Saves the current game state to a text file.
	 * Writes the game data including draw cards, discard cards, player information, current color, current sign,
	 * turn status, direction, and session name to a text file.
	 * The file is saved in the directory specific to the user's username.
	 * If the directory does not exist, it creates one.
	 *
	 * @throws IOException if an I/O error occurs while writing to the file
	 */
    private void saveGameToFile() {
    	
    	
    	try  {
    		
    		File userDirectory = new File("/Users/muham/git/Comp-Project/comp132_project/src/txts/" + MainMenu_page.thisUser.getUsername());
            if (!userDirectory.exists()) {
                userDirectory.mkdirs();
            }
        	
            String filePath = userDirectory.getAbsolutePath() + File.separator + sessionName + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
    		writer.write("DrawCards;" + game.cardsToString(drawCards));
    		writer.write("\nDiscardCards;" + game.cardsToString(discardCards));
    		writer.write("\nPlayers;");
    		for(Player player : players){
    			writer.write(player.getName() + "+" + game.cardsToString(player.getPlayerCards()) + "!");
    		}
    		writer.write("\nUser;" + game.cardsToString(user.getPlayerCards()));
    		writer.write("\nCurrentColor;" + currentColor);
    		writer.write("\nCurrentSign;" + currentSign);
    		writer.write("\nIsUserTurn;" + String.valueOf(isUserTurn));
    		writer.write("\nDirection;" + String.valueOf(direction));
    		writer.write("\nSessionName;" + sessionName);
    		
    		writer.close();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    
    /**
     * Loads a game state from a file.
     *
     * @param fileName the name of the file to load the game state from
     */
    private void loadGame(String fileName) {
    	try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
    		String line;
    		
    		while ((line = reader.readLine()) != null) {
                String[] elm = line.split(";");
                if(elm[0].equals("DrawCards")) {
                    drawCards = game.loadCards(elm[1].split(","));
                }
                else if(elm[0].equals("DiscardCards")) {
                    discardCards = game.loadCards(elm[1].split(","));    
                }
                else if(elm[0].equals("Players")) {
                    players = game.loadPlayers(elm[1]);
                }
                else if(elm[0].equals("User")) {
                    Player player = new Player("You", game.loadCards(elm[1].split(",")));
                    user = player;
                }
                else if(elm[0].equals("CurrentColor")) {
                    currentColor = elm[1];
                }
                else if(elm[0].equals("CurrentSign")) {
                    currentSign = elm[1];
                }
                else if(elm[0].equals("IsUserTurn")) {
                    isUserTurn = Boolean.valueOf(elm[1]);
                }
                else if(elm[0].equals("Direction")) {
                    direction = Boolean.valueOf(elm[1]);
                }
                else if(elm[0].equals("SessionName")) {
                	sessionName = elm[1];
                }
            }
    		
		}
    	catch (IOException e) {
			e.printStackTrace();
		}
    }    
}