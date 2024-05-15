package game;

import java.util.ArrayList;

import card.Card;

public interface Move {

	String makeChoose();
	Card choosedCard(String sign, String color, ArrayList<Card> drawCards, ArrayList<Card> discards, Log events);
}
