package com.wisekrakr.w2dge.game;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.constants.ZIndexes;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.GameItemComponent;
import com.wisekrakr.w2dge.game.components.entities.GroundComponent;
import com.wisekrakr.w2dge.game.components.entities.PlayerComponent;
import com.wisekrakr.w2dge.game.components.graphics.BackgroundComponent;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.RigidBodyComponent;
import com.wisekrakr.w2dge.game.components.ui.ClickableComponent;
import com.wisekrakr.w2dge.game.components.ui.MenuItemComponent;
import com.wisekrakr.w2dge.game.components.ui.MenuItemControlComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.graphics.Renderer;

public class GameObjectFactory {

    /**
     * Creates a new GameObject - PLAYER <br>
     * Consists of {@link PlayerComponent},{@link RigidBodyComponent} components.
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
                new BoxBoundsComponent(dimension)
        );

        player.addComponent(new PlayerComponent());

        if (!isInEditingPhase) {
            player.addComponent(new RigidBodyComponent(new Vector2(GameConstants.PLAYER_SPEED, 0f)));
        }

        player.setNonSerializable();

        return player;
    }

    /**
     * Creates a new GameObject - GROUND <br>
     * Consists of the {@link GroundComponent} component. Adds it to the engine to act and draw.
     *
     * @return new ground Game object
     */
    public static GameObject ground() {
        GameObject ground = new GameObject(
                Tags.GROUND, new Transform(new Vector2(0, GameConstants.GROUND_Y)),
                new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT),
                ZIndexes.CENTER_PLUS,
                new GroundComponent(),
                new BoxBoundsComponent(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.GROUND_Y))
        );

        ground.setNonSerializable();

        return ground;
    }

    public static GameObject background(GameObject[] backgrounds, String filename, GameObject ground, boolean follow, int index) {
        BackgroundComponent bg = new BackgroundComponent(
                AssetFinder.image(AssetFinder.ImageType.BACKGROUND, filename),
                backgrounds,
                ground.getComponent(GroundComponent.class),
                follow
        );
        int x = (int) (index * bg.spriteComponent.dimension.width);
        int y = 0;

        return new GameObject(Tags.BACKGROUND, new Transform(new Vector2(x, y)), bg.dimension, ZIndexes.BACKGROUND, bg);
    }

    public static GameObject groundBg(GameObject[] backgrounds, String filename, GameObject ground, boolean follow, int index) {
        BackgroundComponent bg = new BackgroundComponent(
                AssetFinder.image(AssetFinder.ImageType.GROUNDS, filename),
                backgrounds,
                ground.getComponent(GroundComponent.class),
                follow
        );
        int x = (int) (index * bg.spriteComponent.dimension.width);
        int y = (int) bg.spriteComponent.dimension.height;

        return new GameObject(Tags.GROUND_BACKGROUND, new Transform(new Vector2(x, y)), bg.dimension,
                ZIndexes.BACKGROUND_PLUS, bg);
    }

    /**
     * Creates a new MenuItem - MENU_ITEM -> is non-serializable<br>
     * Contains the following Components: {@link SpriteComponent}, {@link MenuItemComponent}, {@link ClickableComponent},
     * {@link GameItemComponent}
     * <br>
     * This GameObject has the TAG -> GAME_ITEM, because the moment the menu item is placed on the scene it is no longer
     * a menu item but an object in the game --> named GAME_ITEM
     *
     * @param position
     * @param renderer
     * @param components
     * @return
     */
    public static GameObject menuItem(Vector2 position, Renderer renderer, Component<?>... components) {
        GameObject menuItem = new GameObject(
                Tags.GAME_ITEM,
                new Transform(position),
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                ZIndexes.BACK
        );

        renderer.partOfUI(menuItem);
        menuItem.setNonSerializable();

        SpriteComponent sprite = null;

        for (Component<?> component : components) {
            menuItem.addComponent(component);

            if (component instanceof SpriteComponent){
                sprite = (SpriteComponent) component.copy();
            }
        }

        menuItem.addComponent(new ClickableComponent());
        menuItem.addComponent(new GameItemComponent(
                menuItem.transform.copy(),
                menuItem.dimension.copy(),
                sprite)
        ); // add the GameItem component

        return menuItem;
    }

    /**
     * Creates a new MenuItem - MENU_ITEM -> is non-serializable<br>
     * Contains the following Components: {@link SpriteComponent}, {@link MenuItemComponent}, {@link ClickableComponent},
     * {@link GameItemComponent}
     * <br>
     * This GameObject has the TAG -> GAME_ITEM, because the moment the menu item is placed on the scene it is no longer
     * a menu item but an object in the game --> named GAME_ITEM
     *
     * @param position
     * @param dimension
     * @param renderer
     * @param components
     * @return
     */
    public static GameObject menuItem(Vector2 position, Dimension dimension, Renderer renderer, Component<?>... components) {
        GameObject menuItem = new GameObject(
                Tags.GAME_ITEM,
                new Transform(position),
                dimension,
                ZIndexes.FRONT
        );

        renderer.partOfUI(menuItem);
        menuItem.setNonSerializable();

        SpriteComponent sprite = null;

        for (Component<?> component : components) {
            menuItem.addComponent(component);

            if (component instanceof SpriteComponent){
                sprite = (SpriteComponent) component.copy();
            }
        }

        menuItem.addComponent(new ClickableComponent());
        menuItem.addComponent(new GameItemComponent(
                menuItem.transform.copy(),
                menuItem.dimension.copy(),
                sprite)
        ); // add the GameItem component

        return menuItem;
    }

    /**
     * Creates a new GameObject with name TAB -> object is non-serializable
     *
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
     * Consists of the following components: {@link MenuItemControlComponent}
     *
     * @return new cursor GameObject
     */
    public static GameObject mouserCursor() {
        return new GameObject(Tags.CURSOR, new Transform(new Vector2()),
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                ZIndexes.FRONT,
                new MenuItemControlComponent()
        );
    }

    /**
     * Creates a new GameObject - CURSOR<br>
     * Consists of the following components: {@link MenuItemControlComponent}
     * This cursor will be placed on top of a Game Object that has just been cleared (for Level Editing purposes)
     * @param parent Game object that was just cleared
     * @return new cursor GameObject
     */
    public static GameObject mouserCursor(GameObject parent) {
        return new GameObject(Tags.CURSOR, parent.transform.copy(),
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                parent.zIndex,
                parent.getComponent(MenuItemControlComponent.class)
        );
    }
}
