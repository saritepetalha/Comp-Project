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
	

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public GameSession_page(String sessionName, int numPlayer) {
		
		getContentPane().setLayout(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1441, 952);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		this.sessionName = sessionName;
		
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
		user = new Player("You", game.dealCards(7, drawCards));
		discardCards = new ArrayList<>();
		discardCards.add(drawCards.remove(0));
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
        
        
        
        JButton btnNewButton = new JButton("UNO");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(user.checkStatus() == 1) {
		        	user.UNO();
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
		addPlayers(players, scrollPane_3);
		
		JButton btnPenalize = new JButton("Penalize");
		btnPenalize.setBounds(654, 418, 88, 41);
		contentPane.add(btnPenalize);
		
		JButton btnNewButton_1 = new JButton("Load Game");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isUserTurn) {
					saveGameToFile();
				}
			}
		});
		btnNewButton_1.setBounds(919, 367, 85, 21);
		contentPane.add(btnNewButton_1);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(342, 309, 123, 181);
		contentPane.add(scrollPane_4);
		showInfo(scrollPane_4);
		
		
		btnPenalize.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	stopTimer();
	            }
	        });
		
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
        
        
        
        JButton btnNewButton = new JButton("UNO");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(user.checkStatus() == 1) {
		        	user.UNO();
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
		addPlayers(players, scrollPane_3);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(342, 309, 123, 181);
		contentPane.add(scrollPane_4);
		showInfo(scrollPane_4);
		
		JButton btnPenalize = new JButton("Penalize");
		btnPenalize.setBounds(654, 418, 88, 41);
		contentPane.add(btnPenalize);
		
		JButton btnNewButton_1 = new JButton("Load Game");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isUserTurn) {
					saveGameToFile();
				}
			}
		});
		btnNewButton_1.setBounds(919, 367, 85, 21);
		contentPane.add(btnNewButton_1);
		
		btnPenalize.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	stopTimer();
	            }
	        });
		
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
	        		        if(user.checkStatus() == 1) {
	        		        	user.setUNO(true);
	        		            UNOTimer();
	        		        }
	        		        else if(user.checkStatus() == 2) {
	        					JOptionPane.showMessageDialog(null, user.getName() + " won " + sessionName, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
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
	        						JOptionPane.showMessageDialog(null, player + " penalized " + user.getName(), "Penalty", JOptionPane.INFORMATION_MESSAGE);
	        					}
	        		        }
	        		        
	        		        if(clickedCard instanceof WildCard) {
	        		        	String[] options = {"Red", "Yellow", "Blue", "Green"};
	        			        int choice = JOptionPane.showOptionDialog(e.getComponent(), "Choose a color", "Colors",
	        			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	        			        if (choice != JOptionPane.CLOSED_OPTION) {
	        			        	currentColor = options[choice];
	        			        }
	        		        }
	        		        
	        		        displayPlayerCards(user.getPlayerCards(), scrollPane_2);
	        		        showInfo(scrollPane_4);
	        		        
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
	
	private void showInfo(JScrollPane scrollPane) {
		
		JPanel panel = new JPanel(new GridLayout(3, 3));
		JLabel color = new JLabel("Current Color: " + currentColor);
		panel.add(color);
		
		JLabel sign = new JLabel("Current Sign: " + currentSign);
		panel.add(sign);
		
		JLabel num = new JLabel("Number of Cards: " + drawCards.size());
		panel.add(num);
		
		panel.setBounds(342, 309, 1134, 191);
	    scrollPane.setViewportView(panel); 
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
		showInfo(scrollPane_4);
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
				displayPlayerCards(user.getPlayerCards(), scrollPane_2);
		       
				try {
				    players.get(i).getPlayerCards().remove(choosenCard);
				} catch (Exception e) {
				    System.err.println("Kart kaldırılırken bir hata oluştu: " + e.getMessage());
				    e.printStackTrace();
				}
				if(players.get(i).checkStatus() == 1) {
					players.get(i).UNO();
				}
				else if(players.get(i).checkStatus() == 2) {
					JOptionPane.showMessageDialog(this, players.get(i).getName() + " won " + sessionName, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				else {
					players.get(i).setUNO(false);
				}
				if(players.get(i).getUNO()) {
					SecureRandom secureRandom = new SecureRandom();
					int randomNumber = secureRandom.nextInt(4);
					if (randomNumber < 3) {
						String player = "Player " + String.valueOf(secureRandom.nextInt(1,players.size() + 1));
						JOptionPane.showMessageDialog(this, player + "penalized" + players.get(i).getName(), "Penalty", JOptionPane.WARNING_MESSAGE);
					}
					else {
						startTimer(players.get(i));
					}
				}
				
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
					JOptionPane.showMessageDialog(this, currentColor + " is new color", "Color", JOptionPane.WARNING_MESSAGE);
					if(((WildCard) choosenCard).getType() == WildCard.types[1]) {
						if(i == players.size() - 1) {
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							Game.drawCard(user, drawCards, discardCards);
							break;
						}
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						Game.drawCard(players.get(i + 1), drawCards, discardCards);
						
					}
				}
				addPlayers(players, scrollPane_3);
				showInfo(scrollPane_4);
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
				displayPlayerCards(user.getPlayerCards(), scrollPane_2);
		        
				try {
				    players.get(i).getPlayerCards().remove(choosenCard);
				} catch (Exception e) {
				    System.err.println("Kart kaldırılırken bir hata oluştu: " + e.getMessage());
				    e.printStackTrace();
				}
				if(players.get(i).checkStatus() == 1) {
					players.get(i).UNO();
				}
				else if(players.get(i).checkStatus() == 2) {
					JOptionPane.showMessageDialog(this, players.get(i).getName() + " won " + sessionName, "Congratulations", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				else {
					players.get(i).setUNO(false);
				}
				if(players.get(i).getUNO()) {
					SecureRandom secureRandom = new SecureRandom();
					int randomNumber = secureRandom.nextInt(4);
					if (randomNumber < 3) {
						String player = "Player " + String.valueOf(secureRandom.nextInt(1,players.size() + 1));
						JOptionPane.showMessageDialog(this, player + " penalized " + players.get(i).getName(), "Penalty", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						startTimer(players.get(i));
					}
				}
				addPlayers(players, scrollPane_3);
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
							
						}
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
						Game.drawCard(players.get(i - 1), drawCards, discardCards);
	        			
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
				addPlayers(players, scrollPane_3);
				showInfo(scrollPane_4);
			}
		}
		if(version == 1) {
			startTourHelper(last);
		}
		
	}
	private void addPlayers(ArrayList<AIPlayer> players, JScrollPane scrollPane) {
	    JPanel playerPanel = new JPanel(new GridLayout(3, 3)); 

	    for (int i = 1; i <= players.size(); i++) {
	        String playerName = "Player " + i + " Cards: " + String.valueOf(players.get(i-1).getPlayerCards().size()); 
	        JLabel playerLabel = new JLabel(playerName); 
	        playerPanel.add(playerLabel); 

	    }

	    playerPanel.setBounds(170, 50, 1134, 191);
	    scrollPane.setViewportView(playerPanel); 
	    
	}
	
	public void startTimer(Player player) {
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
	            game.drawCard(player, drawCards, discardCards);
	            JOptionPane.showMessageDialog(null, user.getName() + "penalized" + player.getName(), "Penalty", JOptionPane.WARNING_MESSAGE);
            }
        });
        timer.setRepeats(false);
        timer.start(); 
    }
	
	public void UNOTimer() {
		timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SecureRandom secureRandom = new SecureRandom();
				int randomNumber = secureRandom.nextInt(4);
				if (randomNumber < 3) {
					String player = "Player " + String.valueOf(secureRandom.nextInt(1,players.size() + 1));
					Game.drawCard(user, drawCards, discardCards);
					JOptionPane.showMessageDialog(null, player + " penalized " + user.getName(), "Penalty", JOptionPane.INFORMATION_MESSAGE);
				}
            }
		});
		timer.setRepeats(false);
        timer.start();
	}
	public void stopTimer() {
	        if (timer != null && timer.isRunning()) {
	            timer.stop(); 
	        } 
	    }
	
	/**
     * 
     *
     * @param 
     */
    private void saveGameToFile() {
    	
    	
    	try  {
    		
    		File userDirectory = new File("/Users/muham/git/Comp-Project/comp132_project/src/txts/" + MainMenu_page.thisUser.getUsername());
            if (!userDirectory.exists()) {
                userDirectory.mkdirs();
            }
        	
            String filePath = userDirectory.getAbsolutePath() + File.separator + sessionName + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
    		writer.write("DrawCards;" + cardsToString(drawCards));
    		writer.write("\nDiscardCards;" + cardsToString(discardCards));
    		writer.write("\nPlayers;");
    		for(Player player : players){
    			writer.write(player.getName() + "+" + cardsToString(player.getPlayerCards()) + "!");
    		}
    		writer.write("\nUser;" + cardsToString(user.getPlayerCards()));
    		writer.write("\nCurrentColor;" + currentColor);
    		writer.write("\nCurrentSign;" + currentSign);
    		writer.write("\nIsUserTurn;" + String.valueOf(isUserTurn));
    		writer.write("\nDirection;" + String.valueOf(direction));
    		
    		writer.close();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String cardsToString(ArrayList<Card> cards){
    	String value = "";
    	for(int i = 0; i < cards.size(); i++) {
    		Card card = cards.get(i);
    		if (i == cards.size() - 1) {
    			value += card.getName();
    		}
    		else {
    			value += card.getName() + ",";
    		}
        	
        }
    	return value;
    }
    
    private void loadGame(String fileName) {
    	try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
    		String line;
    		
    		while ((line = reader.readLine()) != null) {
                String[] elm = line.split(";");
                if(elm[0].equals("DrawCards")) {
                    drawCards = loadCards(elm[1].split(","));
                }
                else if(elm[0].equals("DiscardCards")) {
                    discardCards = loadCards(elm[1].split(","));    
                }
                else if(elm[0].equals("Players")) {
                    players = loadPlayers(elm[1]);
                }
                else if(elm[0].equals("User")) {
                    Player player = new Player("You", loadCards(elm[1].split(",")));
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
            }
    		
		}
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
    private ArrayList<Card> loadCards(String[] cards){
    	
    	ArrayList<Card> deck = new ArrayList<>();
    	for (String card : cards) {
			Card newCard;
			String[] features = card.split(":");
			;
			if(features[2].equals("Number")) {
				newCard = new NumberCard(features[0], Integer.valueOf(features[1]));
			}
			else if(features[2].equals("Action")) {
				newCard = new ActionCard(features[0], features[1]);
			}
			else{
				newCard = new WildCard(features[1]);
			}
			deck.add(newCard);
    	}
    	return deck;
    }
    private ArrayList<AIPlayer> loadPlayers(String line){
    	ArrayList<AIPlayer> players = new ArrayList<>();
    	String[] elm = line.split("\\!");
    	for (String plyr : elm) {
    		String[] datas = plyr.split("\\+");
    		String[] cardDatas = datas[1].split(",");
    		ArrayList<Card> playerCards = loadCards(cardDatas);
    		Player player = new AIPlayer(datas[0], playerCards);
    		players.add((AIPlayer) player);
    	}	
    	return players;
    }
}

