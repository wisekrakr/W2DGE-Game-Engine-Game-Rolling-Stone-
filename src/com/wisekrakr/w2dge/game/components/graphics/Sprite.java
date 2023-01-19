package com.wisekrakr.w2dge.game.components.graphics;

import com.wisekrakr.w2dge.visual.assets.AssetManager;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite extends Component<Sprite> {

    /**
     * Image that will be loaded from a SpriteSheet
     */
    public BufferedImage image;
    /**
     * Image file name for when a single Sprite is created
     */
    public String fileName;
    /**
     * Sprite width & height
     */
    public Dimension dimension;
    /**
     * Is this sprite part of a SpriteSheet or not
     */
    public boolean isSubSprite = false;
    /**
     * Variables for when this Sprite has the isSubSprite boolean as true
     */
    public int row, column, index;

    /**
     * Sprite of a single file.
     * @param fileName image file name in resources folder
     */
    public Sprite(String fileName) {
        this.fileName = fileName;

        try {
            if (AssetManager.hasSprite(fileName)){
                throw new Exception("Asset already exists: " + fileName);
            }
            this.image = ImageIO.read(new File(fileName));

        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Could not create Sprite: " + fileName);
        }
    }

    /**
     * Sprite of a {@link com.wisekrakr.w2dge.visual.graphics.SpriteSheet}
     * @param image sprite image
     */
    public Sprite(BufferedImage image){
        this.image = image;
    }


    public Sprite(BufferedImage image, Dimension dimension, int row, int column, int index) {
        this.image = image;
        this.dimension = dimension;
        this.row = row;
        this.column = column;
        this.index = index;
        this.isSubSprite = true;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.image,
                (int)gameObject.transform.position.x, (int)gameObject.transform.position.y,
                gameObject.dimension.width, gameObject.dimension.height,
                null
        );
    }

    @Override
    public Component<Sprite> copy() {
        if (!isSubSprite){
            return new Sprite(this.image);
        }else {
            return new Sprite(this.image, this.dimension, this.row, this.column, this.index);
        }

    }
}
