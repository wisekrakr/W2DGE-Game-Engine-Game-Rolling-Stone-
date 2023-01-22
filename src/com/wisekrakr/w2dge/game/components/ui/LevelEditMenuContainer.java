package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class LevelEditMenuContainer extends Component<LevelEditMenuContainer> {

    public List<GameObject> menuItems;

    public LevelEditMenuContainer() {
        this.menuItems = new ArrayList<>();
        init();
    }

    @Override
    public void init() {
        SpriteSheet groundSprites = AssetFinder.spriteSheet(
                AssetFinder.ImageType.TILES,
                "spritesheet_tiles_blue_10x10.png",
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                10, 10
        );
        SpriteSheet buttonSprites = AssetFinder.spriteSheet(
                AssetFinder.ImageType.BUTTONS,
                "spritesheet_buttons.png",
                new Dimension(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT),
                2, 2
        );

        // Creates a menu item component that contains sprite
        for (int i = 0; i < groundSprites.sprites.size(); i++) {
            Sprite currentSprite = groundSprites.sprites.get(i);

            int x = (int) (GameConstants.BUTTON_OFFSET_X + (currentSprite.column * GameConstants.BUTTON_WIDTH) +
                    (currentSprite.column * GameConstants.BUTTON_HORIZONTAL_SPACING));
            int y = (int) (GameConstants.BUTTON_OFFSET_Y + (currentSprite.row * GameConstants.BUTTON_HEIGHT) +
                    (currentSprite.row * GameConstants.BUTTON_VERTICAL_SPACING));

            menuItems.add(GameObjectFactory.block((Sprite) currentSprite.copy(), x, y, buttonSprites));
        }

        start();
    }

    private void start() {
        for (GameObject menuItem : menuItems) {
            for (Component<?> c : menuItem.getAllComponents()) {
                c.init();
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        for (GameObject menuItem : menuItems) {
            menuItem.update(deltaTime);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        for (GameObject menuItem : menuItems) {
            menuItem.render(g2d);
        }
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
