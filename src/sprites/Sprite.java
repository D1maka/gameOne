package sprites;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.01.13
 * Time: 16:18
 * To change this template use File | Settings | File Templates.
 */
public class Sprite {
    private Image image;

    public Sprite(Image image){
        this.image = image;
    }

    public int getWidth(){
        return image.getWidth(null);
    }

    public int getHeight(){
        return image.getHeight(null);
    }

    public void draw(Graphics g, float x, float y){
        g.drawImage(image, (int) x, (int) y, null);
    }
}
