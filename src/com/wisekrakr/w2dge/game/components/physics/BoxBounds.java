package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;


public class BoxBounds extends Bounds {

    public Dimension dimension;

    public BoxBounds(Dimension dimension) {
        this.dimension = dimension;
    }

    public static boolean collision(Bounds b1, Bounds b2){
        return false;
    }

    @Override
    public Dimension dimension() {
        return this.dimension;
    }

    @Override
    public Component<Bounds> copy() {
        return new BoxBounds(dimension.copy());
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
