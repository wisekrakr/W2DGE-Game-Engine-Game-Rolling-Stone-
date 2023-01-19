package com.wisekrakr.w2dge.ui;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuItem extends Component<MenuItem> {

    public Transform transform;
    public Dimension dimension;
    public Sprite sprite;
    public Sprite hoverSprite;
    public Sprite currentImage;
    public boolean isClicked;

    public MenuItem(Transform transform, Dimension dimension, Sprite sprite, Sprite hoverSprite) {
        this.transform = transform;
        this.dimension = dimension;
        this.sprite = sprite;
        this.hoverSprite = hoverSprite;
        this.isClicked = false;
    }

    @Override
    public void init() {
        currentImage = gameObject.getComponent(Sprite.class);
    }

    @Override
    public void update(double deltaTime) {
        Screen screen = Screen.getInstance();
        if (! isClicked && this.gameObject.inMouseBounds()) {
            // if clicked
            if (screen.mouseListener.mousePressed && screen.mouseListener.mouseButton == MouseEvent.BUTTON1){
                isClicked = true;
                System.out.println("Clicked!");
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.sprite.image,
                (int)this.transform.position.x, (int)this.transform.position.y,
                this.dimension.width, this.dimension.height, null);

        g2d.drawImage(this.currentImage.image,
                (int)this.transform.position.x, (int)this.transform.position.y,
                this.currentImage.dimension.width, this.currentImage.dimension.height, null);

        if (isClicked) {
            g2d.drawImage(hoverSprite.image,
                    (int)this.transform.position.x, (int)this.transform.position.y,
                    this.dimension.width, this.dimension.height, null);
        }
    }

    @Override
    public Component<MenuItem> copy() {
        return null;
    }
}
