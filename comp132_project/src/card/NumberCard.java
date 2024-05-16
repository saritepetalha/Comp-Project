package card;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class NumberCard extends NormalCard{
	
	protected int number;
	
	public NumberCard(String color, int number) {
		super(color);
		this.number = number;
		this.name = color + ":" + String.valueOf(number) + ":Number";
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String getSign() {
		return String.valueOf(number);
	}
}
