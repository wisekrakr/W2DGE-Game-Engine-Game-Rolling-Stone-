package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.main.Game;
import com.wisekrakr.util.FileUtils;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.graphics.DebugRenderer;

import java.awt.*;

public class LevelScene extends Scene {

    public LevelScene(String name) {
        super.createScene(name);
        this.type = Game.SceneType.LEVEL_1;
    }

    @Override
    public void init() {
        super.init();

        this.player = GameObjectFactory.player(
                new Vector2(GameConstants.PLAYER_START_X, GameConstants.PLAYER_START_Y),
                Screen.getInstance().isInEditorPhase);

        this.ground = GameObjectFactory.ground();

        addGameObjectToScene(player);
        addGameObjectToScene(ground);

        Screen.getInputListener().setController(this.player);

        initBackgrounds(7, "bg04-hd.png", "ground02.png");

        FileUtils.importFileToLevel("Test", this);

        getRenderer().initDebugging();
    }

    @Override
    public void update(double deltaTime) {
        if (!Game.isPaused){
            super.update(deltaTime);
        }

        camera.bounds(null, GameConstants.CAMERA_OFFSET_GROUND_Y);

    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);

        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        getRenderer().render(g2d);

        if (getRenderer().isDebugging){
            getRenderer().debugRenderer.render(g2d, getGameObjects(),
                    DebugRenderer.DebugType.DISTANCE, DebugRenderer.DebugType.BORDER);
        }

    }


    @Override
    public void terminate() {

    }
}
