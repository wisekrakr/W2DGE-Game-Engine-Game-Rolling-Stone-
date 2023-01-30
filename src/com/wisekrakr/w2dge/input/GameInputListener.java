package com.wisekrakr.w2dge.input;

import com.wisekrakr.main.Game;
import com.wisekrakr.util.FileUtils;
import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.GameItemComponent;
import com.wisekrakr.w2dge.game.components.entities.PlayerComponent;
import com.wisekrakr.w2dge.game.components.physics.RigidBodyComponent;
import com.wisekrakr.w2dge.game.components.ui.MenuItemControlComponent;
import com.wisekrakr.w2dge.math.Direction;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.LevelEditorScene;
import com.wisekrakr.w2dge.visual.scene.LevelScene;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Initializes both {@link MouseListener} & {@link KeyListener}<br>
 * Handles most key and mouse input events
 */
public class GameInputListener extends AbstractGameInputListener implements GameLoopImpl {

    /**
     * Registers a click every 0.2 seconds
     */
    private float debounceTime = 0.5f;
    /**
     * Registers when the ability to click again arises, so the user can't spam click
     */
    private float debounceTimeLeft = 0.0f;

    public boolean shiftKeyPressed = false;
    public boolean controlKeyPressed = false;

    @Override
    public void init() {

    }

    @Override
    public void update(double deltaTime) {
        debounceTimeLeft -= deltaTime;

        inputsOnAllScenes();

        // Inputs LevelEditorScene
        if (Screen.getScene() instanceof LevelEditorScene) {

            keyJustPressed(this.keyListener.keyCode);

            // Keyboard
            switch (this.keyListener.keyCode) {
                case KeyEvent.VK_F1 -> Screen.getInstance().changeScene(Game.SceneType.LEVEL_1); // go to level scene
                case KeyEvent.VK_F5 -> keyJustPressed(KeyEvent.VK_F5); // save level to file
                case KeyEvent.VK_F9 -> keyJustPressed(KeyEvent.VK_F9); // import level to scene
                case KeyEvent.VK_ESCAPE -> { // clear all selected game item in the level edit scene
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).clearSelected();
                }
                case KeyEvent.VK_W -> { // W
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).direction = Direction.UP;
                }
                case KeyEvent.VK_S -> { // S
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).direction = Direction.DOWN;
                }
                case KeyEvent.VK_A -> { // A
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).direction = Direction.LEFT;
                }
                case KeyEvent.VK_D -> { // D
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).direction = Direction.RIGHT;
                }
                case KeyEvent.VK_UP -> Screen.getScene().camera.position.y -= 3;
                case KeyEvent.VK_DOWN -> Screen.getScene().camera.position.y += 3;
                case KeyEvent.VK_LEFT -> Screen.getScene().camera.position.x -= 3;
                case KeyEvent.VK_RIGHT -> Screen.getScene().camera.position.x += 3;
                case KeyEvent.VK_C -> { // C duplicate objects
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).duplicate();
                }
                case KeyEvent.VK_DELETE -> { // DELETE delete objects
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).delete();
                }
                case KeyEvent.VK_Q -> { // Q rotate to the left
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).rotate(45f);
                }
                case KeyEvent.VK_E -> { // E rotate to the right
                    Screen.getScene().levelEditMouseCursor.getComponent(MenuItemControlComponent.class).rotate(-45f);
                }
            }


            // Inputs LevelScene
        } else if (Screen.getScene() instanceof LevelScene) {
            PlayerComponent p = player.getComponent(PlayerComponent.class);
            RigidBodyComponent rigidPlayer = player.getComponent(RigidBodyComponent.class);

            // Keyboard
            switch (this.keyListener.keyCode) {
                // UI controls
                case KeyEvent.VK_F2 ->
                        Screen.getInstance().changeScene(Game.SceneType.EDITOR); // go to level editor scene
                case KeyEvent.VK_P -> keyJustPressed(KeyEvent.VK_P); // pausing and un-pausing the game

                // Player controls
                case KeyEvent.VK_SPACE -> { // player jump
                    if (p.grounded) {
                        p.jump();
                        p.grounded = false;
                    }
                }
                case KeyEvent.VK_W -> rigidPlayer.direction = Direction.UP;
                case KeyEvent.VK_S -> rigidPlayer.direction = Direction.DOWN;
                case KeyEvent.VK_A -> rigidPlayer.direction = Direction.LEFT;
                case KeyEvent.VK_D -> rigidPlayer.direction = Direction.RIGHT;

                // Camera controls
                case KeyEvent.VK_R -> keyJustPressed(KeyEvent.VK_R); // reset player and camera to start

                // Level controls
                case KeyEvent.VK_G -> keyJustPressed(KeyEvent.VK_G); // change game item color
            }
        }
    }

    private void getComponentAndSetBoolean(Class<? extends Component> component, boolean b) {
        for (GameObject gameObject : Screen.getScene().getGameObjects()) {
            if (gameObject.getComponent(component) != null) {
                switch (component.getSimpleName()) {
                    case "GameItemComponent" -> gameObject.getComponent(GameItemComponent.class).changeColor = b;

                }
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {

    }


    private void inputsOnAllScenes() {
        // UI controls
        switch (this.keyListener.keyCode) {
            case KeyEvent.VK_0 -> System.exit(1);
            case KeyEvent.VK_SHIFT -> shiftKeyPressed = true;
            case KeyEvent.VK_CONTROL -> controlKeyPressed = true;
        }

        if (!keyListener.isKeyPressed(KeyEvent.VK_SHIFT)) {
            shiftKeyPressed = false;
        }
        if (!keyListener.isKeyPressed(KeyEvent.VK_CONTROL)) {
            controlKeyPressed = false;
        }
    }

    /**
     * Method that makes it so the user can't hold down a key
     */
    private void keyJustPressed(int keyCode) {
        if (debounceTimeLeft < 0) {
            debounceTimeLeft = debounceTime;

            switch (keyCode) {
                case KeyEvent.VK_F5 -> { // export a level file
                    System.out.println("Export file");
                    Thread exportThread = new Thread(() -> {

                        FileUtils.exportLevelToFile("Test", Screen.getScene().getGameObjects());

                    }, "Export level thread");
                    exportThread.setDaemon(true);
                    exportThread.start();
                }
                case KeyEvent.VK_F9 -> { // import a level file
                    System.out.println("Import file");
                    new Thread(() -> {

                        FileUtils.importFileToLevel("Test", Screen.getScene());

                    }, "Import level thread").start();
                }
                case KeyEvent.VK_P -> Game.isPaused = !Game.isPaused; // pause the game
                case KeyEvent.VK_R -> player.getComponent(PlayerComponent.class).reset(); // reset player to start
                case KeyEvent.VK_G ->
                        getComponentAndSetBoolean(GameItemComponent.class, true); // change color game item
            }
        }
    }

    @Override
    public void terminate() {

    }
}
