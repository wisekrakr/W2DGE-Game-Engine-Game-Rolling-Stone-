package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.main.Game;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.components.controls.CameraControlsComponent;
import com.wisekrakr.w2dge.game.components.regions.GridComponent;
import com.wisekrakr.w2dge.game.components.ui.MenuContainerComponent;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;

public class LevelEditorScene extends Scene {

    public GameObject cursor;

    private GridComponent gridComponent;
    private CameraControlsComponent cameraControlsComponent;
    private MenuContainerComponent editingContainer;

    public LevelEditorScene(String name) {
        super.createScene(name);

        this.type = Game.SceneType.EDITOR;
    }

    @Override
    public void init() {
        super.init();

        gridComponent = new GridComponent();
        cameraControlsComponent = new CameraControlsComponent();
        editingContainer = new MenuContainerComponent(this);

        cursor = GameObjectFactory.mouserCursor();
        player = GameObjectFactory.player(new Vector2(100, 300), Screen.getInstance().isInEditorPhase);
        ground = GameObjectFactory.ground();

        addGameObjectToScene(player);
        addGameObjectToScene(ground);

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        camera.bounds(null, GameConstants.CAMERA_OFFSET_GROUND_Y + 100);

        gridComponent.update(deltaTime);
        cameraControlsComponent.update(deltaTime);
        editingContainer.update(deltaTime);
        cursor.update(deltaTime);

        Screen.getInstance().inputListener.update(gameObjects, this);


    }


    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        renderer.render(g2d);
        gridComponent.render(g2d);
        editingContainer.render(g2d);

        // Cursor always rendered last - so it is on top of everything
        cursor.render(g2d);
    }

    @Override
    public void terminate() {

    }
}
