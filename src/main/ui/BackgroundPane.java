package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

//This class paints the background image for the welcome screen.
//This code was mainly copied from the following source:
//https://stackoverflow.com/questions/28889667/create-a-form-with-a-background-image-jlayeredpane
public class BackgroundPane extends JPanel {
    private BufferedImage cloudImage;

    //EFFECTS: changes the preferred size to be the size of the image.
    @Override
    public Dimension getPreferredSize() {
        BufferedImage img = getBackgroundImage();

        Dimension size = super.getPreferredSize();
        if (img != null) {
            size.width = Math.max(size.width, img.getWidth());
            size.height = Math.max(size.height, img.getHeight());
        }

        return size;
    }

    //getter
    public BufferedImage getBackgroundImage() {
        return cloudImage;
    }

    //MODIFIES: this
    //EFFECTS: sets the background image to be the image value
    public void setBackgroundImage(BufferedImage value) {
        if (cloudImage != value) {
            BufferedImage old = cloudImage;
            cloudImage = value;
            firePropertyChange("background", old, cloudImage);
            revalidate();
            repaint();
        }
    }

    //EFFECTS: paints the image as the background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage bg = getBackgroundImage();
        if (bg != null) {
            int x = (getWidth() - bg.getWidth()) / 2;
            int y = (getHeight() - bg.getHeight()) / 2;
            g.drawImage(bg, x, y, this);
        }
    }

}

