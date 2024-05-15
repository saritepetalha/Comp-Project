package gui;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image backgroundImage;

    public CustomPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
