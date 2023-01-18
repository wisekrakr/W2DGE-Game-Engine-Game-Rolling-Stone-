package com.wisekrakr.w2dge.game.components.graphics;

import com.wisekrakr.w2dge.game.components.Component;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Graphics extends Component<Graphics> {

    public Sprite layerOne, layerTwo, layerThree;
    private final Sprite[] layers;

    public Graphics(Sprite layerOne, Sprite layerTwo, Sprite layerThree, Color colorOne, Color colorTwo) {
        this.layerOne = layerOne;
        this.layerTwo = layerTwo;
        this.layerThree = layerThree;
        this.layers = new Sprite[]{this.layerOne, this.layerTwo, this.layerThree};

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
    public void init() {
        super.init();
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
        affineTransform.translate(gameObject.transform.position.x, gameObject.transform.position.y);
        affineTransform.rotate(gameObject.transform.rotation, gameObject.dimension.center.x, gameObject.dimension.center.y);
        affineTransform.scale(gameObject.transform.scale.x, gameObject.transform.scale.y);

        return affineTransform;
    }


    @Override
    public void render(Graphics2D g2d) {
        for (Sprite layer : layers) {
            g2d.drawImage(layer.image, transform(), null);
        }
    }
}
