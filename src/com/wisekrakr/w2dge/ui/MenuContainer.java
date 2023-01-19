package com.wisekrakr.w2dge.ui;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MenuContainer extends Component<MenuContainer> {

    public List<GameObject> menuItems;

    public MenuContainer() {
        this.menuItems = new ArrayList<>();
        init();
    }

    @Override
    public void init() {
        SpriteSheet groundSprites = AssetFinder.spriteSheet(
                AssetFinder.ImageType.TILES,
                "spritesheet_tiles_blue_10x10.png",
                GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT, 10, 10
        );
        SpriteSheet buttonSprites = AssetFinder.spriteSheet(
                AssetFinder.ImageType.BUTTONS,
                "spritesheet_buttons.png",
                GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT, 2, 2
        );

        // Creates a menu item component that contains sprite
        for (int i = 0; i < groundSprites.sprites.size(); i++) {
            Sprite currentSprite = groundSprites.sprites.get(i);

            int x = GameConstants.BUTTON_OFFSET_X + (currentSprite.column * GameConstants.BUTTON_WIDTH) +
                    (currentSprite.column * GameConstants.BUTTON_HORIZONTAL_SPACING);
            int y = GameConstants.BUTTON_OFFSET_Y + (currentSprite.row * GameConstants.BUTTON_HEIGHT) +
                    (currentSprite.row * GameConstants.BUTTON_VERTICAL_SPACING);

            MenuItem menuItem = new MenuItem(new Transform(new Vector2(x,y)),
                    new Dimension(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT),
                    buttonSprites.sprites.get(0),buttonSprites.sprites.get(1)
            );

            GameObject object = new GameObject(
                    "Generated Menu Container", new Transform(new Vector2(x, y)),
                    new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                    currentSprite.copy(),
                    menuItem
            );

            menuItems.add(object);
        }

        start();
    }

    private void start(){
        for (GameObject menuItem: menuItems){
            for (Component<?>c: menuItem.getAllComponents()){
                c.init();
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        for (GameObject menuItem: menuItems){
            menuItem.update(deltaTime);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        for (GameObject menuItem: menuItems){
            menuItem.render(g2d);
        }
    }

    @Override
    public Component<MenuContainer> copy() {
        return null;
    }
}
