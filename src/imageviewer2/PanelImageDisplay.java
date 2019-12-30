package imageviewer2;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelImageDisplay extends JPanel implements ImageDisplay {
    private Image currentImage;
    private BufferedImage image;

    @Override
    public void show(Image image) {
        this.currentImage = image;
        this.image = image();
        repaint();
    }
    
    public void paint(Graphics g) {
        Rectangle rect = center(scale(image.getWidth(), image.getHeight()));
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, rect.x, rect.y, rect.width, rect.height, this);
    }

    @Override
    public Image getCurrentImage() {
        return currentImage;
    }

    private BufferedImage image() {
        try {
            return ImageIO.read(new ByteArrayInputStream(currentImage.data()));
        } catch (IOException ex) {
            return null;
        }
        
    }

    private Rectangle calculate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    private Rectangle center(Dimension size) {
        return new Rectangle(
                (getWidth()-size.width)/2,
                (getHeight()-size.height)/2,
                size.width,
                size.height);
    }

    private Dimension scale(int width, int height) {
        double scaleWidth = width / (double)getWidth();
        double scaleHeight = height / (double)getHeight();
        double scale = Math.max(scaleWidth, scaleHeight);
        if (scale > 1) {
            width = (int) (width / scale);
            height = (int) (height / scale);
        }
        return new Dimension(width, height);
    }
    
}
