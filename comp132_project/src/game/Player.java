package game;

import java.util.ArrayList;

import card.Card;

public class Player{
	
	protected ArrayList<Card> playerCards;
	protected String name;
	
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

	

}
