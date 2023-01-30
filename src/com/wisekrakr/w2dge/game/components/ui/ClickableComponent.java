package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class ClickableComponent extends Component<ClickableComponent> {
    public boolean isSelected;

    @Override
    public void render(Graphics2D g2d) {
        Camera camera = Screen.getScene().camera;

        if (isSelected) {
            g2d.setColor(Colors.synthWaveMagenta);
            g2d.setStroke(GameConstants.THICK_LINE);

            if (this.gameObject.getComponent(BoxBoundsComponent.class) != null){
                g2d.draw(
                        new Rectangle2D.Float(
                                this.gameObject.transform.position.x,
                                this.gameObject.transform.position.y,
                                this.gameObject.dimension.width,
                                this.gameObject.dimension.height
                        )
                );
            }else if (this.gameObject.getComponent(TriangleBoundsComponent.class) != null){
                TriangleBoundsComponent triangle = this.gameObject.getComponent(TriangleBoundsComponent.class);
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

            g2d.setStroke(GameConstants.LINE);
        }

    }

    public boolean inDragBounds(GameObject g, Vector2 position, Dimension dragDimension){
        return g.transform.position.x + g.dimension.width <= position.x + dragDimension.width &&
                g.transform.position.y + g.dimension.height <= position.y + dragDimension.height &&
                g.transform.position.x >= position.x && g.transform.position.y >= position.y;
    }

    public boolean inClickBounds(GameObject g, Vector2 position) {
        if (g.getComponent(BoxBoundsComponent.class)!= null){
            return position.x > this.gameObject.transform.position.x &&
                    position.x < this.gameObject.transform.position.x + this.gameObject.dimension.width &&
                    position.y > this.gameObject.transform.position.y &&
                    position.y < this.gameObject.transform.position.y + this.gameObject.dimension.height;

        }else if (g.getComponent(TriangleBoundsComponent.class)!= null){
            TriangleBoundsComponent triangle = this.gameObject.getComponent(TriangleBoundsComponent.class);

            // Compute vectors
            Vector2 v0 = new Vector2(triangle.x3 - triangle.x1, triangle.y3 - triangle.y1);
            Vector2 v1 = new Vector2(triangle.x2 - triangle.x1, triangle.y2 - triangle.y1);
            Vector2 v2 = new Vector2(position.x - triangle.x1, position.y - triangle.y1);

            // Compute dot products
            float dot00 = Vector2.dot(v0, v0);
            float dot01 = Vector2.dot(v0, v1);
            float dot02 = Vector2.dot(v0, v2);
            float dot11 = Vector2.dot(v1, v1);
            float dot12 = Vector2.dot(v1, v2);

            // Compute barycentric coordinates
            float invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
            float u = (dot11 * dot02 - dot01 * dot12) * invDenom;
            float v = (dot00 * dot12 - dot01 * dot02) * invDenom;

            // Check if point is in triangle
            return (u >= 0.0f) && (v >= 0.0f) && (u + v < 1.0f);
        }
        return false;
    }


    @Override
    public Component<ClickableComponent> copy() {
        return new ClickableComponent();
    }


    @Override
    public String name() {
        return getClass().getSimpleName();
    }

}
