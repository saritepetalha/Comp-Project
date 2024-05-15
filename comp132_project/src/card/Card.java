package card;

import javax.swing.ImageIcon;

public abstract class Card {

    protected ImageIcon image;
    protected String name;

    public ImageIcon getImage() {
        return image;
    }

	public abstract String getColor();
	public abstract String getSign();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
