package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.math.Vector2;

import java.awt.*;

public class LevelEditorScene extends Scene {

    public LevelEditorScene(String name) {
        super.Scene(name);
        Scene.currentScene = this;

    }

    public static LevelEditorScene getScene() {
        if (currentScene == null) {
            currentScene = new LevelEditorScene("Level Editor Scene");
        }
        return (LevelEditorScene) currentScene;
    }

    @Override
    public void init() {
        player = factory.player(new Vector2(100, 300));
        factory.ground();
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
