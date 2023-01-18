package com.wisekrakr.w2dge.math;

public class Dimension {

    public int width;
    public final int height;
    public Vector2 center;

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;;
    }

    @Override
    public String toString() {
        return "Dimension: {width= " + this.width + " - height= " + this.height+"} \n" +
            "Center: {x=" + this.center.x + " - y= " + this.center.y +"}";
    };

}
