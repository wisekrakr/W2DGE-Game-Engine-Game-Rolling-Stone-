package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Vector2;

public class RigidBodyComponent extends Component<RigidBodyComponent> {

    public Vector2 velocity;

    public RigidBodyComponent(Vector2 velocity) {
        this.velocity = velocity;
    }

    @Override
    public void update(double deltaTime) {
        gameObject.transform.position.x += velocity.x * deltaTime;
        gameObject.transform.position.y += velocity.y * deltaTime;

//        velocity.x += Dimensions.GRAVITY * deltaTime;
        velocity.y += GameConstants.GRAVITY * deltaTime;

        if (Math.abs(velocity.y) > GameConstants.TERMINAL_VELOCITY){
            velocity.y = Math.signum(velocity.y) * GameConstants.TERMINAL_VELOCITY; // either positive or negative
        }
    }

    @Override
    public Component<RigidBodyComponent> copy() {
        return new RigidBodyComponent(velocity.copy());
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}