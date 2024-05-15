package card;

import javax.swing.ImageIcon;

import gui.ImageResizer;

public class NormalCard extends Card{
	
	private static final long serialVersionUID = 1L;
	protected String color;
	public static String[] colors= {"Red", "Yellow", "Blue", "Green"};
	
	public NormalCard(String color) {
		this.color = color;
		if(color.equals("Red")) {
			image = new ImageIcon(ImageResizer.resizeImage("img/redcard.png", 120, 175));
		}
		else if(color.equals("Green")) {
			image = new ImageIcon(ImageResizer.resizeImage("img/greencard.png", 120, 175));
		}
		else if(color.equals("Blue")) {
			image = new ImageIcon(ImageResizer.resizeImage("img/bluecard.png", 120, 175));
		}
		else if(color.equals("Yellow")) {
			image = new ImageIcon(ImageResizer.resizeImage("img/yellowcard.png", 120, 175));
		}
	}
	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String getSign() {
		return null;
	}
}
