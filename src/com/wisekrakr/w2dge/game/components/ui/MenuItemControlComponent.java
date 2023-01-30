package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;
import com.wisekrakr.w2dge.input.GameInputListener;
import com.wisekrakr.w2dge.input.MouseListener;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Direction;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
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
    private float debounceKeyStroke = 0.3f;
    private float debounceKeyStrokeLeft = 0.0f;
    private float debounceOperation = 0.3f;
    private float debounceOperationLeft = 0.0f;

    private final List<GameObject> selectedGameObjects;
    private final Vector2 worldPosition;
    private boolean isEditing = false;
    private boolean wasDragged = false;
    private final Vector2 dragPosition;
    private final Dimension dragDimension;

    public Direction direction = Direction.NONE;

    public MenuItemControlComponent() {
        this.worldPosition = new Vector2();
        this.selectedGameObjects = new ArrayList<>();

        this.dragPosition = new Vector2();
        this.dragDimension = new Dimension();
    }

    @Override
    public void update(double deltaTime) {
        debounceTimeLeft -= deltaTime;
        debounceKeyStrokeLeft -= deltaTime;
        debounceOperationLeft -= deltaTime;

        GameInputListener inputListener = Screen.getInputListener();
        Camera camera = Screen.getScene().camera;

        if (!isEditing && this.gameObject.getComponent(SpriteComponent.class) != null) {
            isEditing = true;
        }

        if (isEditing) {
            // snap the mouse and place something in each individual grid line
            updateSpritePosition(inputListener.mouseListener, camera);
        }

        // Mouse has been clicked
        if (inputListener.mouseListener.position.y < GameConstants.TAB_OFFSET_Y &&
                inputListener.leftMousePressed() &&
                !wasDragged &&
                debounceTimeLeft < 0) {

            debounceTimeLeft = debounceTime;

            // Selecting multiple game objects with SHIFT
            if (inputListener.shiftKeyPressed) {
                stackGameObjectFromSelected(
                        new Vector2(inputListener.mouseListener.position.x, inputListener.mouseListener.position.y), true
                );
            } else {
                // click to add to grid
                copyObjectToScene();

                clearSelectedGameObjects(
                        new Vector2(inputListener.mouseListener.position.x, inputListener.mouseListener.position.y));
            }
        }

        // Right mouse click
        if (inputListener.mouseListener.position.y < GameConstants.TAB_OFFSET_Y &&
                inputListener.rightMousePressed() &&
                !wasDragged &&
                debounceTimeLeft < 0) {

            debounceTimeLeft = debounceTime;

            // Deselecting multiple game objects with SHIFT
            if (inputListener.shiftKeyPressed) {
                stackGameObjectFromSelected(
                        new Vector2(inputListener.mouseListener.position.x, inputListener.mouseListener.position.y),
                        false
                );
            }
        }

        // Mouse Dragging
        if (!inputListener.leftMousePressed() && wasDragged) {
            clearSelected();
            stackGameObjectFromSelected(dragPosition, true);
            wasDragged = false;
        }

        // W-S-A-D movement for selected game objects
        if (debounceKeyStrokeLeft <= 0) {
            moveSelectedGameObjects(direction, inputListener.shiftKeyPressed ? 0.1f : 1.0f);

            debounceKeyStrokeLeft = debounceKeyStroke;
            direction = Direction.NONE;
        }


    }

    @Override
    public void render(Graphics2D g2d) {
        MouseListener mouseListener = Screen.getInputListener().mouseListener;

        if (isEditing) {
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
        } else if (mouseListener.mouseDragged && Screen.getInputListener().leftMousePressed()) {
            wasDragged = true;

            g2d.setColor(new Color(1, 1, 1, 0.3f));
            dragPosition.x = mouseListener.position.x;
            dragPosition.y = mouseListener.position.y;
            dragDimension.width = (int) mouseListener.dx;
            dragDimension.height = (int) mouseListener.dy;

            if (dragDimension.width < 0) {
                dragDimension.width *= -1;
                dragPosition.x -= dragDimension.width;
            }
            if (dragDimension.height < 0) {
                dragDimension.height *= -1;
                dragPosition.y -= dragDimension.height;
            }
            g2d.fillRect((int) dragPosition.x, (int) dragPosition.y, (int) dragDimension.width, (int) dragDimension.height);

            g2d.setColor(Colors.synthWaveCyan);
            g2d.setStroke(GameConstants.LINE);
            g2d.drawRect((int) dragPosition.x, (int) dragPosition.y, (int) dragDimension.width, (int) dragDimension.height);
        }

    }


    /**
     * Updates the sprite position on the screen based of the mouse position
     *
     * @param mouseListener {@link MouseListener}
     * @param camera        {@link Camera}
     */
    private void updateSpritePosition(MouseListener mouseListener, Camera camera) {
        worldPosition.x = (float) Math.floor((mouseListener.position.x + camera.position.x +
                mouseListener.dx) / this.gameObject.dimension.width);
        worldPosition.y = (float) Math.floor((mouseListener.position.y + camera.position.y +
                mouseListener.dy) / this.gameObject.dimension.height);

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
        stackGameObjectFromSelected(mousePosition, true);
    }


    /**
     * Gets the camera position of the current scene<br>
     * Loops through all current game object in the current scene <br.
     * Searches for a {@link ClickableComponent} on the game object<br>
     * If there is a clickable component and that game object is in bounds of the mouse position, the game object gets
     * selected or deselected (depending on toAdd), and we break out the loop.
     *
     * @param mousePosition position of the cursor in the scene
     * @param toAdd         either add it or remove it from selected game objects
     */
    private void stackGameObjectFromSelected(Vector2 mousePosition, boolean toAdd) {

        if (!wasDragged) {
            for (GameObject g : Screen.getScene().getGameObjects()) {
                // transform to world coordinates
                mousePosition.x += Screen.getScene().camera.position.x;
                mousePosition.y += Screen.getScene().camera.position.y;

                ClickableComponent clickable = g.getComponent(ClickableComponent.class);
                if (clickable != null && clickable.inClickBounds(g, mousePosition)) {
                    if (toAdd) {
                        this.selectedGameObjects.add(g);
                        clickable.isSelected = true;
                    } else {
                        this.selectedGameObjects.remove(g);
                        clickable.isSelected = false;
                    }
                    break;
                }
            }
        } else {
            for (GameObject g : Screen.getScene().getGameObjects()) {
                float x = mousePosition.x + Screen.getScene().camera.position.x;
                float y = mousePosition.y + Screen.getScene().camera.position.y;

                ClickableComponent clickable = g.getComponent(ClickableComponent.class);
                if (clickable != null) {
                    if (clickable.inDragBounds(g, new Vector2(x,y), dragDimension)) {
                        this.selectedGameObjects.add(g);
                        clickable.isSelected = true;
                    }
                }
            }
        }
    }


    private void moveSelectedGameObjects(Direction direction, float scale) {
        Vector2 distance = new Vector2(0.0f, 0.0f);
        for (GameObject g : selectedGameObjects) {
            switch (direction) {
                case UP -> distance.y = -g.dimension.height * scale;
                case DOWN -> distance.y = g.dimension.height * scale;
                case LEFT -> distance.x = -g.dimension.width * scale;
                case RIGHT -> distance.x = g.dimension.width * scale;
            }
        }

        for (GameObject gameObject : selectedGameObjects) {
            gameObject.transform.position.x += distance.x;
            gameObject.transform.position.y += distance.y;
            float gridX = (float) (Math.floor(gameObject.transform.position.x / gameObject.dimension.width) + 1) * gameObject.dimension.width;
            float gridY = (float) (Math.floor(gameObject.transform.position.y / gameObject.dimension.height) * gameObject.dimension.height);

            if (gameObject.transform.position.x < gridX + 1 && gameObject.transform.position.x > gridX - 1) {
                gameObject.transform.position.x = gridX;
            }

            if (gameObject.transform.position.y < gridY + 1 && gameObject.transform.position.y > gridY - 1) {
                gameObject.transform.position.y = gridY;
            }

            TriangleBoundsComponent bounds = gameObject.getComponent(TriangleBoundsComponent.class);
            if (bounds != null) bounds.init();
        }
    }

    /**
     * Creates a copy of all selected Game Objects in the scene and adds them to the scene
     */
    public void duplicate() {
        if (debounceOperationLeft <= 0) {
            for (GameObject g : selectedGameObjects) {
                Screen.getScene().addGameObjectToScene(g.copy());
            }
            debounceOperationLeft = debounceOperation;
        }
    }

    public void delete() {
        if (debounceOperationLeft <= 0) {
            for (GameObject g : selectedGameObjects) {
                Screen.getScene().removeGameObjectToScene(g);
            }
            clearSelected();
            debounceOperationLeft = debounceOperation;
        }
    }

    public void rotate(float degrees) {
        if (debounceOperationLeft <= 0) {
            for (GameObject g : selectedGameObjects) {
                g.transform.rotation += degrees;
            }

            debounceOperationLeft = debounceOperation;
        }
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

}
