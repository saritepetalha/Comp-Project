package card;

import java.io.Serializable;

import javax.swing.ImageIcon;

import gui.ImageResizer;

public class ActionCard extends NormalCard implements Serializable{
	
	private static final long serialVersionUID = 1L;
	protected String action;
	public static String[] actionTypes = {"Draw", "Reverse", "Skip"};
	private ImageIcon actionImage;
	private String imagePath;
	
	public ActionCard(String color, String action) {
		super(color);
		this.action = action;
	    this.name = color + ":" + action + ":Action";
		if(action.equals("Draw")) {
			actionImage =new ImageIcon(ImageResizer.resizeImage("img/draw.png", 30, 30));
			imagePath = "img/draw.png";
		}
		else if(action.equals("Reverse")) {
			actionImage =new ImageIcon(ImageResizer.resizeImage("img/reverse.jpg", 30, 30));
			imagePath = "img/reverse.jpg";
		}
		else if(action.equals("Skip")) {
			actionImage =new ImageIcon(ImageResizer.resizeImage("img/skip.png", 30, 30));
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
	@Override
	public String getSign() {
		return action;
	}
}
