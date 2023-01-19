package com.wisekrakr.util;

import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

public class AssetFinder {
    public enum ImageType {
        BACKGROUND, BUTTONS, CHARACTERS, OBSTACLES, OTHERS
    }

    public static String image(ImageType type, String imageName) {
        String image = null;
        switch (type) {

            case BACKGROUND:
                image = "assets/images/background/" + imageName;
                break;
            case BUTTONS:
                image = "assets/images/buttons/" + imageName;
                break;
            case CHARACTERS:
                image = "assets/images/characters/" + imageName;
                break;
            case OBSTACLES:
                image = "assets/images/obstacles/" + imageName;
                break;
            case OTHERS:
                image = "assets/images/others/" + imageName;
                break;
            default:
                System.err.println("Not an image type");
        }

        return image;
    }

    /**
     * Returns a sprite sheet from resources/ assets
     *
     * @param sheetName File name (not path)
     * @param width     tile width of a single sprite in sprite sheet
     * @param height    tile height of a single sprite in sprite sheet
     * @param columns   nr of columns within the sprite sheet
     * @param size      nr of sprites within the sprites sheet
     * @return {@link  SpriteSheet}
     */
    public static SpriteSheet spriteSheet(String sheetName, int width, int height, int columns, int size) {
        String path = "assets/spritesheets/" + sheetName;

        return new SpriteSheet(path, width, height, 2, columns, size);
    }
}
