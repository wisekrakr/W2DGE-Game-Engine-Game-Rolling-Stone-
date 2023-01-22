package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;


public abstract class Bounds extends Component<Bounds> {

    public enum BoundsType{
        BOX, TRIANGLE
    }

    public BoundsType type;

    public abstract Dimension dimension();

    public static boolean collision(Bounds b1, Bounds b2){
        if (b1.type.equals(b2.type)){
            switch (b1.type){
                case BOX:
                    return BoxBounds.collision(b1, b2);
                case TRIANGLE:
                    break;
                default:
                    System.err.println("Collision error: '" + b1.type + "' is not a valid Bounds type");
            }
        }
        return false;
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
