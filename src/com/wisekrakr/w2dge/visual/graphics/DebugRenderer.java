package com.wisekrakr.w2dge.visual.graphics;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.graphics.BackgroundComponent;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;
import com.wisekrakr.w2dge.game.components.ui.MenuItemComponent;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class DebugRenderer {

    public enum DebugType{BORDER,DISTANCE}

    public void render(Graphics2D g2d, List<GameObject> gameObjects, DebugType...types) {
        for (GameObject gameObject : gameObjects) {
            for (DebugType type: types){
                switch (type){
                    case BORDER -> renderBorder(g2d, gameObject);
                    case DISTANCE ->  renderDistanceLine(g2d, gameObject);
                }
            }
        }
    }

    /**
     * Renders a border along a GameObject.
     * @param g2d
     * @param gameObject
     */
    private void renderBorder(Graphics2D g2d, GameObject gameObject) {
        g2d.setColor(Color.GREEN);
        g2d.setStroke(GameConstants.THICK_LINE);
        Camera camera = Screen.getScene().camera;

        if (gameObject.getComponent(MenuItemComponent.class) == null) {
            if (gameObject.getComponent(BoxBoundsComponent.class) != null) {
                BoxBoundsComponent box = gameObject.getComponent(BoxBoundsComponent.class);
                g2d.draw(
                        new Rectangle2D.Float(
                                gameObject.transform.position.x + box.buffer.x - camera.position.x,
                                gameObject.transform.position.y + box.buffer.y - camera.position.y,
                                box.buffer == Vector2.Zero ? gameObject.dimension.width :
                                        gameObject.dimension.width - box.buffer.x,
                                box.buffer == Vector2.Zero ? gameObject.dimension.height :
                                        gameObject.dimension.height - box.buffer.y
                        )
                );
            } else if (gameObject.getComponent(TriangleBoundsComponent.class) != null) {
                TriangleBoundsComponent triangle = gameObject.getComponent(TriangleBoundsComponent.class);
                g2d.draw(new Line2D.Float(
                        triangle.x1 - camera.position.x,
                        triangle.y1 - camera.position.y,
                        triangle.x2 - camera.position.x,
                        triangle.y2 - camera.position.y
                ));
                g2d.draw(new Line2D.Float(
                        triangle.x1 - camera.position.x,
                        triangle.y1 - camera.position.y,
                        triangle.x3 - camera.position.x,
                        triangle.y3 - camera.position.y
                ));
                g2d.draw(new Line2D.Float(
                        triangle.x3 - camera.position.x,
                        triangle.y3 - camera.position.y,
                        triangle.x2 - camera.position.x,
                        triangle.y2 - camera.position.y
                ));
            }
        }
        g2d.setStroke(GameConstants.LINE);
    }

    private void renderDistanceLine(Graphics2D g2d, GameObject gameObject){
        if (!gameObject.equals(Screen.getScene().player) && !gameObject.equals(Screen.getScene().ground) &&
                gameObject.getComponent(BackgroundComponent.class) == null) {
            int x1 = (int) (Screen.getScene().player.transform.position.x - Screen.getScene().camera.position.x);
            int y1 = (int) (Screen.getScene().player.transform.position.y - Screen.getScene().camera.position.y);
            int x2 = (int) (gameObject.transform.position.x - Screen.getScene().camera.position.x);
            int y2 = (int) (gameObject.transform.position.y - Screen.getScene().camera.position.y);
            g2d.drawLine(x1, y1, x2, y2);
            int distance = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            g2d.drawString(gameObject.name + " " + distance, (x1 + x2) / 2, (y1 + y2) / 2);
        }
    }

}
