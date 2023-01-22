package com.wisekrakr.w2dge.visual.graphics;

import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.assets.AssetManager;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {
    public List<Sprite> sprites;
    public final int tileWidth;
    public final int tileHeight;
    public final int spacing;

    /**
     * Loops through all the sprites within a sprite sheet.
     * @param fileName Name of the sprite sheet file
     * @param tileWidth width of a tile in the sprite sheet
     * @param tileHeight height of a tile in the sprite sheet
     * @param spacing spacing between sprites in the sprite sheet
     * @param columns number of columns in the sprite sheet
     * @param size number of sprites in the sprite sheet
     */
    public SpriteSheet(String fileName, int tileWidth, int tileHeight, int spacing, int columns, int size) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.spacing = spacing;
        this.sprites = new ArrayList<>();

        Sprite parent = AssetManager.getSprite(fileName);
        int row = 0; // starting row in the sprite sheet
        int count = 0; // how many sprites are loaded so far

        while (count < size){
            for (int column = 0; column < columns; column++) {
                // top left corner of the image of the x-axis
                int imgX = (column * tileWidth) + (column * spacing); // sprite width + spacing between sprites
                // top left corner of the image of the y-axis
                int imgY = (row * tileHeight) + (row * spacing); // sprite height + spacing between sprites

                sprites.add(new Sprite(
                        parent.image.getSubimage(imgX, imgY, tileWidth, tileHeight),
                        new Dimension(tileWidth, tileHeight),
                        row, column, count, fileName));

                count++;

                if (column > size - 1){
                    break;
                }
            }
            row++;
        }

//        if (!AssetManager.hasSpriteSheet(fileName)){
//            AssetManager.addSpriteSheet(fileName, this);
//        }
        AssetManager.addSpriteSheet(fileName, this);
    }
}
