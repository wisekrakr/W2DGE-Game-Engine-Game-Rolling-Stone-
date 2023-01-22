package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.game.components.regions.SnapToGrid;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.LevelEditorScene;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuItem extends Component<MenuItem> {

    private final Transform transform;
    private final Dimension dimension;
    private final Sprite sprite;
    private final Sprite hoverSprite;
    private Sprite subSprite;
    public boolean isClicked;
    private int bufferX, bufferY; // menu item margin

    public MenuItem(Transform transform, Dimension dimension, Sprite sprite, Sprite hoverSprite) {
        this.transform = transform;
        this.dimension = dimension;
        this.sprite = sprite;
        this.hoverSprite = hoverSprite;
        this.isClicked = false;
    }

    @Override
    public void init() {
        subSprite = gameObject.getComponent(Sprite.class);

        this.bufferX = (int)((this.dimension.width / 2.0f) - (this.subSprite.dimension.width / 2.0f));
        this.bufferY = (int)((this.dimension.height / 2.0f) - (this.subSprite.dimension.height / 2.0f));
    }

    @Override
    public void update(double deltaTime) {
        Screen screen = Screen.getInstance();
        if (! isClicked && this.gameObject.inMouseBounds()) {
            // when clicked
            if (screen.mouseListener.mousePressed && screen.mouseListener.mouseButton == MouseEvent.BUTTON1){
                GameObject object = gameObject.copy(); // copy GameObject

                object.removeComponent(MenuItem.class); // remove the MenuItem component

                LevelEditorScene scene = (LevelEditorScene) screen.getCurrentScene(); // get Level Editor Scene

                object.addComponent(scene.cursor.getComponent(SnapToGrid.class)); // add SnapToGrid component to copied object

                scene.cursor = object; // new Level Editor Scene Mouse Cursor

                isClicked = true;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.sprite.image,
                (int)this.transform.position.x, (int)this.transform.position.y,
                this.dimension.width, this.dimension.height, null);

        g2d.drawImage(this.subSprite.image,
                (int)this.transform.position.x + bufferX, (int)this.transform.position.y + bufferY,
                this.subSprite.dimension.width, this.subSprite.dimension.height, null);

        if (isClicked) {
            g2d.drawImage(hoverSprite.image,
                    (int)this.transform.position.x, (int)this.transform.position.y,
                    this.subSprite.dimension.width, this.subSprite.dimension.height, null);
        }
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
