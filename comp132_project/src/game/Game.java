package game;

import java.util.ArrayList;
import java.util.Collections;

import card.ActionCard;
import card.Card;
import card.NumberCard;
import card.WildCard;

public class Game {
	
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
	public static void shuffleCards(ArrayList<Card> cards){
		Collections.shuffle(cards);
	}
	
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
	public static boolean isValidMove(Card card, String currentSign, String currentColor) {
		
		if(card instanceof WildCard || card.getSign().equals(currentSign) || card.getColor() == currentColor) {
			return true;
		}
		return false;
	}
	
	public static void drawCard(Player player, ArrayList<Card> drawCards, ArrayList<Card> discards) {
		if(drawCards.isEmpty()) {
			shuffleCards(discards);
			drawCards.addAll(discards);
			discards.clear();
		}
		player.playerCards.add(drawCards.remove(0));
	}
}
