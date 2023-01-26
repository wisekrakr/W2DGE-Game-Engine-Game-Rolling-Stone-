package com.wisekrakr.w2dge.input;

import com.wisekrakr.main.Game;
import com.wisekrakr.util.FileUtils;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.entities.Block;
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
        inputsOnAllScenes();

        Screen screen = Screen.getInstance();

        switch (screen.keyListener.keyCode) {
            case KeyEvent.VK_F1 -> screen.changeScene(Game.SceneType.LEVEL_1);
            case KeyEvent.VK_F5 -> {
                System.out.println("Export file");
                Thread exportThread = new Thread(() -> {

                    FileUtils.exportLevelToFile("Test", gameObjects);

                }, "Export level thread");
                exportThread.setDaemon(true);
                exportThread.start();
            }
            case KeyEvent.VK_F9 -> {
                System.out.println("Import file");
                new Thread(() -> {

                    FileUtils.importFileToLevel("Test", scene);

                }, "Import level thread").start();
            }
        }
    }

    /**
     * Handles all mouse and keyboard input for {@link com.wisekrakr.w2dge.visual.scene.LevelScene}
     */
    public void update() {
        inputsOnAllScenes();

        Screen screen = Screen.getInstance();
        Player p = player.getComponent(Player.class);


        switch (screen.keyListener.keyCode) {
            // UI controls
            case KeyEvent.VK_F2 -> screen.changeScene(Game.SceneType.EDITOR);
            case KeyEvent.VK_P -> {
                Game.isPaused = !Game.isPaused;



                //                if (!Game.isPaused){
//                    // todo show pause scene
//                    screen.changeScene(Game.SceneType.PAUSE);
//                }else{
//                    screen.changeScene(Game.SceneType.LEVEL_1);
//                }
            }
            // Player controls
            case KeyEvent.VK_SPACE -> {
                if (p.grounded) {
                    p.jump();
                    p.grounded = false;
                }
            }
            // Camera controls
            case KeyEvent.VK_R -> screen.getCurrentScene().resetToStart();
            // Level controls
            case KeyEvent.VK_G -> {
                for (GameObject gameObject : screen.getCurrentScene().gameObjects) {
                    if (gameObject.getComponent(Block.class) != null) {
                        // todo block change color
                        gameObject.getComponent(Block.class).changeColor();
                        System.out.println("changeye");
                    }
                }
            }
        }
    }


    private void inputsOnAllScenes() {
        // UI controls
        Screen screen = Screen.getInstance();

        switch (screen.keyListener.keyCode) {
            case KeyEvent.VK_ESCAPE -> System.exit(1);
        }

    }
}
