package com.wisekrakr.w2dge.game.loops;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;

import java.awt.*;

public class Graphics implements GameLoopImpl {

    private final GameObject parent;
    public SpriteComponent layerOne;
    public SpriteComponent layerTwo;
    public SpriteComponent layerThree;
    private final Color colorOne;
    private final Color colorTwo;
    private final SpriteComponent[] layers;

    public Graphics(GameObject parent, SpriteComponent layerOne, SpriteComponent layerTwo, SpriteComponent layerThree, Color colorOne, Color colorTwo) {
        this.parent = parent;
        this.layerOne = layerOne;
        this.layerTwo = layerTwo;
        this.layerThree = layerThree;
        this.colorOne = colorOne;
        this.colorTwo = colorTwo;
        this.layers = new SpriteComponent[]{this.layerOne, this.layerTwo, this.layerThree};

        init();
    }

    @Override
    public void init() {
        int threshold = 150;
        for (int x = 0; x < layerOne.image.getWidth(); x++) {
            for (int y = 0; y < layerOne.image.getHeight(); y++) {
                Color color = new Color(layerOne.image.getRGB(x, y));
                if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold) {
                    layerOne.image.setRGB(x, y, colorOne.getRGB());
                }
            }
        }
        for (int x = 0; x < layerTwo.image.getWidth(); x++) {
            for (int y = 0; y < layerTwo.image.getHeight(); y++) {
                Color color = new Color(layerTwo.image.getRGB(x, y));
                if (color.getRed() > threshold && color.getGreen() > threshold && color.getBlue() > threshold) {
                    layerTwo.image.setRGB(x, y, colorTwo.getRGB());
                }
            }
        }
    }

    @Override
    public void update(double deltaTime) {

    }


    @Override
    public void render(Graphics2D g2d) {
        for (SpriteComponent layer : layers) {
            g2d.drawImage(layer.image, this.parent.transform(0,0), null);
        }
    }

    @Override
    public void terminate() {

    }

}
