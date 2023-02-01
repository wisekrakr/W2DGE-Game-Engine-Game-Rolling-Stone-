package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.PlayerState;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.PortalComponent;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.game.components.physics.BoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriggerComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.assets.AssetManager;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.awt.*;
import java.util.List;
import java.util.*;


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

        // Adding first tab container objects
        addingTabContainerWithMenuItems(
                AssetManager.groundSheet.sprites,
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                0, BoundsComponent.BoundsType.BOX
        );

        // Adding second tab container objects
        addingTabContainerWithMenuItems(
                AssetManager.smallBlockSheet.sprites,
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                1, BoundsComponent.BoundsType.BOX
        );

        // Adding third tab container objects


        // Adding fourth tab container objects
        addingTabContainerWithMenuItems(
                AssetManager.spikesSheet.sprites,
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                3, BoundsComponent.BoundsType.TRIANGLE
        );

        // Adding fifth tab container objects
        addingTabContainerWithMenuItems(
                AssetManager.bigSpritesSheet.sprites,
                new Dimension(GameConstants.TILE_WIDTH * 2, GameConstants.TILE_HEIGHT + (GameConstants.TILE_HEIGHT / 2)),
                4, BoundsComponent.BoundsType.BOX
        );

        // Adding sixth tab container objects
        addingTabContainerWithMenuItems(
                AssetManager.portalSheet.sprites,
                new Dimension(GameConstants.TILE_WIDTH, (GameConstants.TILE_HEIGHT * 2)),
                5, BoundsComponent.BoundsType.BOX,
                new TriggerComponent()
        );
    }


    private void addingTabContainerWithMenuItems(List<SpriteComponent> sprites, Dimension dimension,
                                                 int tabNr, BoundsComponent.BoundsType type, Component<?>... components) {

        for (SpriteComponent currentSpriteComponent : sprites) {
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

            GameObject object = GameObjectFactory.menuItem(
                    position,
                    dimension,
                    currentScene.getRenderer(),
                    currentSpriteComponent,
                    menuItem.copy(),
                    type.equals(BoundsComponent.BoundsType.BOX) ?
                            new BoxBoundsComponent(new Dimension(dimension.width, dimension.height - 1)) :
                            new TriangleBoundsComponent(new Dimension(dimension.width, dimension.height - 1))
            );

            Arrays.stream(components).forEachOrdered(object::addComponent);

            if (tabNr == 1){
                BoxBoundsComponent bounds = object.getComponent(BoxBoundsComponent.class);
                if (bounds != null){
                    bounds.buffer = new Vector2(0, GameConstants.TILE_HEIGHT - 16);
                }
            }

            // TODO not nice looking. WE need a way to tell what portal was placed on the scene
            if (tabNr == 5){
                if (currentSpriteComponent.equals(sprites.get(0))){
                    object.addComponent(new PortalComponent(PlayerState.NORMAL));
                }else if (currentSpriteComponent.equals(sprites.get(1))){
                    object.addComponent(new PortalComponent(PlayerState.FLYING));
                }
            }

            this.tabsMap.get(tabs.get(tabNr)).add(object);
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
        return getClass().getSimpleName();
    }
}
