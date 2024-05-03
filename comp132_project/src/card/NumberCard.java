package card;

import javax.swing.ImageIcon;

public class NumberCard extends NormalCard{
	
	protected int number;
	
	public NumberCard(String color, int number) {
		super(color);
		this.number = number;
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
