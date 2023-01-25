package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.game.components.physics.BoxBounds;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.assets.AssetManager;
import com.wisekrakr.w2dge.visual.scene.LevelEditorScene;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LevelEditMenuContainer extends Component<LevelEditMenuContainer> {

    private final LevelEditorScene levelEditorScene;

    private final Sprite containerBg;
    private final List<GameObject> menuItems;
    private final List<GameObject> tabs;
    private final Map<GameObject, List<GameObject>> tabsMap;
    private GameObject currentTab;

    private GameObject focusedButton = null;
    private GameObject focusedTab = null;

    public LevelEditMenuContainer(LevelEditorScene levelEditorScene) {
        this.levelEditorScene = levelEditorScene;
        this.menuItems = new ArrayList<>();
        this.tabs = new ArrayList<>();
        this.tabsMap = new HashMap<>();
        this.containerBg = AssetManager.getSprite(AssetFinder.image(AssetFinder.ImageType.UI, "levelEditMenu.png"));
        init();
    }

    @Override
    public void init() {
        initTabs();
        addTabObjects();

        start();
    }

    private void start() {
        for (GameObject gameObject : this.menuItems) {
            for (GameObject menuItem: tabsMap.get(gameObject)){
                for (Component<?> c : menuItem.getAllComponents()) {
                    c.init();
                }
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        for (GameObject gameObject : this.tabsMap.get(currentTab)) {
            gameObject.update(deltaTime);

            MenuItem menuItem = gameObject.getComponent(MenuItem.class);
            if (gameObject != focusedButton && menuItem.isSelected){
                menuItem.isSelected = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.containerBg.image, 0, GameConstants.MENU_CONTAINER_Y,
                (int) this.containerBg.dimension.width, (int) this.containerBg.dimension.height, null);

        for (GameObject menuItem : this.tabsMap.get(currentTab)) {
            menuItem.render(g2d);
        }
    }

    private void initTabs() {
        for (int i = 0; i < AssetManager.tabsSheet.sprites.size(); i++) {
            Sprite currentTab = AssetManager.tabsSheet.sprites.get(i);

            int x = (int) (GameConstants.TAB_OFFSET_X + (currentTab.column * GameConstants.TAB_WIDTH) +
                    (currentTab.column * GameConstants.TAB_HORIZONTAL_SPACING));
            int y = GameConstants.TAB_OFFSET_Y;

            GameObject tab = GameObjectFactory.menuItemTab(currentTab, new Vector2(x, y), levelEditorScene.renderer);
            levelEditorScene.renderer.partOfUI(tab);

            tabs.add(tab);
            tabsMap.put(tab, new ArrayList<>());

            levelEditorScene.addGameObjectToScene(tab);
        }

        this.currentTab = this.tabs.get(0);
    }

    private void addTabObjects() {
        // Creates a menu item component that contains sprite
        for (int i = 0; i < AssetManager.groundSheet.sprites.size(); i++) {
            Sprite currentSprite = AssetManager.groundSheet.sprites.get(i);

            int x = (int) (GameConstants.BUTTON_OFFSET_X + (currentSprite.column * GameConstants.BUTTON_WIDTH) +
                    (currentSprite.column * GameConstants.BUTTON_HORIZONTAL_SPACING));
            int y = (int) (GameConstants.BUTTON_OFFSET_Y + (currentSprite.row * GameConstants.BUTTON_HEIGHT) +
                    (currentSprite.row * GameConstants.BUTTON_VERTICAL_SPACING));

            // Adding first tab container objects
            GameObject object = GameObjectFactory.menuItem((Sprite) currentSprite.copy(), new Vector2(x, y),
                    AssetManager.buttonSheet, this.levelEditorScene.renderer,this);

            this.tabsMap.get(this.tabs.get(0)).add(object);

            // Adding second tab container objects
            if (i < AssetManager.smallBlockSheet.sprites.size()) {

                object = GameObjectFactory.menuItemTab(AssetManager.smallBlockSheet.sprites.get(i), new Vector2(x, y),
                        levelEditorScene.renderer,
                        object.getComponent(MenuItem.class).copy()
                );

                if (i == 0) {
                    object.addComponent(new BoxBounds(new Dimension(GameConstants.TILE_WIDTH, 16)));
                }

                this.tabsMap.get(tabs.get(1)).add(object);
            }

            // Adding fourth tab container objects
            if (i < AssetManager.spikesSheet.sprites.size()) {
                object = GameObjectFactory.menuItemTab(AssetManager.spikesSheet.sprites.get(i), new Vector2(x, y),
                        levelEditorScene.renderer, object.getComponent(MenuItem.class).copy());

                //todo add triangle bounds
//                if (i == 0){
//                    menuItem.addComponent(new BoxBounds(new Dimension(GameConstants.TILE_WIDTH, 16)));
//                }
                this.tabsMap.get(tabs.get(3)).add(object);
            }

            // Adding fifth tab container objects
            if (i == 0) {
                object = GameObjectFactory.menuItemTab(AssetManager.bigSpritesSheet.sprites.get(i), new Vector2(x, y),
                        levelEditorScene.renderer,
                        object.getComponent(MenuItem.class).copy(),
                        new BoxBounds(new Dimension(GameConstants.TILE_WIDTH * 2, 56))
                );
                this.tabsMap.get(tabs.get(4)).add(object);
            }

            // Adding sixth tab container objects
            if (i < AssetManager.portalSheet.sprites.size()) {
                object = GameObjectFactory.menuItemTab(AssetManager.portalSheet.sprites.get(i), new Vector2(x, y),
                        levelEditorScene.renderer,
                        object.getComponent(MenuItem.class).copy(),
                        new BoxBounds(new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT * 2))
                );
                //todo portal should not collide with player (BoxBounds)
                //todo add portal component to portal object

                this.tabsMap.get(tabs.get(5)).add(object);
            }
        }
    }

    public void setFocusedButton(GameObject gameObject){
        this.focusedButton = gameObject;
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
