package card;

import javax.swing.ImageIcon;

import gui.ImageResizer;

public class WildCard extends Card{
	
	private String type;
	public static String[] types = {"Normal", "Draw"};
	
	public WildCard(String type) {
		this.type = type;
		if(type == "Normal") {
			image = new ImageIcon(ImageResizer.resizeImage("img/wildcard.png", 120, 175));
		}
		else if(type == "Draw") {
			image = new ImageIcon(ImageResizer.resizeImage("img/wilddrawcard.png", 120, 175));
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getColor() {
		return null;
	}
	@Override
	public String getSign() {
		return null;
	}
}
