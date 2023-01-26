package com.wisekrakr.w2dge.game;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.constants.ZIndexes;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.Ground;
import com.wisekrakr.w2dge.game.components.entities.Player;
import com.wisekrakr.w2dge.game.components.graphics.Background;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.game.components.physics.BoxBounds;
import com.wisekrakr.w2dge.game.components.physics.RigidBody;
import com.wisekrakr.w2dge.game.components.regions.SnapToGrid;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.graphics.Renderer;

public class GameObjectFactory {

    /**
     * Creates a new GameObject - PLAYER <br>
     * Consists of {@link Player},{@link RigidBody} components.
     *
     * @param position         {@link Vector2} position of the game object
     * @param isInEditingPhase if false, this Game Object will have full motion. If false, only visual.
     * @return new player Game object
     */
    public static GameObject player(Vector2 position, boolean isInEditingPhase) {
        Dimension dimension = new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        GameObject player = new GameObject(
                Tags.PLAYER,
                new Transform(new Vector2(position.x, position.y)),
                dimension,
                ZIndexes.CENTER,
                new BoxBounds(dimension)
        );

        player.addComponent(new Player());

        if (!isInEditingPhase) {
            player.addComponent(new RigidBody(new Vector2(GameConstants.PLAYER_SPEED, 0f)));
        }

        player.setNonSerializable();

        return player;
    }

    /**
     * Creates a new GameObject - GROUND <br>
     * Consists of the {@link Ground} component. Adds it to the engine to act and draw.
     *
     * @return new ground Game object
     */
    public static GameObject ground() {
        GameObject ground = new GameObject(
                Tags.GROUND, new Transform(new Vector2(0, GameConstants.GROUND_Y)),
                new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT),
                ZIndexes.CENTER_PLUS,
                new Ground(),
                new BoxBounds(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.GROUND_Y))
        );

        ground.setNonSerializable();

        return ground;
    }

    public static GameObject background(GameObject[] backgrounds, String filename, GameObject ground, boolean follow, int index) {
        Background bg = new Background(
                AssetFinder.image(AssetFinder.ImageType.BACKGROUND, filename),
                backgrounds,
                ground.getComponent(Ground.class),
                follow
        );
        int x = (int) (index * bg.sprite.dimension.width);
        int y = 0;

        return new GameObject(Tags.BACKGROUND, new Transform(new Vector2(x, y)), bg.dimension, ZIndexes.BACKGROUND, bg);
    }

    public static GameObject groundBg(GameObject[] backgrounds, String filename, GameObject ground, boolean follow, int index) {
        Background bg = new Background(
                AssetFinder.image(AssetFinder.ImageType.GROUNDS, filename),
                backgrounds,
                ground.getComponent(Ground.class),
                follow
        );
        int x = (int) (index * bg.sprite.dimension.width);
        int y = (int) bg.sprite.dimension.height;

        return new GameObject(Tags.GROUND_BACKGROUND, new Transform(new Vector2(x, y)), bg.dimension,
                ZIndexes.BACKGROUND_PLUS, bg);
    }

    /**
     * Creates a new MenuItem - MENU_ITEM -> is non-serializable<br>
     * Contains the following Components: {@link Sprite}, {@link com.wisekrakr.w2dge.game.components.ui.MenuItem},
     * <br>
     * This GameObject has the TAG -> GAME_ITEM, because the moment the menu item is placed on the scene it is no longer
     * a menu item but an object in the game --> named GAME_ITEM
     *
     * @param position
     * @param renderer
     * @param components
     * @return
     */
    public static GameObject menuItem(Vector2 position, Renderer renderer, Component<?>...components) {
        GameObject menuItem = new GameObject(
                Tags.GAME_ITEM,
                new Transform(position),
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                ZIndexes.BACK
        );

        renderer.partOfUI(menuItem);
        menuItem.setNonSerializable();

        for (Component<?> component : components) {
            menuItem.addComponent(component);
        }

        return menuItem;
    }

    /**
     * Creates a new GameObject with name TAB -> object is non-serializable
     *
     * @param sprite
     * @param position
     * @param renderer
     * @return
     */
    public static GameObject menuItemTab(Vector2 position, Renderer renderer,
                                         Component<?>... components) {
        GameObject tab = new GameObject(Tags.TAB, new Transform(position),
                new Dimension(GameConstants.TAB_WIDTH, GameConstants.TAB_HEIGHT), ZIndexes.FRONT);

        renderer.partOfUI(tab);
        tab.setNonSerializable();

        for (Component<?> component : components) {
            tab.addComponent(component);
        }

        return tab;
    }

    /**
     * Creates a new GameObject - CURSOR<br>
     * Consists of the following components: {@link SnapToGrid}, {@link Sprite}
     *
     * @return new cursor GameObject
     */
    public static GameObject mouserCursor() {
        return new GameObject(Tags.CURSOR, new Transform(new Vector2()),
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                ZIndexes.FRONT,
                new SnapToGrid()
        );
    }
}
