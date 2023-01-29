package com.wisekrakr.w2dge.visual.assets;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetManager {
    public static Map<String, SpriteComponent> sprites = new HashMap<>();
    public static Map<String, SpriteSheet> spritesheets = new HashMap<>();

    public static SpriteSheet layerOne;
    public static SpriteSheet layerTwo;
    public static SpriteSheet layerThree;
    public static SpriteSheet spikesSheet;
    public static SpriteSheet buttonSheet;
    public static SpriteSheet bigSpritesSheet;
    public static SpriteSheet smallBlockSheet;
    public static SpriteSheet groundSheet;
    public static SpriteSheet tabsSheet;
    public static SpriteSheet portalSheet;
    public static SpriteSheet fireworkSheet;


    public static boolean hasSprite(String fileName) {
        return AssetManager.sprites.containsKey(new File(fileName).getAbsolutePath());
    }

    public static boolean hasSpriteSheet(String fileName) {
        return AssetManager.spritesheets.containsKey(new File(fileName).getAbsolutePath());
    }

    /**
     * Get a sprite out of the sprite pool. If Sprite is not yet in the pool, create it and throw it in the pool.
     *
     * @param fileName name of the file to load
     * @return {@link SpriteComponent}
     */
    public static SpriteComponent getSprite(String fileName) {
        File file = new File(fileName);

        if (!AssetManager.hasSprite(file.getAbsolutePath())) {
            SpriteComponent spriteComponent = new SpriteComponent(file.getAbsolutePath());
            // TODO i don't like this sitting here - Dimension should not be initialized here
            spriteComponent.dimension = new Dimension(spriteComponent.image.getWidth(), spriteComponent.image.getHeight());
            AssetManager.addSprite(fileName, spriteComponent);
        }
        return AssetManager.sprites.get(file.getAbsolutePath());
    }

    /**
     * Get a sprite sheet out of the sprite sheet pool. If {@link  SpriteSheet} is not yet in the pool, create it and throw it in the pool.
     *
     * @param fileName name of the file to load
     * @return {@link SpriteSheet}
     */
    public static SpriteSheet getSpriteSheet(String fileName) {
        File file = new File(fileName);

        if (AssetManager.hasSpriteSheet(file.getAbsolutePath())) {
            return AssetManager.spritesheets.get(file.getAbsolutePath());
        } else {
            System.err.println("Sprite Sheet: " + fileName + " does not exist");
        }

        return null;
    }

    /**
     * If the sprite is not yet added to the AssetManager sprite pool, add it.
     *
     * @param fileName The absolute path to the image file.
     * @param spriteComponent   Sprite to get the image from
     */
    public static void addSprite(String fileName, SpriteComponent spriteComponent) {
        File file = new File(fileName);

        // If the sprite is not yet added to the AssetManager sprite pool
        if (!AssetManager.hasSprite(file.getAbsolutePath())) {
            AssetManager.sprites.put(file.getAbsolutePath(), spriteComponent);
        } else {
            System.err.println("Asset Manager already has asset: " + file.getAbsolutePath());
        }
    }

    /**
     * If the sprite sheet is not yet added to the AssetManager sprite pool, add it.
     *
     * @param fileName    name of the file of the sprite sheet
     * @param spriteSheet Sprite sheet to put into spritesheets Map
     */
    public static void addSpriteSheet(String fileName, SpriteSheet spriteSheet) {
        File file = new File(fileName);

        // If the sprite is not yet added to the AssetManager sprite pool
        if (!AssetManager.hasSpriteSheet(file.getAbsolutePath())) {
            AssetManager.spritesheets.put(file.getAbsolutePath(), spriteSheet);
        } else {
            System.err.println("Asset Manager already has sprite sheet asset: " + file.getAbsolutePath());
        }
    }

    public static void clearSprites() {
        AssetManager.sprites.clear();
    }

    public static void loadAllSpriteSheets() {
        layerOne = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerOne.png",
                new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT), 13, 13 * 5);
        layerTwo = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerTwo.png",
                new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT), 13, 13 * 5);
        layerThree = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerThree.png",
                new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT), 13, 13 * 5);

//        groundSheet = new SpriteSheet("assets/spritesheets/tiles/spritesheet_tiles_11x22.png",
//                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT), 4,
//                11, 22);
//        groundSheet = AssetFinder.spriteSheet(
//                AssetFinder.ImageType.TILES,
//                "spritesheet_tiles_blue_10x10.png",
//                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
//                10, 10
//        );
        groundSheet = AssetFinder.spriteSheet(
                AssetFinder.ImageType.TILES,
                "ground.png",
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                6, 12
        );

        buttonSheet = AssetFinder.spriteSheet(
                AssetFinder.ImageType.BUTTONS,
                "spritesheet_buttons.png",
                new Dimension(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT),
                2, 2
        );

        tabsSheet = AssetFinder.spriteSheet(
                AssetFinder.ImageType.UI,
                "tabs.png",
                new Dimension(GameConstants.TAB_WIDTH, GameConstants.TAB_HEIGHT),
                6, 6
        );

        spikesSheet = AssetFinder.spriteSheet(
                AssetFinder.ImageType.OBSTACLES,
                "spikes.png",
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                4, 4
        );

        bigSpritesSheet = AssetFinder.spriteSheet(
                AssetFinder.ImageType.OBSTACLES,
                "bigSprites.png",
                new Dimension(GameConstants.TILE_WIDTH * 2, GameConstants.TILE_HEIGHT * 2),
                2, 2
        );

        smallBlockSheet = AssetFinder.spriteSheet(
                AssetFinder.ImageType.UI,
                "smallBlocks.png",
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                6, 1
        );

        portalSheet = AssetFinder.spriteSheet(
                AssetFinder.ImageType.OTHERS,
                "portal.png",
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT * 2),
                2, 2
        );

        fireworkSheet = AssetFinder.spriteSheet(
                AssetFinder.ImageType.OTHERS,
                "yellow_firework.png",
                new Dimension(GameConstants.TILE_WIDTH * 2, GameConstants.TILE_HEIGHT * 2),
                6, 10
        );
    }

}
