package com.wisekrakr.w2dge.math;

import com.wisekrakr.w2dge.InterprocessImpl;

public class Dimension implements InterprocessImpl<Dimension> {

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
    public String toString() {
        return "Dimension: {width= " + this.width + " - height= " + this.height+"} \n" +
            "Center: {x=" + this.center.x + " - y= " + this.center.y +"}";
    };

}
