package com.wisekrakr.w2dge.game.components.regions;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.geom.Line2D;


public class Grid extends Component<Grid> {

    Camera camera;
    public Dimension dimension;
    private int gridYLines = GameConstants.GRID_Y_LINES;
    private int gridXLines = GameConstants.GRID_X_LINES;


    public Grid() {
        this.camera = Screen.getInstance().getCurrentScene().camera;
        this.dimension = new Dimension(GameConstants.GRID_WIDTH, GameConstants.GRID_HEIGHT);
    }

    @Override
    public void update(double deltaTime) {


    }

    @Override
    public void render(Graphics2D g2d) {
        // Look for the GROUND game object
        // Math.floor converts to a solid number - relative to 0,0
        float startX = (float) Math.floor(camera.position.x / dimension.width) * dimension.width - camera.position.x;
        float startY = (float) Math.floor(camera.position.y / dimension.height) * dimension.height - camera.position.y;

        for (int column = 0; column < gridYLines; column++) {
            // start drawing from the top left (x=0,0) to the bottom of the screen (SCREEN_HEIGHT)
            g2d.draw(new Line2D.Float(startX, 0, startX, GameConstants.SCREEN_HEIGHT));
            startX += dimension.width;
        }
    }
}
