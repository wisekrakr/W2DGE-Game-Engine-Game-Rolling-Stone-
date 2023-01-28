package com.wisekrakr.w2dge.visual.graphics;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;
import com.wisekrakr.w2dge.game.components.ui.MenuItemComponent;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DebugRenderer {

    public void render(Graphics2D g2d, GameObject gameObject) {
        g2d.setColor(Color.GREEN);
        g2d.setStroke(GameConstants.THICK_LINE);
        Camera camera = Screen.getScene().camera;

        if (gameObject.getComponent(MenuItemComponent.class) == null) {
            if (gameObject.getComponent(BoxBoundsComponent.class) != null){
                g2d.draw(
                        new Rectangle2D.Float(
                                gameObject.transform.position.x,
                                gameObject.transform.position.y,
                                gameObject.dimension.width,
                                gameObject.dimension.height
                        )
                );
            }else if (gameObject.getComponent(TriangleBoundsComponent.class) != null){
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


}
