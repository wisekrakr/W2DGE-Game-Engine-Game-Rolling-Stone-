package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.controls.CameraControls;
import com.wisekrakr.w2dge.game.components.regions.Grid;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;

public class LevelEditorScene extends Scene {
    private Grid grid;
    private CameraControls cameraControls;
    private GameObject cursor;

    public LevelEditorScene(String name) {
        super.Scene(name);
    }

    @Override
    public void init() {
        grid = new Grid();
        cameraControls = new CameraControls();

        cursor = factory.mouserCursor();
        player = factory.player(new Vector2(100, 300), Screen.getInstance().isInEditorPhase);
        ground = factory.ground(GameConstants.GROUND_Y);

        addGameObjectToScene(player);
        addGameObjectToScene(ground);
        addGameObjectToScene(cursor);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        camera.bounds(null, GameConstants.CAMERA_OFFSET_GROUND_Y);

        grid.update(deltaTime);
        cameraControls.update(deltaTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        renderer.render(g2d);
        grid.render(g2d);
    }

    @Override
    public void terminate() {

    }
}
