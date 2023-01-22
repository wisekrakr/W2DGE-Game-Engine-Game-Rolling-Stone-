package com.wisekrakr.w2dge.math;

import com.wisekrakr.w2dge.InterprocessImpl;
import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.data.Serializable;

public class Dimension extends Serializable implements  InterprocessImpl<Dimension> {

    public int width;
    public int height;
    public Vector2 center;

    public Dimension() {
    }

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;;
    }

    @Override
    public Dimension copy() {
        Dimension dimension = new Dimension(this.width, this.height);
        if (dimension.center != null){
            dimension.center = this.center.copy();
        }
        return dimension;
    }

    @Override
    public String serialize(int tabSize) {
        return beginObjectProperty("Dimension", tabSize) +
                addIntProperty("width", width, tabSize + 1, true, true) +
                addIntProperty("height", height, tabSize + 1, true, false) +
                closeObjectProperty(tabSize);
    }


    public static Dimension deserialize() {
        Parser.consumeBeginObjectProperty("Dimension");

        int width = Parser.consumeIntProperty("width");
        Parser.consume(',');

        int height = Parser.consumeIntProperty("height");
        Parser.consumeEndObjectProperty();

        Dimension dimension = new Dimension(width, height);
        dimension.width = width;
        dimension.height = height;

        return dimension;
    }

    @Override
    public String toString() {
        return "Dimension: {width= " + this.width + " - height= " + this.height+"} \n" +
            "Center: {x=" + this.center.x + " - y= " + this.center.y +"}";
    };

}
