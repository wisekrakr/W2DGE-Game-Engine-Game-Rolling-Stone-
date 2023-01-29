package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;

import java.awt.*;

public class GameItemComponent extends Component<GameItemComponent> {

    private final Transform transform;
    private final Dimension dimension;
    private final SpriteComponent spriteComponent;
    private int[][] originalRGB;
    public boolean changeColor = false;
    private int width, height;

    public GameItemComponent(Transform transform, Dimension dimension, SpriteComponent spriteComponent) {
        this.transform = transform;
        this.dimension = dimension;
        this.spriteComponent = spriteComponent;
    }

    @Override
    public void init() {
        // Get the width and height of the image
        width = spriteComponent.image.getWidth();
        height = spriteComponent.image.getHeight();

        // Store the original RGB values in a separate array
        originalRGB = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                originalRGB[x][y] = spriteComponent.image.getRGB(x, y);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
//        AlphaComposite composite;
//        if (changeColor) {
//            SpriteComponent spriteComponent = gameObject.getComponent(SpriteComponent.class);
//            if (spriteComponent != null) {
////                BufferedImage image = spriteComponent.image;
////                ImageUtils.changeColor(image,this,  Colors.babyBlue);
//
//
////                composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f); // transparent until placement
////                g2d.setComposite(composite);
////                g2d.drawImage(spriteComponent.image,
////                        (int) gameObject.transform.position.x, (int) gameObject.transform.position.y,
////                        (int) gameObject.dimension.width, (int) gameObject.dimension.height,
////                        null
////                );
////            }
//        }else{
//            // Reset the image back to its original state
////            for (int x = 0; x < width; x++) {
////                for (int y = 0; y < height; y++) {
////                    spriteComponent.image.setRGB(x, y, originalRGB[x][y]);
////                }
////            }
//
////            composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
////            g2d.setComposite(composite);
//        }
    }

    @Override
    public Component<GameItemComponent> copy() {
        return new GameItemComponent(transform.copy(), dimension.copy(), (SpriteComponent) spriteComponent.copy());
    }

    @Override
    public String serialize(int tabSize) {

        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty(Names.GAME_ITEM, tabSize)); // Game Object

        builder.append(transform.serialize(tabSize + 1)); // Transform
        builder.append(addEnding(true, true));

        builder.append(dimension.serialize(tabSize + 1)); // Dimension
        builder.append(addEnding(true, true));

        builder.append(spriteComponent.serialize(tabSize + 1)); // Sprite
        builder.append(addEnding(true, true));

        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static GameItemComponent deserialize() {

        Transform t = Transform.deserialize();
        Parser.consume(',');

        Dimension d = Dimension.deserialize();
        Parser.consume(',');

        Parser.consumeBeginObjectProperty(Names.SPRITE);
        SpriteComponent s = SpriteComponent.deserialize();
        Parser.consume(',');

        Parser.consumeEndObjectProperty();

        return new GameItemComponent(t, d, s);
    }


    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
