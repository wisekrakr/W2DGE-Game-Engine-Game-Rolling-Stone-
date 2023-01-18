package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;

public class LevelEditorScene extends Scene {

    public LevelEditorScene(String name) {
        super.Scene(name);
    }

    @Override
    public void init() {
        player = factory.player(new Vector2(100, 300), Screen.getInstance().isInEditorPhase);

        addGameObjectToScene(player);
        addGameObjectToScene(factory.ground());
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        camera.bounds(null, GameConstants.CAMERA_OFFSET_GROUND_Y);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Colors.synthWaveBlue);
        g2d.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        this.renderer.render(g2d);

    }

    @Override
    public void terminate() {

    }
}
