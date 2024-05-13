package card;

import javax.swing.ImageIcon;

public abstract class Card {

    protected ImageIcon image;

    public ImageIcon getImage() {
        return image;
    }

	public abstract String getColor();
	public abstract String getSign();
}
