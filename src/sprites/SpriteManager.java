package sprites;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;


/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.01.13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class SpriteManager {

    public static final String BEAT_SPRITE = "Beat.jpg";
    public static final String BLOCK_SPRITE = "Block.jpg";
    public static final String BALL_SPRITE = "Ball.gif";
    public static final String WON_GAME_SPRITE = "WonGame.gif";
    public static final String LOST_GAME_SPRITE = "LostGame.gif";

    private static SpriteManager instance;

    private HashMap<String, Sprite> sprites;

    private SpriteManager(){
        sprites = new HashMap<String, Sprite>();
    }

    public static SpriteManager getInstance(){
        if (instance == null){
            instance = new SpriteManager();
        }
        return instance;
    }

    public Sprite getSprite(String name){
        if(sprites.get(name) == null){
            Sprite sprite = new Sprite(getImage(name));
            sprites.put(name, sprite);
            return sprite;
        } else {
            return sprites.get(name);
        }
    }

    private Image getImage(String name){
        Image sourceImg, acceleratedImg;

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        try {
            sourceImg = ImageIO.read(new File(name));
            acceleratedImg = gc.createCompatibleImage(sourceImg.getWidth(null), sourceImg.getHeight(null), Transparency.BITMASK);
            acceleratedImg.getGraphics().drawImage(sourceImg, 0, 0, null);
            return acceleratedImg;
        } catch (IOException e) {
            e.printStackTrace();

            // Add default pic for not existing sprites
            return null;
        }

    }
}
