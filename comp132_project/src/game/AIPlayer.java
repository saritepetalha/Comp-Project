package game;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.io.Serializable;
import java.security.SecureRandom;

import card.Card;
import card.NormalCard;

public class AIPlayer extends Player implements Move, Serializable{

	public AIPlayer(String name, ArrayList<Card> cards) {
		super(name, cards);
		
	}
	
	@Override
	public Card choosedCard(String sign, String color, ArrayList<Card> drawCards, ArrayList<Card> discards, Log events) {
		
		for (Card card : playerCards) {
			if (Game.isValidMove(card, sign, color)) {
				return card;
			}
		}
		Game.drawCard(this, drawCards, discards);
		events.writeEvent(this.name + " draw a card.");
		return choosedCard(sign, color, drawCards, discards, events);
	}

	@Override
	public String makeChoose() {
		int red = 0;
		int yellow = 0;
		int green = 0;
		int blue = 0;
		
		for(Card card : playerCards) {
			if(card instanceof NormalCard) {
				if (card.getColor().equals(NormalCard.colors[0])) {
					red++;
				}
				else if (card.getColor().equals(NormalCard.colors[1])) {
					yellow++;
				}
				else if (card.getColor().equals(NormalCard.colors[2])) {
					green++;
				}
				else if (card.getColor().equals(NormalCard.colors[3])) {
					blue++;
				}
			}
		}
		int max = Math.max(Math.max(red, green), Math.max(yellow, blue));
		if(max == red) {
			return NormalCard.colors[0];
		}
		else if(max == yellow) {
			return NormalCard.colors[1];
		}
		else if(max == green) {
			return NormalCard.colors[2];
		}
		else {
			return NormalCard.colors[3];
		}
	}
	@Override
	public void UNO(Log events) {
		SecureRandom secureRandom = new SecureRandom();
		int randomNumber = secureRandom.nextInt(10);
		if(randomNumber < 8) {
			UNO = false;
			JOptionPane.showMessageDialog(null, this.name + "said UNO", "UNO!", JOptionPane.INFORMATION_MESSAGE);
			events.writeEvent(name + " said UNO");
		}
		else {
			UNO = true;
		}
		
	}

}
