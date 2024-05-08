package card;

import javax.swing.ImageIcon;

public class ActionCard extends NormalCard{
	
	protected String action;
	public static String[] actionTypes = {"Draw", "Reverse", "Skip"};
	private ImageIcon actionImage;
	private String imagePath;
	
	public ActionCard(String color, String action) {
		super(color);
		this.action = action;
		if(action == "Draw") {
			actionImage =new ImageIcon("img/draw.png");
			imagePath = "img/draw.png";
		}
		else if(action == "Reverse") {
			actionImage =new ImageIcon("img/reverse.png");
			imagePath = "img/reverse.png";
		}
		else if(action == "Skip") {
			actionImage =new ImageIcon("img/skip.png");
			imagePath = "img/skip.png";
		}
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public static String[] getActionTypes() {
		return actionTypes;
	}

	public static void setActionTypes(String[] actionTypes) {
		ActionCard.actionTypes = actionTypes;
	}

	public ImageIcon getActionImage() {
		return actionImage;
	}

	public void setActionImage(ImageIcon actionImage) {
		this.actionImage = actionImage;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
