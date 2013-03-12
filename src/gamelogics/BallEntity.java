package gamelogics;

import sprites.Sprite;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28.01.13
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class BallEntity extends Entity{

    public BallEntity(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public void setVelocity(int xVelocity, int yVelocity){
        setXVelocity(xVelocity);
        setYVelocity(yVelocity);
    }

    @Override
    public void move(int delta){
        float x = getX();
        float y = getY();
        float newX = x + delta * getXVelocity() / 1000;
        float newY = y + delta * getYVelocity() / 1000;
        setX(newX);
        setY(newY);
    }

    public void horizontalBounce(int objectSpeed, float friction){
        int newXVelocity = ((getXVelocity())) +  (int) (objectSpeed * friction);
        int newYVelocity = - (getYVelocity());
        setVelocity(newXVelocity, newYVelocity);
    }

    public void verticalBounce(int objectSpeed, float friction) {
        int newXVelocity = - (getXVelocity());
        int newYVelocity = getYVelocity() + (int) (objectSpeed * friction);
        setVelocity(newXVelocity, newYVelocity);
    }
}
