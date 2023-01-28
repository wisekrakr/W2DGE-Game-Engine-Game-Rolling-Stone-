package com.wisekrakr.util;

import com.wisekrakr.w2dge.constants.Colors;

import java.awt.image.BufferedImage;

public class ImageUtils {

    public static void changeColor(BufferedImage image){
        // Get the width and height of the image
        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, Colors.synthWaveRed.getRGB());

            }
        }

        // Iterate through all pixels
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                // Get the RGB value of the pixel
//                int rgb = image.getRGB(x, y);
//
//                // Extract the red, green, and blue components
//                int red = (rgb >> 16) & 0xff;
//                int green = (rgb >> 8) & 0xff;
//                int blue = rgb & 0xff;
//
//                // Change the color of the pixel
//                red = 255 - red;
//                green = 255 - green;
//                blue = 255 - blue;
//
//                // or change to a specific colornew Color(255, 41, 117);
//                 red = 255;
//                 green = 41;
//                 blue = 117;
//
//                // Set the new RGB value of the pixel
//                rgb = (red << 16) | (green << 8) | blue;
//                image.setRGB(x, y, rgb);
//            }
//        }
    }
}
