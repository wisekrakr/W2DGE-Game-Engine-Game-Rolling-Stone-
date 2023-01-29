package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.LevelEditorScene;

import java.awt.*;

public class MenuItemComponent extends LevelEditMenuItemComponent<MenuItemComponent> {

    public final SpriteComponent hoverSpriteComponent;

    public MenuItemComponent(Transform transform, Dimension dimension, MenuContainerComponent parentContainer, SpriteComponent spriteComponent, SpriteComponent hoverSpriteComponent) {
        super(transform, dimension, parentContainer, spriteComponent);
        this.hoverSpriteComponent = hoverSpriteComponent;
    }

    @Override
    public void update(double deltaTime) {

        // when clicked
        if (Screen.getInputListener().leftMousePressed()) {
            if (!isSelected && this.gameObject.inMouseBounds()) {

                GameObject object = gameObject.copy(); // copy GameObject

                object.removeComponent(MenuItemComponent.class); // remove the MenuItem component

                LevelEditorScene scene = (LevelEditorScene) Screen.getScene(); // get Level Editor Scene

                object.addComponent(scene.levelEditMouseCursor.getComponent(MenuItemControlComponent.class)); // add SnapToGrid component to copied object

                scene.levelEditMouseCursor = object; // new Level Editor Scene Mouse Cursor

                isSelected = true;

                this.parentContainer.setFocusedButton(this.gameObject);
            }
        }

        if (Screen.getInputListener().escapePressed()){
            isSelected = false;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        int bufferX = (int) ((this.dimension.width / 2.0f) - (this.gameObject.getComponent(SpriteComponent.class).dimension.width / 2.0f));
        int bufferY = (int) ((this.dimension.height / 2.0f) - (this.gameObject.getComponent(SpriteComponent.class).dimension.height / 2.0f));

        Screen.getScene().getRenderer().drawImage(g2d, this.spriteComponent.image, this.gameObject,0,0);
        Screen.getScene().getRenderer().drawImage(
                g2d, this.gameObject.getComponent(SpriteComponent.class).image, this.gameObject, bufferX, bufferY);

//        g2d.drawImage(this.spriteComponent.image,
//                (int) this.transform.position.x, (int) this.transform.position.y,
//                (int) this.dimension.width, (int) this.dimension.height, null);
//
//        g2d.drawImage(this.gameObject.getComponent(SpriteComponent.class).image,
//                (int) this.transform.position.x + bufferX, (int) this.transform.position.y + bufferY,
//                (int) this.gameObject.getComponent(SpriteComponent.class).dimension.width,
//                (int) this.gameObject.getComponent(SpriteComponent.class).dimension.height,
//                null);

        if (isSelected) {
            Screen.getScene().getRenderer().drawImage(g2d, hoverSpriteComponent.image, this.gameObject,0,0);
//            g2d.drawImage(hoverSpriteComponent.image,
//                    (int) this.transform.position.x, (int) this.transform.position.y,
//                    (int) this.dimension.width, (int) this.dimension.height, null);

        }
    }

    @Override
    public Component<MenuItemComponent> copy() {
        return new MenuItemComponent(transform.copy(), dimension.copy(), parentContainer, (SpriteComponent) spriteComponent.copy(), (SpriteComponent) hoverSpriteComponent.copy());
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
