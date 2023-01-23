package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.CollisionManager;


public abstract class Bounds<T> extends Component<T> implements BoundsImpl {

    public enum BoundsType {
        BOX, TRIANGLE
    }

    public BoundsType type;

    @Override
    public void init() {
        calculateCenter();
    }

    @Override
    public boolean checkCollision(Bounds<?> b1, Bounds<?> b2) {
        if (b1.type.equals(b2.type)) {
            switch (b1.type) {
                case BOX:
                    return collision((BoxBounds) b1, (BoxBounds) b2);
                case TRIANGLE:
                    break;
                default:
                    System.err.println("Collision error: '" + b1.type + "' is not a valid Bounds type");
            }
        }
        return false;
    }

    @Override
    public void resolveCollision(Bounds<?> bounds, GameObject player, CollisionManager.HitType type) {
        if (bounds.type == BoundsType.BOX) {
            BoxBounds box = (BoxBounds) bounds;
            box.resolveColliding(player, type);
        }
    }

    /**
     * Centers the component, so all physics will go through the middle of this component (x-0 and y-0 are in the center)
     */
    protected abstract void calculateCenter();

    @Override
    public String name() {
        return getClass().getName();
    }
}
