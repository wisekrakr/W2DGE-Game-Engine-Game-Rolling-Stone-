package com.wisekrakr.w2dge.visual.graphics;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.physics.BoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;

import java.awt.*;
import java.awt.geom.Line2D;

public class DebugRenderer  {

    public void render(Graphics2D g2d, GameObject gameObject) {
        g2d.setColor(Color.GREEN);

        switch (gameObject.getComponent(BoundsComponent.class).type){

            case BOX -> {
                BoxBoundsComponent box = gameObject.getComponent(BoxBoundsComponent.class);
                g2d.drawRect((int) box.gameObject.transform.position.x, (int) box.gameObject.transform.position.y,
                        (int) box.dimension.width, (int) box.dimension.height);
            }
            case TRIANGLE -> {
                TriangleBoundsComponent triangle = gameObject.getComponent(TriangleBoundsComponent.class);

                g2d.draw(new Line2D.Float(triangle.x1, triangle.y1, triangle.x2, triangle.y2));
                g2d.draw(new Line2D.Float(triangle.x1, triangle.y1, triangle.x3, triangle.y3));
                g2d.draw(new Line2D.Float(triangle.x3, triangle.y3, triangle.x2, triangle.y2));
            }
        }
    }


}
