package com.wisekrakr.w2dge.game.components.regions;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.geom.Line2D;


public class GridComponent extends Component<GridComponent> {

    Camera camera;
    public Dimension dimension;

    public GridComponent() {
        this.camera = Screen.getScene().camera;
        this.dimension = new Dimension(GameConstants.GRID_WIDTH, GameConstants.GRID_HEIGHT);
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(1f)); // 1 pixel wide
        g2d.setColor(new Color(0.2f,0.2f,0.2f, 0.45f));

        // Look for the GROUND game object
        GameObject ground = Screen.getScene().ground;
        if (ground == null){
            ground = GameObjectFactory.ground();
        }
        float bottom = Math.min(ground.transform.position.y - camera.position.y, GameConstants.SCREEN_HEIGHT);

        // Math.floor converts to a solid number - relative to 0,0
        float startX = (float) Math.floor(camera.position.x / dimension.width) * dimension.width - camera.position.x;
        float startY = (float) Math.floor(camera.position.y / dimension.height) * dimension.height - camera.position.y;

        int gridYLines = GameConstants.GRID_Y_LINES;
        for (int column = 0; column < gridYLines; column++) {
            // start drawing from the top left (x=0,0) to the bottom of the screen (SCREEN_HEIGHT)
            g2d.draw(new Line2D.Float(startX, 0, startX, bottom));
            startX += dimension.width;
        }

        int gridXLines = GameConstants.GRID_X_LINES;
        for (int row = 0; row < gridXLines; row++) {
            // if camera is below the ground line, we will draw the horizontal  lines
            if(camera.position.y + startY < ground.transform.position.y){
                g2d.draw(new Line2D.Float(0, startY, GameConstants.SCREEN_WIDTH, startY));
                startY += dimension.height;
            }
        }
    }

    @Override
    public Component<GridComponent> copy() {
        return new GridComponent();
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
