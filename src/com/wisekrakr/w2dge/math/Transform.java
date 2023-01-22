package com.wisekrakr.w2dge.math;

import com.wisekrakr.w2dge.InterprocessImpl;
import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.data.Serializable;

public class Transform extends Serializable implements  InterprocessImpl<Transform> {
    public Vector2 position;
    public float rotation;
    public Vector2 scale;

    public Transform(Vector2 position) {
        this.position = position;
        this.scale = new Vector2(1.0f, 1.0f);
        this.rotation = 0.0f;
    }

    @Override
    public Transform copy() {
        Transform transform = new Transform(this.position.copy());
        transform.scale = this.scale.copy();
        transform.rotation = this.rotation;
        return transform;
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Transform", tabSize));

        builder.append(beginObjectProperty("Position", tabSize + 1));
        builder.append(position.serialize(tabSize + 2));
        builder.append(closeObjectProperty(tabSize + 1));
        builder.append(addEnding(true, true));

        builder.append(beginObjectProperty("Scale", tabSize + 1));
        builder.append(scale.serialize(tabSize + 2));
        builder.append(closeObjectProperty(tabSize + 1));
        builder.append(addEnding(true, true));

        builder.append(addFloatProperty("rotation", rotation, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }


    public static Transform deserialize() {
        Parser.consumeBeginObjectProperty("Transform");

        Parser.consumeBeginObjectProperty("Position");
        Vector2 position = Vector2.deserialize();
        Parser.consumeEndObjectProperty();

        Parser.consume(',');

        Parser.consumeBeginObjectProperty("Scale");
        Vector2 scale = Vector2.deserialize();
        Parser.consumeEndObjectProperty();

        Parser.consume(',');

        float rotation = Parser.consumeFloatProperty("rotation");
        Parser.consumeEndObjectProperty();

        Transform transform = new Transform(position);
        transform.scale = scale;
        transform.rotation = rotation;

        return transform;
    }

    @Override
    public String toString() {
        return "Transform{" +
                "position=" + position +
                ", rotation=" + rotation +
                ", scale=" + scale +
                '}';
    }
}
