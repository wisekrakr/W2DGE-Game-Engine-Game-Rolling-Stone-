package com.wisekrakr.w2dge.assets;

import com.wisekrakr.w2dge.game.components.graphics.Sprite;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    public static Map<String, Sprite>sprites = new HashMap<>();

    public static boolean hasSprite(String fileName){
        return AssetManager.sprites.containsKey(fileName);
    }

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
