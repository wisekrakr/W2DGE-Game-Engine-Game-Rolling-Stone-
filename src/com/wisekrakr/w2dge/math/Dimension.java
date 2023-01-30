package com.wisekrakr.w2dge.math;

import com.wisekrakr.w2dge.InterprocessImpl;
import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.data.Serializable;

import java.beans.Transient;

public class Dimension extends Serializable implements InterprocessImpl<Dimension> {

    public float width;
    public float height;

    public Dimension() {
        this(0, 0);
    }

    public Dimension(float width, float height) {
        this.width = width;
        this.height = height;
        ;
    }

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setSize(double width, double height) {
        this.width = (int) Math.ceil(width);
        this.height = (int) Math.ceil(height);
    }

    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Transient
    public Dimension getSize() {
        return new Dimension(width, height);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dimension d) {
            return (width == d.width) && (height == d.height);
        }
        return false;

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
        return "Dimension: {width= " + this.width + " - height= " + this.height + "} \n";
//                +
//            "Center: {x=" + this.center.x + " - y= " + this.center.y +"}";
    }

    ;

}
