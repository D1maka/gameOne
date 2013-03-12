package gamelogics;

import sprites.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28.01.13
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class BeatEntity extends Entity {

    public BeatEntity(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public void setVelocity(int velocity){
        setXVelocity(velocity);
    }

    public int getVelocity(){
        return getXVelocity();
    }

    public void move(int delta){
        float x = getX();
        float newX = x + delta * getXVelocity() / 1000;
        setX(newX);
    }
}
