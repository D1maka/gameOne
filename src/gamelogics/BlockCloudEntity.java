package gamelogics;

import sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 28.01.13
 * Time: 16:34
 * To change this template use File | Settings | File Templates.
 */
public class BlockCloudEntity extends Entity {

    private ArrayList<BlockEntity> blocks;

    public BlockCloudEntity(int width, int elements, Sprite blockSprite) {
        super();
        blocks = new ArrayList<BlockEntity>(elements);
        int x = 0;
        int y = 0;
        for (int el = elements; el >= 0; el--){
            blocks.add(new BlockEntity(x, y, blockSprite));
            x += blockSprite.getWidth() + 1;
            if (x >= width){
                y += blockSprite.getHeight() + 1;
                x = 0;
            }
        }
    }

    public void draw(Graphics g){
        for (BlockEntity block: blocks){
            block.draw(g);
        }
    }

    public ArrayList<BlockEntity> getBlocks(){
        return blocks;
    }

    public void destroyBlock(BlockEntity block){
        blocks.remove(block);
    }
}
