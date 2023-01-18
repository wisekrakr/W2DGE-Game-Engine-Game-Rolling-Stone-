package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.regions.Grid;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LevelEditorScene extends Scene {

    private Grid grid;

    public LevelEditorScene(String name) {
        super.Scene(name);
    }

    @Override
    public void init() {
        grid = factory.grid();

        player = factory.player(new Vector2(100, 300), Screen.getInstance().isInEditorPhase);
        ground = factory.ground(GameConstants.GROUND_Y);
        addGameObjectToScene(player);
        addGameObjectToScene(factory.ground(GameConstants.GROUND_Y));
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        camera.bounds(null, GameConstants.CAMERA_OFFSET_GROUND_Y);
        grid.update(deltaTime);

        TimerTask task = new TimerTask() {
            public void run() {
                ground.dimension.height += deltaTime * 5f;
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        timer.schedule(task, delay);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Colors.synthWaveBlue);
        g2d.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        this.renderer.render(g2d);
        grid.render(g2d);
    }

    @Override
    public void terminate() {

    }
}
