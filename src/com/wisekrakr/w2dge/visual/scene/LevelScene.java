package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.util.FileUtils;
import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;

public class LevelScene extends Scene {
    public LevelScene(String name) {
        super.createScene(name);
    }

    @Override
    public void init() {
        player = GameObjectFactory.player(new Vector2(100, 300), Screen.getInstance().isInEditorPhase);
        ground = GameObjectFactory.ground();

        addGameObjectToScene(player);
        addGameObjectToScene(ground);

        FileUtils.importFileToLevel("Test", this);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        camera.bounds(null, GameConstants.CAMERA_OFFSET_GROUND_Y);

        Screen.getInstance().inputListener.update();
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Colors.synthWaveOrange);
        g2d.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        this.renderer.render(g2d);
    }

    @Override
    public void terminate() {

    }
}
