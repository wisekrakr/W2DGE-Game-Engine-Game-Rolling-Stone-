package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;

import java.awt.*;

public class Block extends Component<Block> {

    private final Transform transform;
    private final Dimension dimension;
    private final Sprite sprite;


    public Block(Transform transform, Dimension dimension, Sprite sprite) {
        this.transform = transform;
        this.dimension = dimension;
        this.sprite = sprite;
    }

    @Override
    public Component<Block> copy() {
        return new Block(transform.copy(), dimension.copy(), (Sprite) sprite.copy());
    }

    @Override
    public String serialize(int tabSize) {

        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty(Names.BLOCK, tabSize)); // Game Object

        builder.append(transform.serialize(tabSize + 1)); // Transform
        builder.append(addEnding(true, true));

        builder.append(dimension.serialize(tabSize + 1)); // Dimension
        builder.append(addEnding(true, true));

        builder.append(sprite.serialize(tabSize + 1)); // Sprite
        builder.append(addEnding(true, true));

        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static Block deserialize() {

        Transform t = Transform.deserialize();
        Parser.consume(',');

        Dimension d = Dimension.deserialize();
        Parser.consume(',');

        Parser.consumeBeginObjectProperty("Sprite");
        Sprite s = Sprite.deserialize();
        Parser.consume(',');

        Parser.consumeEndObjectProperty();

        return new Block(t, d, s);
    }

    public void changeColor() {
        Graphics2D g2d = sprite.image.createGraphics();

        g2d.drawImage(
                this.sprite.image,
                (int) this.gameObject.transform.position.x, (int) this.gameObject.transform.position.y,
                (int) this.dimension.width, (int) this.dimension.height,
                null
        );
        g2d.setColor(Colors.synthWaveRed);
        g2d.fillRect((int) this.gameObject.transform.position.x, (int) this.gameObject.transform.position.y,
                (int) this.dimension.width, (int) this.dimension.height);
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
