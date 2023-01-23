package com.wisekrakr.w2dge.game.player;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Graphics implements GameLoopImpl {

    private final GameObject parent;
    public Sprite layerOne;
    public Sprite layerTwo;
    public Sprite layerThree;
    private final Color colorOne;
    private final Color colorTwo;
    private final Sprite[] layers;

    public Graphics(GameObject parent, Sprite layerOne, Sprite layerTwo, Sprite layerThree, Color colorOne, Color colorTwo) {
        this.parent = parent;
        this.layerOne = layerOne;
        this.layerTwo = layerTwo;
        this.layerThree = layerThree;
        this.colorOne = colorOne;
        this.colorTwo = colorTwo;
        this.layers = new Sprite[]{this.layerOne, this.layerTwo, this.layerThree};

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

    /**
     * A method that helps with drawing a {@link com.wisekrakr.w2dge.game.GameObject} rotation. <br>
     * We also set the rotation of a GameObjects with this method.
     *
     * @return {@link AffineTransform}
     */
    private AffineTransform transform() {

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToIdentity(); // reset transform
        affineTransform.translate(parent.transform.position.x, parent.transform.position.y);
//        affineTransform.rotate(parent.transform.rotation, parent.dimension.center.x, parent.dimension.center.y);
        affineTransform.rotate(
                parent.transform.rotation,
                parent.dimension.width * parent.transform.scale.x / 2.0f,
                parent.dimension.height * parent.transform.scale.y / 2.0f
        );
        affineTransform.scale(parent.transform.scale.x, parent.transform.scale.y);

        return affineTransform;
    }


    @Override
    public void render(Graphics2D g2d) {
        for (Sprite layer : layers) {
            g2d.drawImage(layer.image, transform(), null);
        }
    }

    @Override
    public void terminate() {

    }

}
