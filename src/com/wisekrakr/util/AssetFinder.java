package com.wisekrakr.util;

import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

public class AssetFinder {
    public enum ImageType {
        BACKGROUND, BUTTONS, PLAYER, OBSTACLES, OTHERS, TILES, GROUNDS, UI
    }

    public static String image(ImageType type, String imageName) {
        StringBuilder path = new StringBuilder("assets/images/");
        switch (type) {
            case BACKGROUND -> path.append("background/");
            case GROUNDS -> path.append("grounds/");
            case BUTTONS -> path.append("buttons/");
            case PLAYER -> path.append("player/");
            case OBSTACLES -> path.append("obstacles/");
            case TILES -> path.append("tiles/");
            case UI -> path.append("ui/");
            case OTHERS -> path.append("others/");
            default -> System.err.println("Not an image type");
        }

        return path.append(imageName).toString();
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
    public static SpriteSheet spriteSheet(ImageType type, String sheetName, Dimension dimension, int columns, int size) {
        StringBuilder path = new StringBuilder("assets/spritesheets/");

        switch (type) {
            case BACKGROUND -> path.append("background/");
            case BUTTONS -> path.append("buttons/");
            case PLAYER -> path.append("player/");
            case OBSTACLES -> path.append("obstacles/");
            case UI -> path.append("ui/");
            case OTHERS -> path.append("others/");
            case TILES -> path.append("tiles/");
            default -> System.err.println("Not an image type for sprite sheet");
        }
        SpriteSheet spriteSheet = new SpriteSheet(path.append(sheetName).toString(), dimension, 2, columns, size);
        spriteSheet.path = path.append(sheetName).toString();
        return spriteSheet;
    }
}
