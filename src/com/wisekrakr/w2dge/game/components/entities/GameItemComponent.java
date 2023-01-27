package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameItemComponent extends Component<GameItemComponent> {

    private final Transform transform;
    private final Dimension dimension;
    private final SpriteComponent spriteComponent;
    public boolean changeColor = false;

    public GameItemComponent(Transform transform, Dimension dimension, SpriteComponent spriteComponent) {
        this.transform = transform;
        this.dimension = dimension;
        this.spriteComponent = spriteComponent;

    }


    @Override
    public void render(Graphics2D g2d) {
        SpriteComponent sprite = (SpriteComponent) spriteComponent.copy();
        BufferedImage image = sprite.image;
        if (changeColor) {
            Graphics2D g2dd = image.createGraphics();
            g2dd.setColor(Color.GREEN);
            g2dd.fillRect(
                    (int) gameObject.transform.position.x, (int) gameObject.transform.position.y,
                    (int) gameObject.dimension.width, (int) gameObject.dimension.height
            );
            g2dd.drawImage(sprite.image, 0, 0, null);

            changeColor = false;
        }
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
        return getClass().getName();
    }
}
