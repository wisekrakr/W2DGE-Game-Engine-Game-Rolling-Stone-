package com.wisekrakr.w2dge.game.components.graphics;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.assets.AssetManager;

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
     * @param fileName name of the file
     */
    public Sprite(BufferedImage image, String fileName){
        this.image = image;
        this.fileName = fileName;
    }


    public Sprite(BufferedImage image, Dimension dimension, int row, int column, int index, String fileName) {
        this.image = image;
        this.dimension = dimension;
        this.row = row;
        this.column = column;
        this.index = index;
        this.fileName = fileName;
        this.isSubSprite = true;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.image,
                (int)gameObject.transform.position.x, (int)gameObject.transform.position.y,
                (int)gameObject.dimension.width, (int)gameObject.dimension.height,
                null
        );
    }

    @Override
    public Component<Sprite> copy() {
        if (!isSubSprite){
            return new Sprite(this.image, this.fileName);
        }else {
            return new Sprite(this.image, this.dimension, this.row, this.column, this.index, this.fileName);
        }
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Sprite", tabSize));
        builder.append(addBooleanProperty("isSubSprite", isSubSprite, tabSize + 1, true, true));

        if (isSubSprite){
            builder.append(addStringProperty("FilePath", fileName, tabSize + 1, true, true));
            builder.append(addFloatProperty("tileWidth", dimension.width, tabSize + 1, true, true));
            builder.append(addFloatProperty("tileHeight", dimension.height, tabSize + 1, true, true));
            builder.append(addIntProperty("row", row, tabSize + 1, true, true));
            builder.append(addIntProperty("column", column, tabSize + 1, true, true));
            builder.append(addIntProperty("index", index, tabSize + 1, true, false));
            builder.append(closeObjectProperty(tabSize));

            return builder.toString();
        }

        builder.append(addStringProperty("FilePath", fileName, tabSize + 1, true, true));
        builder.append(addFloatProperty("tileWidth", dimension.width, tabSize + 1, true, true));
        builder.append(addFloatProperty("tileHeight", dimension.height, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static Sprite deserialize() {
        boolean isSubSprite = Parser.consumeBooleanProperty("isSubSprite");
        Parser.consume(',');
        String filePath = Parser.consumeStringProperty("FilePath");

        if (isSubSprite){
            Parser.consume(',');
            Parser.consumeFloatProperty("tileWidth");
            Parser.consume(',');
            Parser.consumeFloatProperty("tileHeight");
            Parser.consume(',');
            Parser.consumeIntProperty("row");
            Parser.consume(',');
            Parser.consumeIntProperty("column");
            Parser.consume(',');
            int index = Parser.consumeIntProperty("index");
            if (!AssetManager.hasSpriteSheet(filePath)){
                System.err.println("SpriteSheet: " + filePath + " has not been loaded yet");
                System.exit(-1);
            }

            Parser.consumeEndObjectProperty();

            return (Sprite) AssetManager.getSpriteSheet(filePath).sprites.get(index).copy();
        }

        Parser.consume(',');
        Parser.consumeIntProperty("tileWidth");
        Parser.consume(',');
        Parser.consumeIntProperty("tileHeight");

        if (!AssetManager.hasSprite(filePath)){
            System.err.println("Sprite: " + filePath + " has not been loaded yet");
            System.exit(-1);
        }
        Parser.consumeEndObjectProperty();

        return (Sprite) AssetManager.getSprite(filePath).copy();
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
