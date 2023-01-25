package com.wisekrakr.w2dge.math;

import com.wisekrakr.w2dge.InterprocessImpl;
import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.data.Serializable;

public class Dimension extends Serializable implements  InterprocessImpl<Dimension> {

    public float width;
    public float height;
//    public Vector2 center;

    public Dimension() {
    }

    public Dimension(float width, float height) {
        this.width = width;
        this.height = height;;
    }

    @Override
    public Dimension copy() {
        return new Dimension(this.width, this.height);
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty(Names.DIMENSION, tabSize));
        builder.append(addFloatProperty("width", width, tabSize + 1, true, true));
        builder.append(addFloatProperty("height", height, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static Dimension deserialize() {
        Parser.consumeBeginObjectProperty(Names.DIMENSION);
        float width = Parser.consumeFloatProperty("width");
        Parser.consume(',');
        float height = Parser.consumeFloatProperty("height");
        Parser.consumeEndObjectProperty();

        return new Dimension(width, height);
    }

    @Override
    public String toString() {
        return "Dimension: {width= " + this.width + " - height= " + this.height+"} \n" ;
//                +
//            "Center: {x=" + this.center.x + " - y= " + this.center.y +"}";
    };

}
