package com.wisekrakr.w2dge.game.components.regions;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.event.MouseEvent;


public class SnapToGrid extends Component<SnapToGrid> {

    /**
     * Registers a click every 0.2 seconds
     */
    private float debounceTime = 0.2f;
    /**
     * Registers when the ability to click again arises, so the user can't spam click
     */
    private float debounceTimeLeft = 0.0f;

    @Override
    public void update(double deltaTime) {
        debounceTimeLeft -= deltaTime;

        Screen screen = Screen.getInstance();

        // snap the mouse and place something in each individual grid line
        if (this.gameObject.getComponent(Sprite.class) != null) {
            float x = (float) Math.floor((screen.mouseListener.position.x + screen.getCurrentScene().camera.position.x +
                    screen.mouseListener.dx) / this.gameObject.dimension.width);
            float y = (float) Math.floor((screen.mouseListener.position.y + screen.getCurrentScene().camera.position.y +
                    screen.mouseListener.dy) / this.gameObject.dimension.height);

            // transform to world x and y
            this.gameObject.transform.position.x = x * this.gameObject.dimension.width - screen.getCurrentScene().camera.position.x;
            this.gameObject.transform.position.y = y * this.gameObject.dimension.height - screen.getCurrentScene().camera.position.y;

            // click to add to grid
            if (screen.mouseListener.position.y < GameConstants.BUTTON_OFFSET_Y &&
                    screen.mouseListener.mousePressed &&
                    screen.mouseListener.mouseButton == MouseEvent.BUTTON1 &&
                    debounceTimeLeft < 0) {

                debounceTimeLeft = debounceTime;

                GameObject copy = this.gameObject.copy();
                copy.transform.position = new Vector2(x * this.gameObject.dimension.width, y * this.gameObject.dimension.height);
                screen.getCurrentScene().addGameObjectToScene(copy);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        Sprite sprite = gameObject.getComponent(Sprite.class);
        if (sprite != null) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f); // transparent until placement
            g2d.setComposite(composite);
            g2d.drawImage(sprite.image, (int) gameObject.transform.position.x, (int) gameObject.transform.position.y,
                    gameObject.dimension.width, gameObject.dimension.height, null);
            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2d.setComposite(composite);
        }
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
