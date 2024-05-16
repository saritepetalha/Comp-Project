package game;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import card.ActionCard;
import card.Card;
import card.NumberCard;
import card.WildCard;
import gui.MainMenu_page;

public class Game {
	
	/**
	 * Generates a standard deck of UNO cards.
	 *
	 * @return The generated deck of UNO cards.
	 */
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
				String actionType = card.ActionCard.actionTypes[i];
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
	
	/**
	 * Shuffles the given list of cards.
	 *
	 * @param cards The list of cards to shuffle.
	 */
	
	public static void shuffleCards(ArrayList<Card> cards){
		Collections.shuffle(cards);
	}
	
	/**
	 * Deals a specified number of cards from the draw pile to a player.
	 *
	 * @param numCards   The number of cards to deal.
	 * @param drawCards  The draw pile containing the cards.
	 * @return           The list of cards dealt to the player.
	 */
	public ArrayList<Card> dealCards(int numCards, ArrayList<Card> drawCards) {
        ArrayList<Card> playerCards = new ArrayList<>();
        for (int j = 0; j < numCards; j++) {
            if (!drawCards.isEmpty()) {
                Card card = drawCards.remove(0);
                playerCards.add(card);
            }
        }
        return playerCards;
    }
	
	/**
	 * Checks if a given card can be played as a valid move.
	 *
	 * @param card          The card to be checked.
	 * @param currentSign   The current sign in play.
	 * @param currentColor  The current color in play.
	 * @return              True if the card can be played, false otherwise.
	 */
	public static boolean isValidMove(Card card, String currentSign, String currentColor) {
		
		if(card instanceof WildCard || card.getSign().equals(currentSign) || card.getColor().equals(currentColor)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Draws a card from the draw pile and adds it to the player's hand.
	 *
	 * @param player      The player who is drawing the card.
	 * @param drawCards   The draw pile containing the cards.
	 * @param discards    The discard pile containing the cards.
	 */
	public static void drawCard(Player player, ArrayList<Card> drawCards, ArrayList<Card> discards) {
		if(drawCards.isEmpty()) {
			shuffleCards(discards);
			drawCards.addAll(discards);
			discards.clear();
		}
		player.playerCards.add(drawCards.remove(0));
	}
	 /**
     * Converts an original file path to a new file path based on different root directories.
     *
     * @param originalPath the original file path to be converted
     * @param originalRoot the original root directory of the file path
     * @param newRoot the new root directory to convert the file path to
     * @return the converted file path
     * @throws IllegalArgumentException if the original path doesn't start with the original root
     */
    public static String convertPath(String originalPath, String originalRoot, String newRoot) {
        if (originalPath.startsWith(originalRoot)) {
            String relativePath = originalPath.substring(originalRoot.length());
            return newRoot + relativePath;
        } else {
            throw new IllegalArgumentException("Original path doesn't start with the original root.");
        }
    }
    
    /**
     * Loads the game log from a text file.
     *
     * @param fileName the name of the log file to load
     * @return a String containing the loaded log
     */
    public String loadLog(String fileName) {
    	
    	StringBuilder log = new StringBuilder();
    	
    	try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
            	log.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    	String logs = log.toString();
    	return logs;
    }
    /**
     * Saves the log events to a text file.
     *
     * @param events the Log object containing the events to be saved
     * @param sessionName the name of the session to be used in the file name
     */
    public void saveLog(Log events, String sessionName) {
    	
    	try {
    		File userDirectory = new File("/Users/muham/git/Comp-Project/comp132_project/src/txts/logs/" + MainMenu_page.thisUser.getUsername());
            if (!userDirectory.exists()) {
                userDirectory.mkdirs();
            }
           String filePath = userDirectory.getAbsolutePath() + File.separator + sessionName +  ".txt";
           BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
           writer.write(events.getEvents());
           writer.close();
    	}
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * Calculates the score based on the cards held by each player.
     *
     * @param players the list of AI players
     * @return the total score calculated based on the cards held by the players
     */
    public int calculateScore(ArrayList<AIPlayer> players) {
        int score = 0;
        for (Player player : players) {
            for (Card card : player.getPlayerCards()) {
                if (card instanceof NumberCard) {
                    score += ((NumberCard) card).getNumber();
                } else if (card instanceof ActionCard) {
                    score += 20;
                } else {
                    score += 50;
                }
            }
        }
        return score;
    }
    /**
     * Ends the game and updates user statistics in the users.txt file.
     *
     * @param result true if the user won, false otherwise
     * @param score the user's score in the game
     */
    public void endGame(boolean result, int score) {
    	
    	File file = new File("/Users/muham/git/Comp-Project/comp132_project/src/txts/users.txt");
    	
    	try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder fileContent = new StringBuilder();
            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                
                if (username.equals(MainMenu_page.thisUser.getUsername())) {
                    int gamesPlayed = Integer.parseInt(parts[2]);
                    int wins = Integer.parseInt(parts[3]);
                    int losses = Integer.parseInt(parts[4]);
                    int totalScore = Integer.parseInt(parts[5]);
                    
                    gamesPlayed++;
                    if(result) {
                    	wins++;
                    }
                    else {
                    	losses++;
                    }
                    totalScore+= score;
                    
                    line = String.join(",", username, parts[1], String.valueOf(gamesPlayed), String.valueOf(wins), String.valueOf(losses), String.valueOf(totalScore));
                }
                
                fileContent.append(line).append("\n");
            }
            
            br.close();
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(fileContent.toString());
            bw.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Loads players from a string representation.
     *
     * @param line a string representing player data
     * @return an ArrayList containing the loaded AIPlayers
     */
    public ArrayList<AIPlayer> loadPlayers(String line){
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
    /**
     * Loads cards from a string array representation.
     *
     * @param cards an array of strings representing the cards
     * @return an ArrayList containing the loaded cards
     */
    public ArrayList<Card> loadCards(String[] cards){
    	
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
    /**
     * Converts an ArrayList of cards to a comma-separated string representation.
     *
     * @param cards the ArrayList of cards to convert
     * @return a comma-separated string representation of the cards
     */
    public String cardsToString(ArrayList<Card> cards){
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

	/**
	 * Adds player information to the scroll pane.
	 * 
	 * @param players    ArrayList containing AIPlayer objects representing the players.
	 * @param scrollPane JScrollPane to contain the player information panel.
	 */
	public void addPlayers(ArrayList<AIPlayer> players, JScrollPane scrollPane) {
	    JPanel playerPanel = new JPanel(new GridLayout(3, 3)); 
	    playerPanel.setBackground(new Color(255, 255, 255));

	    for (int i = 1; i <= players.size(); i++) {
	        String playerName = "Player " + i + " Cards: " + String.valueOf(players.get(i-1).getPlayerCards().size()); 
	        JLabel playerLabel = new JLabel(playerName); 
	        playerPanel.add(playerLabel); 

	    }

	    playerPanel.setBounds(170, 50, 1134, 191);
	    scrollPane.setViewportView(playerPanel); 
	    
	}
    
}
