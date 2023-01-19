package com.wisekrakr.w2dge.game.components.regions;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;


public class SnapToGrid extends Component<SnapToGrid> {

    /**
     * Registers a click every 0.2 seconds
     */
    private float debounceTime = 0.2f;
    /**
     * Registers when the ability to click again arises, so the user can't spam click
     */
    private float debounceTimeLeft = 0.0f;

    private final int gridWidth;
    private final int gridHeight;

    public SnapToGrid(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
    }

    @Override
    public void update(double deltaTime) {
        Screen screen = Screen.getInstance();
        // snap the mouse and place something in each individual grid line
        if (this.gameObject.getComponent(Sprite.class) != null){
            float x = (float) Math.floor(screen.mouseListener.position.x + screen.getCurrentScene().camera.position.x +
                    screen.mouseListener.dx / this.gridWidth);
            float y = (float) Math.floor(screen.mouseListener.position.y + screen.getCurrentScene().camera.position.y +
                    screen.mouseListener.dy / this.gridHeight);

            // transform to world x and y
            this.gameObject.transform.position.x = x * gridWidth - screen.getCurrentScene().camera.position.x;
            this.gameObject.transform.position.y = x * gridHeight - screen.getCurrentScene().camera.position.y;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        Sprite sprite = gameObject.getComponent(Sprite.class);
        if (sprite != null){
            float alpha = 0.5f; // transparent until placement
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(composite);
            g2d.drawImage(sprite.image, (int)gameObject.transform.position.x, (int)gameObject.transform.position.y,
                    gameObject.dimension.width, gameObject.dimension.height,null);
            alpha = 1.0f;
            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(composite);
        }
    }
}
