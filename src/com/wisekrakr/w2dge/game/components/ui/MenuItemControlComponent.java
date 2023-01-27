package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.input.GameInputListener;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class MenuItemControlComponent extends Component<MenuItemControlComponent> {

    /**
     * Registers a click every 0.2 seconds
     */
    private float debounceTime = 0.2f;
    /**
     * Registers when the ability to click again arises, so the user can't spam click
     */
    private float debounceTimeLeft = 0.0f;

    private List<GameObject> selectedGameObjects;
    private final Vector2 worldPosition;
    private boolean isEditing = false;

    public MenuItemControlComponent() {
        this.worldPosition = new Vector2();
        this.selectedGameObjects = new ArrayList<>();
    }


    @Override
    public void update(double deltaTime) {
        debounceTimeLeft -= deltaTime;

        GameInputListener inputListener = Screen.getInputListener();
        Camera camera = Screen.getScene().camera;

        if (!isEditing && this.gameObject.getComponent(SpriteComponent.class) != null) {
            isEditing = true;
        }

        if (isEditing) {
            // snap the mouse and place something in each individual grid line
            updateSpritePosition(inputListener, camera);
        }

        // Mouse has been clicked
        if (inputListener.mouseListener.position.y < GameConstants.TAB_OFFSET_Y &&
                inputListener.mouseListener.mousePressed &&
                inputListener.mouseListener.mouseButton == MouseEvent.BUTTON1 &&
                debounceTimeLeft < 0) {

            debounceTimeLeft = debounceTime;

//            if (isEditing) {
//                // click to add to grid
//                copyObjectToScene();
//            }


            if (inputListener.keyListener.isKeyPressed(KeyEvent.VK_SHIFT)) {
                addGameObjectToSelected(
                        new Vector2(inputListener.mouseListener.position.x, inputListener.mouseListener.position.y));
            } else {
                // click to add to grid
                copyObjectToScene();

                clearSelectedGameObjects(
                        new Vector2(inputListener.mouseListener.position.x, inputListener.mouseListener.position.y));
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        SpriteComponent spriteComponent = gameObject.getComponent(SpriteComponent.class);
        if (spriteComponent != null) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f); // transparent until placement
            g2d.setComposite(composite);
            g2d.drawImage(spriteComponent.image,
                    (int) gameObject.transform.position.x, (int) gameObject.transform.position.y,
                    (int) gameObject.dimension.width, (int) gameObject.dimension.height,
                    null
            );
            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2d.setComposite(composite);
        }
    }



    private void updateSpritePosition(GameInputListener inputListener, Camera camera) {
        worldPosition.x = (float) Math.floor((inputListener.mouseListener.position.x + camera.position.x +
                inputListener.mouseListener.dx) / this.gameObject.dimension.width);
        worldPosition.y = (float) Math.floor((inputListener.mouseListener.position.y + camera.position.y +
                inputListener.mouseListener.dy) / this.gameObject.dimension.height);

        // transform to world x and y
        this.gameObject.transform.position.x = worldPosition.x * this.gameObject.dimension.width - camera.position.x;
        this.gameObject.transform.position.y = worldPosition.y * this.gameObject.dimension.height - camera.position.y;
    }

    private void copyObjectToScene() {
        GameObject copy = this.gameObject.copy();

        copy.transform.position = new Vector2(
                worldPosition.x * this.gameObject.dimension.width,
                worldPosition.y * this.gameObject.dimension.height);

        Screen.getScene().addGameObjectToScene(copy);
    }

    /**
     * Clears the selected game item from the cursor. Sets editing to false
     */
    public void clearSelected() {
        Screen.getScene().levelEditMouseCursor = GameObjectFactory.mouserCursor(this.gameObject);
        isEditing = false;

        clearSelectedGameObjects(Screen.getScene().levelEditMouseCursor.transform.position);
    }

    /**
     * Switches current selected Game Object by clearing the old and adding a new.
     *
     * @param mousePosition
     */
    private void clearSelectedGameObjects(Vector2 mousePosition) {
        for (GameObject g : selectedGameObjects) {
            g.getComponent(ClickableComponent.class).isSelected = false;
        }
        selectedGameObjects.clear();
        addGameObjectToSelected(mousePosition);
    }

    /**
     * Gets the camera position of the current scene<br>
     * Loops through all current game object in the current scene <br.
     * Searches for a {@link ClickableComponent} on the game object<br>
     * If there is a clickable component and that game object is in bounds of the mouse position, the game object gets
     * selected and we break out the loop.
     *
     * @param mousePosition
     */
    private void addGameObjectToSelected(Vector2 mousePosition) {
        // transform to world coordinates
        mousePosition.x += Screen.getScene().camera.position.x;
        mousePosition.y += Screen.getScene().camera.position.y;

        for (GameObject g : Screen.getScene().getGameObjects()) {
            ClickableComponent clickable = g.getComponent(ClickableComponent.class);
            if (clickable != null && clickable.inBounds(mousePosition)) {
                this.selectedGameObjects.add(g);
                clickable.isSelected = true;
                break;
            }
        }
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
