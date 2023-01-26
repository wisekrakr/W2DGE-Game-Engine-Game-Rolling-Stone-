package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.Block;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.game.components.regions.SnapToGrid;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.LevelEditorScene;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuItem extends LevelEditMenuItem<MenuItem> {

    private final Sprite hoverSprite;

    public MenuItem(Transform transform, Dimension dimension, LevelEditMenuContainer parentContainer, Sprite sprite, Sprite hoverSprite) {
        super(transform, dimension, parentContainer, sprite);
        this.hoverSprite = hoverSprite;
    }

    @Override
    public void update(double deltaTime) {
        Screen screen = Screen.getInstance();

        // when clicked
        if (screen.mouseListener.mousePressed && screen.mouseListener.mouseButton == MouseEvent.BUTTON1) {
            if (!isSelected && this.gameObject.inMouseBounds()) {

                GameObject object = gameObject.copy(); // copy GameObject

                object.removeComponent(MenuItem.class); // remove the MenuItem component

                object.addComponent(new Block(transform.copy(), dimension.copy(), (Sprite) sprite.copy())); // add the Block component

                LevelEditorScene scene = (LevelEditorScene) screen.getCurrentScene(); // get Level Editor Scene

                object.addComponent(scene.cursor.getComponent(SnapToGrid.class)); // add SnapToGrid component to copied object

                scene.cursor = object; // new Level Editor Scene Mouse Cursor

                isSelected = true;

                this.parentContainer.setFocusedButton(this.gameObject);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        int bufferX = (int) ((this.dimension.width / 2.0f) - (this.gameObject.getComponent(Sprite.class).dimension.width / 2.0f));
        int bufferY = (int) ((this.dimension.height / 2.0f) - (this.gameObject.getComponent(Sprite.class).dimension.height / 2.0f));

        g2d.drawImage(this.sprite.image,
                (int) this.transform.position.x, (int) this.transform.position.y,
                (int) this.dimension.width, (int) this.dimension.height, null);

        g2d.drawImage(this.gameObject.getComponent(Sprite.class).image,
                (int) this.transform.position.x + bufferX, (int) this.transform.position.y + bufferY,
                (int) this.gameObject.getComponent(Sprite.class).dimension.width,
                (int) this.gameObject.getComponent(Sprite.class).dimension.height,
                null);

        if (isSelected) {
            g2d.drawImage(hoverSprite.image,
                    (int) this.transform.position.x, (int) this.transform.position.y,
                    (int) this.dimension.width, (int) this.dimension.height, null);
        }
    }

    @Override
    public Component<MenuItem> copy() {
        return new MenuItem(transform.copy(), dimension.copy(), parentContainer, (Sprite) sprite.copy(), (Sprite) hoverSprite.copy());
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
