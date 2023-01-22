package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.util.FileUtils;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.controls.CameraControls;
import com.wisekrakr.w2dge.game.components.regions.Grid;
import com.wisekrakr.w2dge.game.components.ui.MenuContainer;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.assets.AssetManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {

    public GameObject cursor;

    private Grid grid;
    private CameraControls cameraControls;
    private MenuContainer editingContainer;

    public LevelEditorScene(String name) {
        super.Scene(name);
    }

    @Override
    public void init() {
        grid = new Grid();
        cameraControls = new CameraControls();
        editingContainer = new MenuContainer();

        cursor = factory.mouserCursor();
        player = factory.player(new Vector2(100, 300), Screen.getInstance().isInEditorPhase);
        ground = factory.ground(GameConstants.GROUND_Y);

        addGameObjectToScene(player);
        addGameObjectToScene(ground);
    }

    public static void main(String[] args) {
        Parser.openFile("Bla");

        System.out.println(Parser.parseInt());
        System.out.println(Parser.parseInt());
        System.out.println(Parser.parseDouble());
        System.out.println(Parser.parseFloat());
        System.out.println(Parser.parseBoolean());
        System.out.println(Parser.parseBoolean());
        System.out.println(Parser.parseString());
        System.out.println(Parser.parseString());
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        camera.bounds(null, GameConstants.CAMERA_OFFSET_GROUND_Y);

        grid.update(deltaTime);
        cameraControls.update(deltaTime);
        editingContainer.update(deltaTime);
        cursor.update(deltaTime);

        if (Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_F5)) {
            System.out.println("Export file");
            FileUtils.exportLevel("Test", gameObjects);
        }

        if (Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_F9)) {
            System.out.println("Import file");
            FileUtils.importLevel("Test", this);
        }

        if (Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_F1)) {
            System.out.println(AssetManager.hasSpriteSheet("assets/spritesheets/tiles/spritesheet_tiles_blue_10x10.png"));
        }
    }


    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        renderer.render(g2d);
        grid.render(g2d);
        editingContainer.render(g2d);

        // Cursor always rendered last - so it is on top of everything
        cursor.render(g2d);
    }

    @Override
    public void terminate() {

    }
}
