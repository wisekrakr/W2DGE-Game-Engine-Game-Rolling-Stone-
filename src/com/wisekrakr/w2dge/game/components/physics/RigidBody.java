package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Vector2;

public class RigidBody extends Component<RigidBody> {

    public Vector2 velocity;

    public RigidBody(Vector2 velocity) {
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
    public Component<RigidBody> copy() {
        return new RigidBody(velocity.copy());
    }
}
