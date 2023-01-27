package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.GameItemComponent;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.game.components.regions.SnapToGridComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.LevelEditorScene;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuItemComponent extends LevelEditMenuItemComponent<MenuItemComponent> {

    private final SpriteComponent hoverSpriteComponent;

    public MenuItemComponent(Transform transform, Dimension dimension, MenuContainerComponent parentContainer, SpriteComponent spriteComponent, SpriteComponent hoverSpriteComponent) {
        super(transform, dimension, parentContainer, spriteComponent);
        this.hoverSpriteComponent = hoverSpriteComponent;
    }

    @Override
    public void update(double deltaTime) {
        Screen screen = Screen.getInstance();

        // when clicked
        if (screen.mouseListener.mousePressed && screen.mouseListener.mouseButton == MouseEvent.BUTTON1) {
            if (!isSelected && this.gameObject.inMouseBounds()) {

                GameObject object = gameObject.copy(); // copy GameObject

                object.removeComponent(MenuItemComponent.class); // remove the MenuItem component

                object.addComponent(new GameItemComponent(transform.copy(), dimension.copy(), (SpriteComponent) spriteComponent.copy())); // add the Block component

                LevelEditorScene scene = (LevelEditorScene) screen.currentScene; // get Level Editor Scene

                object.addComponent(scene.cursor.getComponent(SnapToGridComponent.class)); // add SnapToGrid component to copied object

                scene.cursor = object; // new Level Editor Scene Mouse Cursor

                isSelected = true;

                this.parentContainer.setFocusedButton(this.gameObject);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        int bufferX = (int) ((this.dimension.width / 2.0f) - (this.gameObject.getComponent(SpriteComponent.class).dimension.width / 2.0f));
        int bufferY = (int) ((this.dimension.height / 2.0f) - (this.gameObject.getComponent(SpriteComponent.class).dimension.height / 2.0f));

        g2d.drawImage(this.spriteComponent.image,
                (int) this.transform.position.x, (int) this.transform.position.y,
                (int) this.dimension.width, (int) this.dimension.height, null);

        g2d.drawImage(this.gameObject.getComponent(SpriteComponent.class).image,
                (int) this.transform.position.x + bufferX, (int) this.transform.position.y + bufferY,
                (int) this.gameObject.getComponent(SpriteComponent.class).dimension.width,
                (int) this.gameObject.getComponent(SpriteComponent.class).dimension.height,
                null);

        if (isSelected) {
            g2d.drawImage(hoverSpriteComponent.image,
                    (int) this.transform.position.x, (int) this.transform.position.y,
                    (int) this.dimension.width, (int) this.dimension.height, null);
        }
    }

    @Override
    public Component<MenuItemComponent> copy() {
        return new MenuItemComponent(transform.copy(), dimension.copy(), parentContainer, (SpriteComponent) spriteComponent.copy(), (SpriteComponent) hoverSpriteComponent.copy());
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
