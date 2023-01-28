package com.wisekrakr.w2dge.input;

import com.wisekrakr.main.Game;
import com.wisekrakr.util.FileUtils;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.entities.GameItemComponent;
import com.wisekrakr.w2dge.game.components.entities.PlayerComponent;
import com.wisekrakr.w2dge.game.components.ui.MenuItemControlComponent;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Initializes both {@link MouseListener} & {@link KeyListener}<br>
 * Handles all mouse and keyboard input in the {@link GameInputListener#update(List, Scene)} method
 */
public class GameInputListener extends AbstractGameInputListener{

    /**
     * Handles all mouse and keyboard input for {@link com.wisekrakr.w2dge.visual.scene.LevelEditorScene}
     *
     * @param gameObjects {@link GameObject} to export in save file
     * @param scene       {@link Scene} import the saved level on this scene
     */
    public void update(List<GameObject> gameObjects, Scene scene) {
        inputsOnAllScenes();

        // Keyboard inputs
        switch (this.keyListener.keyCode) {
            case KeyEvent.VK_F1 -> Screen.getInstance().changeScene(Game.SceneType.LEVEL_1);
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
            case KeyEvent.VK_ESCAPE -> Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).clearSelected();

            case KeyEvent.VK_W -> Screen.getScene().camera.position.y -= 3;
            case KeyEvent.VK_S -> Screen.getScene().camera.position.y += 3;
            case KeyEvent.VK_A -> Screen.getScene().camera.position.x -= 3;
            case KeyEvent.VK_D -> Screen.getScene().camera.position.x += 3;
        }
    }

    /**
     * Handles all mouse and keyboard input for {@link com.wisekrakr.w2dge.visual.scene.LevelScene}
     */
    public void update() {
        inputsOnAllScenes();

        PlayerComponent p = player.getComponent(PlayerComponent.class);

        switch (this.keyListener.keyCode) {
            // UI controls
            case KeyEvent.VK_F2 -> Screen.getInstance().changeScene(Game.SceneType.EDITOR);
            case KeyEvent.VK_P -> Game.isPaused = !Game.isPaused;
            // Player controls
            case KeyEvent.VK_SPACE -> {
                if (p.grounded) {
                    p.jump();
                    p.grounded = false;
                }
            }
            // Camera controls
            case KeyEvent.VK_R -> player.getComponent(PlayerComponent.class).reset();
            // Level controls
            case KeyEvent.VK_G -> {
                for (GameObject gameObject : Screen.getScene().getGameObjects()) {
                    if (gameObject.getComponent(GameItemComponent.class) != null) {
                        // todo change color game item
                        gameObject.getComponent(GameItemComponent.class).changeColor = true;
                    }
                }
            }
        }
    }


    private void inputsOnAllScenes() {
        // UI controls
        switch (this.keyListener.keyCode) {
            case KeyEvent.VK_Q -> System.exit(1);
        }
    }
}
