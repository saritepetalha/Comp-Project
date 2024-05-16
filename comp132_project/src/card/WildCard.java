package card;

import java.io.Serializable;

import javax.swing.ImageIcon;

import gui.ImageResizer;

public class WildCard extends Card{
	
	private static final long serialVersionUID = 1L;
	private String type;
	public static String[] types = {"Normal", "Draw"};
	
	public WildCard(String type) {
		this.type = type;
		this.name = "Wild:" + type + ":Wild";
		if(type.equals("Normal") ) {
			image = new ImageIcon(ImageResizer.resizeImage("img/wildcard.png", 120, 175));
		}
		else if(type.equals("Draw")) {
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
