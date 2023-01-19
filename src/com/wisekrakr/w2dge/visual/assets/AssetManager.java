package com.wisekrakr.w2dge.visual.assets;

import com.wisekrakr.w2dge.game.components.graphics.Sprite;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    public static Map<String, Sprite>sprites = new HashMap<>();

    public static boolean hasSprite(String fileName){
        return AssetManager.sprites.containsKey(fileName);
    }

    public static boolean hasSprite(String fileName){
        return AssetManager.sprites.containsKey(fileName);
    }

    /**
     * Get a sprite out of the sprite pool. If Sprite is not yet in the pool, create it and throw it in the pool.
     * @param fileName name of the file to load
     * @return {@link Sprite}
     */
    public static Sprite getSprite(String fileName){
        File file = new File(fileName);

        if (!AssetManager.hasSprite(fileName)) {

            Sprite sprite = new Sprite(file.getAbsolutePath());
            AssetManager.addSprite(fileName, sprite);

        }
        return AssetManager.sprites.get(file.getAbsolutePath());
    }

    /**
     *  If the sprite is not yet added to the AssetManager sprite pool, add it.
     * @param fileName The absolute path to the image file.
     * @param sprite Sprite to place to image on.
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
}
