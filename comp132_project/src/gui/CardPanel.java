package gui;

import javax.swing.JPanel;

import card.Card;

public class CardPanel extends JPanel {

	private Card card;

	public CardPanel(Card card) {
		this.card = card;
	}

	public Card getCard() {
		return card;
	}
}
