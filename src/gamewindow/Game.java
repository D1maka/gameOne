package gamewindow;

import gamelogics.*;
import sprites.Sprite;
import sprites.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 25.01.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int BEAT_START_X = 380;
    public static final int BEAT_START_Y = 580;
    public static final int BALL_START_X = 390;
    public static final int BALL_START_Y = 560;
    private static final int BEAT_VELOCITY = 47;
    private static final int BALL_X_VELOCITY = -25;
    private static final int BALL_Y_VELOCITY = 25;
    private static final float BEAT_FRICTION = 0.001f;

    private BufferStrategy bufferStrategy;
    private Boolean isRunning;
    private ArrayList<Entity> entities;
    private SpriteManager spriteManager;
    private Sprite finalScreen;

    private BeatEntity beat;
    private BallEntity ball;
    private BlockCloudEntity cloud;

    private boolean rightPressed;
    private boolean leftPressed;
    private boolean startPressed;
    private boolean gameFinnished;
    private boolean gameLost;

    public Game(){
        isRunning = false;
        rightPressed = false;
        leftPressed = false;
        startPressed = false;
        entities = new ArrayList<Entity>();
        spriteManager = SpriteManager.getInstance();
        addKeyListener(new KeyInputHandler());
        JFrame frame = new JFrame("Game One");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);
        setFocusable(true);
        requestFocusInWindow();

        setBounds(0, 0, WIDTH, HEIGHT);
        panel.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        setIgnoreRepaint(true);
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();
    }

    public void run(){
        final double GAME_HERTZ = 40.0;
        //final double TARGET_FPS = 60.0;
        final double TIME_BETWEEN_UPDATES = 1000 / GAME_HERTZ;
        final int MAX_FRAMESKIP = 5;

        long lastTime = System.currentTimeMillis();
        long currentTime = 0;
        long step = (long) TIME_BETWEEN_UPDATES;
        long sleepTime = 0;
        startInitialization();
        isRunning = true;

        while (isRunning){
            //currentTime = System.currentTimeMillis();
            //step = currentTime - lastTime;
            //lastTime = currentTime;
            if (startPressed) {
                update(step);
            }
            //step += TIME_BETWEEN_UPDATES;
            if (!gameFinnished) {
                render();
            } else {
                finishGame();
                if (startPressed) {
                    startInitialization();
                }
            }
            //sleepTime = lastTime - System.currentTimeMillis();

            if (sleepTime > 0)  {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void render(){
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (Entity entity: entities){
            entity.draw(g);
        }

        g.dispose();
        bufferStrategy.show();
    }

    public void update(long delta){
        if (rightPressed && checkBeatOutOfBounds() != 1){
            beat.move((int)delta);
        }
        if (leftPressed && checkBeatOutOfBounds() != 0){
            beat.move(-(int)delta);
        }
        ball.move((int)delta);
        resolveBallCollision();
        checkWinCase();
    }

    public void startInitialization(){
        gameFinnished = false;
        startPressed = false;
        entities.clear();
        beat = new BeatEntity(BEAT_START_X, BEAT_START_Y, spriteManager.getSprite(SpriteManager.BEAT_SPRITE));
        ball = new BallEntity(200, 200, spriteManager.getSprite(SpriteManager.BALL_SPRITE));
        cloud = new BlockCloudEntity(800, 40, spriteManager.getSprite(SpriteManager.BLOCK_SPRITE));
        beat.setVelocity(BEAT_VELOCITY);
        ball.setVelocity(BALL_X_VELOCITY, BALL_Y_VELOCITY);
        entities.add(beat);
        entities.add(ball);
        entities.add(cloud);
    }

    private void resolveBallCollision(){
        Rectangle ballBounds = ball.getBounds();
        if (ballBounds.intersectsLine(0, 0, 0, HEIGHT) || ballBounds.intersectsLine(WIDTH, 0, WIDTH, HEIGHT)){
            ball.verticalBounce(0, 0);
        }
        if (ballBounds.intersectsLine(0, 0, WIDTH, 0)){
            ball.horizontalBounce(0, 0);
        }
        if (ballBounds.intersectsLine(0, HEIGHT, WIDTH, HEIGHT)) {
            finalScreen = spriteManager.getSprite(SpriteManager.LOST_GAME_SPRITE);
            startPressed = false;
            gameFinnished = true;
        }
        if (ballBounds.intersects(beat.getBounds())) {
            ball.horizontalBounce(BEAT_VELOCITY, BEAT_FRICTION);
        }
        // OPTIMISE IT!
        for (Iterator<BlockEntity> blocksIter = cloud.getBlocks().iterator(); blocksIter.hasNext();) {
            BlockEntity block = blocksIter.next();
            if (ballBounds.intersects(block.getBounds())) {
                Rectangle blockBounds = block.getBounds();
                if (ballBounds.intersectsLine(blockBounds.getX(), blockBounds.getY(), blockBounds.getX() + blockBounds.getWidth(),
                   blockBounds.getY()) ||
                   ballBounds.intersectsLine(blockBounds.getX(), blockBounds.getY() + blockBounds.getHeight(),
                   blockBounds.getX() + blockBounds.getWidth(), blockBounds.getY() + blockBounds.getHeight())){
                    ball.horizontalBounce(0, 0);
                    blocksIter.remove();
                    break;
                }
                if (ballBounds.intersectsLine(blockBounds.getX(), blockBounds.getY(), blockBounds.getX(),
                    blockBounds.getY() + blockBounds.getHeight()) ||
                    ballBounds.intersectsLine(blockBounds.getX() + blockBounds.getWidth(), blockBounds.getY(),
                    blockBounds.getX() + blockBounds.getWidth(), blockBounds.getY() + blockBounds.getHeight())){
                    ball.verticalBounce(0, 0);
                    blocksIter.remove();
                    break;
                }
            }

        }
    }

    private int checkBeatOutOfBounds(){
        if (beat.getBounds().contains(0, BEAT_START_Y)){
            return 0;
        }
        if (beat.getBounds().contains(WIDTH, BEAT_START_Y)){
            return 1;
        }
        return 2;
    }

    private void checkWinCase(){
        if (cloud.getBlocks().isEmpty()) {
            finalScreen = spriteManager.getSprite(SpriteManager.WON_GAME_SPRITE);
            startPressed = false;
            gameFinnished = true;
        }
    }

    private void finishGame() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if (finalScreen != null) {
            finalScreen.draw(g, 200, 200);
        }
        g.dispose();
        bufferStrategy.show();
    }

    private class KeyInputHandler extends KeyAdapter implements KeyListener{
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }
        }

        public void keyReleased(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                rightPressed = false;
            }
        }

        public void keyTyped(KeyEvent e){
            if (e.getKeyChar() == 10){
                startPressed = true;
            }
            if (e.getKeyChar() == 27){
                System.exit(0);
            }
        }
    }


    public static void main(String[] args){
        Game game = new Game();
        game.run();
    }
}
