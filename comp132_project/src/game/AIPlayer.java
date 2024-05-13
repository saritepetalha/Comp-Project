package game;

import java.util.ArrayList;

import card.Card;
import card.NormalCard;

public class AIPlayer extends Player implements Move{

	public AIPlayer(String name, ArrayList<Card> cards) {
		super(name, cards);
		
	}
	
	@Override
	public Card choosedCard(String sign, String color, ArrayList<Card> drawCards, ArrayList<Card> discards) {
		
		for (Card card : playerCards) {
			if (Game.isValidMove(card, sign, color)) {
				return card;
			}
		}
		Game.drawCard(this, drawCards, discards);
		return choosedCard(sign, color, drawCards, discards);
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
}
