package com.wisekrakr.w2dge.input;

import com.wisekrakr.main.Game;
import com.wisekrakr.util.FileUtils;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.entities.Player;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Initializes both {@link MouseListener} & {@link KeyListener}<br>
 * Handles all mouse and keyboard input in the {@link GameInputListener#update(List, Scene)} method
 */
public class GameInputListener {

    public MouseListener mouseListener;
    public KeyListener keyListener;
    public GameObject player;

    public GameInputListener() {
        this.mouseListener = new MouseListener();
        this.keyListener = new KeyListener();
    }

    public void setController(GameObject player) {
        this.player = player;
    }

    /**
     * Handles all mouse and keyboard input for {@link com.wisekrakr.w2dge.visual.scene.LevelEditorScene}
     *
     * @param gameObjects {@link GameObject} to export in save file
     * @param scene       {@link Scene} import the saved level on this scene
     */
    public void update(List<GameObject> gameObjects, Scene scene) {

        if (Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_F1)) {
            Screen.getInstance().changeScene(Game.SceneType.LEVEL_1);
        } else if (Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_F5)) {
            System.out.println("Export file");
            Thread exportThread = new Thread(() -> {

                FileUtils.exportLevelToFile("Test", gameObjects);

            }, "Export level thread");
            exportThread.setDaemon(true);
            exportThread.start();

        } else if (Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_F9)) {
            System.out.println("Import file");
            new Thread(() -> {

                FileUtils.importFileToLevel("Test", scene);

            }, "Import level thread").start();
        }
    }

    /**
     * Handles all mouse and keyboard input for {@link com.wisekrakr.w2dge.visual.scene.LevelScene}
     */
    public void update() {
        if (Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_F2)) {
            Screen.getInstance().changeScene(Game.SceneType.EDITOR);
        }

        // UI controls
        if (Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            System.exit(1);
        }

        // Player controls
        Player p = player.getComponent(Player.class);

        if (p.grounded && Screen.getInstance().keyListener.isKeyPressed(KeyEvent.VK_SPACE)){
            p.jump();
            p.grounded = false;
        }
    }
}
