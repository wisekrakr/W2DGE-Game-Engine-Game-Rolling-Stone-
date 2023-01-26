package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TabItem extends LevelEditMenuItem<TabItem> {

    public TabItem(Transform transform, Dimension dimension, LevelEditMenuContainer parentContainer, Sprite sprite) {
        super(transform, dimension, parentContainer, sprite);
    }

    @Override
    public void update(double deltaTime) {
        Screen screen = Screen.getInstance();

        // when clicked
        if (screen.mouseListener.mousePressed && screen.mouseListener.mouseButton == MouseEvent.BUTTON1) {
            if (!isSelected && this.gameObject.inMouseBounds()) {
                isSelected = true;
                this.parentContainer.setFocusedTab(this.gameObject);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {

        if (isSelected){
            g2d.drawImage(sprite.image, (int)transform.position.x,(int) transform.position.y,
                    (int)dimension.width,(int) dimension.height,null);
        }else {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f); // transparent until placement
            g2d.setComposite(composite);
            g2d.drawImage(sprite.image,
                    (int) gameObject.transform.position.x, (int) gameObject.transform.position.y,
                    (int) gameObject.dimension.width, (int) gameObject.dimension.height,
                    null
            );
            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2d.setComposite(composite);
        }
    }

    @Override
    public Component<TabItem> copy() {
        return new TabItem(transform.copy(), dimension.copy(), parentContainer, (Sprite) sprite.copy());
    }


    @Override
    public String name() {
        return getClass().getName();
    }
}
