package game;

import java.util.ArrayList;

import card.Card;

public interface Move {

	public Card choosedCard(String sign, String color, ArrayList<Card> drawCards, ArrayList<Card> discards);
	public String makeChoose();
}
