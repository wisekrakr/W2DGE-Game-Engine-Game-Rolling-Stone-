package com.wisekrakr.util;

import com.wisekrakr.w2dge.game.components.entities.GameItemComponent;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {



    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // Create a buffered image with a default color model
        BufferedImage newImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Copy image to buffered image
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return newImage;
    }


    public static void changeColor(BufferedImage image, GameItemComponent gameItemComponent, Color color) {
        if (gameItemComponent.gameObject.getComponent(BoxBoundsComponent.class)!=null){
            // Get the width and height of the image
            int width = image.getWidth();
            int height = image.getHeight();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }else if (gameItemComponent.gameObject.getComponent(TriangleBoundsComponent.class)!=null){
            TriangleBoundsComponent component = gameItemComponent.gameObject.getComponent(TriangleBoundsComponent.class);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(color);

            Polygon triangle = new Polygon();
            triangle.addPoint((int) component.x1, (int) component.y1);
            triangle.addPoint((int) component.x2, (int) component.y2);
            triangle.addPoint((int) component.x3, (int) component.y3);

            g2d.fill(triangle);
        }


    }

    public static void changeColor(BufferedImage image, Color color) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Iterate through all pixels
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Get the RGB value of the pixel
                int rgb = image.getRGB(x, y);

                // Extract the red, green, and blue components
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;

                // Change to a specific color
                red = color.getRed() - red;
                green = color.getGreen() - green;
                blue = color.getBlue() - blue;

                // Set the new RGB value of the pixel
                rgb = (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, rgb);
            }
        }
    }

}
