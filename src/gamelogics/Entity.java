package gamelogics;

import sprites.Sprite;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 27.01.13
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public class Entity {

    private float x;
    private float y;
    private int[] velocity;
    private Sprite sprite;

    public Entity(){
        velocity = new int[]{0, 0};
        x = 0;
        y = 0;
    }

    public Entity(float x, float y, Sprite sprite){
        this.x = x;
        this.y = y;
        velocity = new int[]{0, 0};
        this.sprite = sprite;
    }

    public void move(int delta){
    }

    public void draw(Graphics g){
        sprite.draw(g, x, y);
    }

    public Rectangle getBounds(){
        return new Rectangle((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    protected int getXVelocity(){
        return velocity[0];
    }

    protected int getYVelocity(){
        return velocity[1];
    }

    protected Sprite getSprite() {
        return sprite;
    }

    protected void setX(float x) {
        this.x = x;
    }

    protected void setY(float y) {
        this.y = y;
    }

    protected void setXVelocity(int xVelocity){
        velocity[0] = xVelocity;
    }

    protected void setYVelocity(int yVelocity){
        velocity[1] = yVelocity;
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
