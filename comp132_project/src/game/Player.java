package game;

import java.io.Serializable;
import java.util.ArrayList;

import card.Card;

public class Player{
	
	protected ArrayList<Card> playerCards;
	protected String name;
	protected boolean UNO = false;
	
	public Player(String name, ArrayList<Card> cards) {
		this.name = name;
		playerCards = cards;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(ArrayList<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Indicates that the player has declared UNO.
	 *
	 * @param events The log where the UNO declaration event will be recorded.
	 */
	
	public void UNO(Log events) {
		events.writeEvent(name + " said UNO!");
		UNO = false;
	}
	public boolean getUNO() {
		return UNO;
	}
	public void setUNO(boolean UNO) {
		this.UNO = UNO;
	}
	/**
	 * Checks the status of the player's hand.
	 *
	 * @return 1 if the player has only one card left,
	 *         2 if the player has no cards left,
	 *         0 otherwise.
	 */
	public int checkStatus(){
		
		if(playerCards.size() == 1) {
			return 1;
		}
		else if(playerCards.size() == 0) {
			return 2;
		}
		return 0;
	}
}
