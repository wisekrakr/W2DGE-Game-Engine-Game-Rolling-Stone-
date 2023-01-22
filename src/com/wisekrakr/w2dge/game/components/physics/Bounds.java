package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.components.Component;


public abstract class Bounds extends Component<Bounds> {

    public enum BoundsType{
        BOX, TRIANGLE
    }

    public BoundsType type;

    public static boolean collision(Bounds b1, Bounds b2){
        if (b1.type.equals(b2.type)){
            switch (b1.type){
                case BOX:
                    return BoxBounds.collision((BoxBounds) b1, (BoxBounds) b2);
                case TRIANGLE:
                    break;
                default:
                    System.err.println("Collision error: '" + b1.type + "' is not a valid Bounds type");
            }
        }
        return false;
    }

    /**
     * Centers the component, so all physics will go through the middle of this component (x-0 and y-0 are in the center)
     */
    public abstract void calculateCenter();

    @Override
    public String name() {
        return getClass().getName();
    }
}
