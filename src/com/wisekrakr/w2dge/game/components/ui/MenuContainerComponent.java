package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.assets.AssetManager;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MenuContainerComponent extends Component<MenuContainerComponent> {

    private final Scene currentScene;

    private final SpriteComponent containerBg;
    private final List<GameObject> menuItems;
    private final List<GameObject> tabs;
    private final Map<GameObject, List<GameObject>> tabsMap;

    private GameObject focusedTab = null;
    private GameObject focusedButton = null;

    public MenuContainerComponent() {
        this.currentScene = Screen.getScene();
        this.menuItems = new ArrayList<>();
        this.tabs = new ArrayList<>();
        this.tabsMap = new HashMap<>();
        this.containerBg = AssetManager.getSprite(AssetFinder.image(AssetFinder.ImageType.UI, "levelEditMenu.png"));

        start();
    }

    private void start() {
        for (int i = 0; i < AssetManager.tabsSheet.sprites.size(); i++) {
            SpriteComponent currentTab = AssetManager.tabsSheet.sprites.get(i);

            int x = (int) (GameConstants.TAB_OFFSET_X + (currentTab.column * GameConstants.TAB_WIDTH) +
                    (currentTab.column * GameConstants.TAB_HORIZONTAL_SPACING));
            int y = GameConstants.TAB_OFFSET_Y;

            GameObject tab = GameObjectFactory.menuItemTab(
                    new Vector2(x, y),
                    currentScene.getRenderer(),
                    new TabItemComponent(
                            new Transform(new Vector2(x, y)),
                            new Dimension(GameConstants.TAB_WIDTH, GameConstants.TAB_HEIGHT), this,
                            currentTab
                    )
            );

            tabs.add(tab);
            tabsMap.put(tab, new ArrayList<>());

            currentScene.addGameObjectToScene(tab);
        }

        this.focusedTab = this.tabs.get(0);
        this.focusedTab.getComponent(TabItemComponent.class).isSelected = true;

        addTabObjects();
    }

    @Override
    public void init() {
        for (GameObject gameObject : this.menuItems) {
            for (GameObject menuItem : tabsMap.get(gameObject)) {
                for (Component<?> c : menuItem.getAllComponents()) {
                    c.init();
                }
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        for (GameObject g : this.tabsMap.get(focusedTab)) {
            g.update(deltaTime);

            MenuItemComponent menuItem = g.getComponent(MenuItemComponent.class);
            if (g != focusedButton && menuItem.isSelected) {
                menuItem.isSelected = false;
            }
        }

        for (GameObject g : this.tabs) {
            TabItemComponent tabItem = g.getComponent(TabItemComponent.class);
            if (g != focusedTab && tabItem.isSelected) {
                tabItem.isSelected = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.containerBg.image, 0, GameConstants.MENU_CONTAINER_Y,
                (int) this.containerBg.dimension.width, (int) this.containerBg.dimension.height, null);

        for (GameObject menuItem : this.tabsMap.get(focusedTab)) {
            menuItem.render(g2d);
        }
    }

    private void addTabObjects() {
        // Creates a menu item component that contains sprite
        for (int i = 0; i < AssetManager.groundSheet.sprites.size(); i++) {
            SpriteComponent currentSpriteComponent = AssetManager.groundSheet.sprites.get(i);

            int x = (int) (GameConstants.BUTTON_OFFSET_X + (currentSpriteComponent.column * GameConstants.BUTTON_WIDTH) +
                    (currentSpriteComponent.column * GameConstants.BUTTON_HORIZONTAL_SPACING));
            int y = (int) (GameConstants.BUTTON_OFFSET_Y + (currentSpriteComponent.row * GameConstants.BUTTON_HEIGHT) +
                    (currentSpriteComponent.row * GameConstants.BUTTON_VERTICAL_SPACING));

            // Adding first tab container objects
            Vector2 position = new Vector2(x, y);

            // Menu item button
            MenuItemComponent menuItem = new MenuItemComponent(
                    new Transform(position), new Dimension(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT),
                    this, AssetManager.buttonSheet.sprites.get(0), AssetManager.buttonSheet.sprites.get(1)
            );

            // Menu item object within the button
            GameObject object = GameObjectFactory.menuItem(
                    position,
                    currentScene.getRenderer(),
                    currentSpriteComponent.copy(),
                    menuItem,
                    new BoxBoundsComponent(new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT)) // dimensions of Game Item
            );
            this.tabsMap.get(this.tabs.get(0)).add(object);

            // Adding second tab container objects
            if (i < AssetManager.smallBlockSheet.sprites.size()) {

                object = GameObjectFactory.menuItem(
                        position,
                        currentScene.getRenderer(),
                        AssetManager.smallBlockSheet.sprites.get(i),
                        menuItem.copy(),
                        new BoxBoundsComponent(new Dimension(GameConstants.TILE_WIDTH, 16))
                );

//                if (i == 0) {
//                    object.addComponent(new BoxBoundsComponent(new Dimension(GameConstants.TILE_WIDTH, 16)));
//                }

                this.tabsMap.get(this.tabs.get(1)).add(object);
            }

            // Adding fourth tab container objects
            if (i < AssetManager.spikesSheet.sprites.size()) {
                SpriteComponent spriteComponent = AssetManager.spikesSheet.sprites.get(i);
                object = GameObjectFactory.menuItem(
                        position,
                        currentScene.getRenderer(),
                        spriteComponent,
                        menuItem.copy(),
                        new TriangleBoundsComponent(new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT))
                );

                this.tabsMap.get(this.tabs.get(3)).add(object);
            }

            // Adding fifth tab container objects
            if (i == 0) {
                object = GameObjectFactory.menuItem(
                        new Vector2(x, y),
                        currentScene.getRenderer(),
                        AssetManager.bigSpritesSheet.sprites.get(i),
                        menuItem.copy(),
                        new BoxBoundsComponent(new Dimension(GameConstants.TILE_WIDTH * 2, 56))
                );

                this.tabsMap.get(tabs.get(4)).add(object);
            }

            // Adding sixth tab container objects
            if (i < AssetManager.portalSheet.sprites.size()) {
                object = GameObjectFactory.menuItem(
                        new Vector2(x, y),
                        currentScene.getRenderer(),
                        AssetManager.portalSheet.sprites.get(i),
                        menuItem.copy(),
                        new BoxBoundsComponent(new Dimension(GameConstants.TILE_WIDTH, (GameConstants.TILE_HEIGHT * 2)))
                );

                //todo portal should not collide with player (BoxBounds)
                //todo add portal component to portal object

                this.tabsMap.get(tabs.get(5)).add(object);
            }
        }
    }


    public void setFocusedTab(GameObject focusedTab) {
        this.focusedTab = focusedTab;
    }

    public void setFocusedButton(GameObject focusedButton) {
        this.focusedButton = focusedButton;
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
