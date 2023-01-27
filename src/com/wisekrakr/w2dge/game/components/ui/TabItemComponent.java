package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TabItemComponent extends LevelEditMenuItemComponent<TabItemComponent> {

    public TabItemComponent(Transform transform, Dimension dimension, MenuContainerComponent parentContainer, SpriteComponent spriteComponent) {
        super(transform, dimension, parentContainer, spriteComponent);
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
            g2d.drawImage(spriteComponent.image, (int)transform.position.x,(int) transform.position.y,
                    (int)dimension.width,(int) dimension.height,null);
        }else {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f); // transparent until placement
            g2d.setComposite(composite);
            g2d.drawImage(spriteComponent.image,
                    (int) gameObject.transform.position.x, (int) gameObject.transform.position.y,
                    (int) gameObject.dimension.width, (int) gameObject.dimension.height,
                    null
            );
            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2d.setComposite(composite);
        }
    }

    @Override
    public Component<TabItemComponent> copy() {
        return new TabItemComponent(transform.copy(), dimension.copy(), parentContainer, (SpriteComponent) spriteComponent.copy());
    }


    @Override
    public String name() {
        return getClass().getName();
    }
}
