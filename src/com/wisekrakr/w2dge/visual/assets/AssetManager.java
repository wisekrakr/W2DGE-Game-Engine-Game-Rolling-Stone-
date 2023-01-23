package com.wisekrakr.w2dge.visual.assets;

import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    public static Map<String, Sprite>sprites = new HashMap<>();
    public static Map<String, SpriteSheet>spritesheets = new HashMap<>();

    public static boolean hasSprite(String fileName){
        return AssetManager.sprites.containsKey(new File(fileName).getAbsolutePath());
    }

    public static boolean hasSpriteSheet(String fileName){
        return AssetManager.spritesheets.containsKey(new File(fileName).getAbsolutePath());
    }

    /**
     * Get a sprite out of the sprite pool. If Sprite is not yet in the pool, create it and throw it in the pool.
     * @param fileName name of the file to load
     * @return {@link Sprite}
     */
    public static Sprite getSprite(String fileName){
        File file = new File(fileName);

        if (!AssetManager.hasSprite(file.getAbsolutePath())) {
            Sprite sprite = new Sprite(file.getAbsolutePath());
            //TODO i don't like this sitting here - Dimension should not be initialized here
            sprite.dimension = new Dimension(sprite.image.getWidth(), sprite.image.getHeight());
            AssetManager.addSprite(fileName, sprite);
        }
        return AssetManager.sprites.get(file.getAbsolutePath());
    }

    /**
     * Get a sprite sheet out of the sprite sheet pool. If {@link  SpriteSheet} is not yet in the pool, create it and throw it in the pool.
     * @param fileName name of the file to load
     * @return {@link SpriteSheet}
     */
    public static SpriteSheet getSpriteSheet(String fileName){
        File file = new File(fileName);

        if (AssetManager.hasSpriteSheet(file.getAbsolutePath())) {
            return AssetManager.spritesheets.get(file.getAbsolutePath());
        }else {
            System.err.println("Sprite Sheet: " + fileName + " does not exist");
        }

        return null;
    }

    /**
     *  If the sprite is not yet added to the AssetManager sprite pool, add it.
     * @param fileName The absolute path to the image file.
     * @param sprite Sprite to get the image from
     */
    public static void addSprite(String fileName, Sprite sprite){
        File file = new File(fileName);

        // If the sprite is not yet added to the AssetManager sprite pool
        if(!AssetManager.hasSprite(file.getAbsolutePath())){
            AssetManager.sprites.put(file.getAbsolutePath(), sprite);
        }else{
            System.err.println("Asset Manager already has asset: " + file.getAbsolutePath());
        }
    }

    /**
     * If the sprite sheet is not yet added to the AssetManager sprite pool, add it.
     * @param fileName name of the file of the sprite sheet
     * @param tileWidth width of a tile in the sprite sheet
     * @param tileHeight height of a tile in the sprite sheet
     * @param spacing spacing of tiles in the sprite sheet
     * @param columns number of columns in the sprite sheet
     * @param size number of total objects in the sprite sheet
     */
    public static void addSpriteSheet(String fileName,  SpriteSheet spriteSheet){
        File file = new File(fileName);

        // If the sprite is not yet added to the AssetManager sprite pool
        if(!AssetManager.hasSpriteSheet(file.getAbsolutePath())){
            AssetManager.spritesheets.put(file.getAbsolutePath(),spriteSheet);
        }else{
            System.err.println("Asset Manager already has sprite sheet asset: " + file.getAbsolutePath());
        }
    }
}
