package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Vector2;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ClickableComponent extends Component<ClickableComponent> {
    public boolean isSelected;

    @Override
    public void render(Graphics2D g2d) {
        if (isSelected){
            g2d.setColor(Colors.blueGreen);
            g2d.setStroke(GameConstants.THICK_LINE);
            g2d.draw(
                    new Rectangle2D.Float(
                            this.gameObject.transform.position.x,
                            this.gameObject.transform.position.y,
                            this.gameObject.dimension.width,
                            this.gameObject.dimension.height
                    )
            );
            g2d.setStroke(GameConstants.LINE);
        }
    }

    public boolean inBounds(Vector2 position){
        return position.x > this.gameObject.transform.position.x &&
                position.x < this.gameObject.transform.position.x + this.gameObject.dimension.width &&
                position.y > this.gameObject.transform.position.y &&
                position.y < this.gameObject.transform.position.y + this.gameObject.dimension.height;
    }

    @Override
    public Component<ClickableComponent> copy() {
        return new ClickableComponent();
    }


    @Override
    public String name() {
        return getClass().getName();
    }

}
