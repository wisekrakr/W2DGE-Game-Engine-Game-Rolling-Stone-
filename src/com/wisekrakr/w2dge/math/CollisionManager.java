package com.wisekrakr.w2dge.math;

import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.entities.PlayerComponent;
import com.wisekrakr.w2dge.game.components.physics.*;
import com.wisekrakr.w2dge.visual.Screen;

public class CollisionManager {

    public enum HitType {
        TRIANGLE, BOX, GROUND
    }

    public void update(GameObject gameObject) {
        GameObject player = Screen.getScene().player;

        // Collision detection
        BoundsComponent<?> boundsComponent = gameObject.getComponent(BoundsComponent.class);

        if (gameObject != player && boundsComponent != null) {
            if (boundsComponent.checkCollision(player.getComponent(BoundsComponent.class), gameObject.getComponent(BoundsComponent.class))) {

                // Handle player collision with different Game Objects
                // Collision detection for the player with the ground
                if (gameObject.name.equalsIgnoreCase(Tags.GROUND)) {
                    boundsComponent.resolveCollision(gameObject.getComponent(BoundsComponent.class), player, HitType.GROUND);
                }
                // Collision detection for the player with a game item
                else if (gameObject.name.equalsIgnoreCase(Tags.GAME_ITEM)) {
                    if (gameObject.getComponent(BoxBoundsComponent.class) != null) {
                        boundsComponent.resolveCollision(gameObject.getComponent(BoundsComponent.class), player, HitType.BOX);
                    } else if (gameObject.getComponent(TriangleBoundsComponent.class) != null) {
                        boundsComponent.resolveCollision(gameObject.getComponent(BoundsComponent.class), player, HitType.TRIANGLE);
                    }
                }
            }
        }
    }

    public static void collisionResolver(GameObject parent, GameObject player, HitType type) {
        switch (type) {
            case TRIANGLE -> player.getComponent(PlayerComponent.class).reset();
            case BOX -> {
                float dx = parent.getComponent(BoxBoundsComponent.class).center.x -
                        player.getComponent(BoxBoundsComponent.class).center.x;
                float dy = parent.getComponent(BoxBoundsComponent.class).center.y -
                        player.getComponent(BoxBoundsComponent.class).center.y;
                float combinedHalfWidth = player.getComponent(BoxBoundsComponent.class).halfDimension.width +
                        parent.getComponent(BoxBoundsComponent.class).halfDimension.width;
                float combinedHalfHeight = player.getComponent(BoxBoundsComponent.class).halfDimension.height +
                        parent.getComponent(BoxBoundsComponent.class).halfDimension.height;

                // Overlap x- direction and overlap y-direction
                float overlapX = combinedHalfWidth - Math.abs(dx);
                float overlapY = combinedHalfHeight - Math.abs(dy);
                if (overlapX >= overlapY) {
                    if (dy > 0.5f) {
                        // Collision on the bottom of the player
                        player.transform.position.y = parent.transform.position.y - parent.dimension.height;
                        player.getComponent(RigidBodyComponent.class).velocity.y = 0; //stop falling
                        player.getComponent(PlayerComponent.class).grounded = true;
                    } else {
                        // Collision on the top of the player
                        player.getComponent(PlayerComponent.class).reset();
                    }
                    // Collision on the left or the right
                } else {
                    if (dx < 0 && dy <= 0.3f) {
                        player.transform.position.y = parent.transform.position.y - parent.dimension.height;
                        player.getComponent(RigidBodyComponent.class).velocity.y = 0; //stop falling
                        player.getComponent(PlayerComponent.class).grounded = true;
                    } else {
                        // todo elevate blocks
//                        if (parent.getComponent(ElevateComponent.class) != null){
//                            player.getComponent(PlayerComponent.class).elevate = true;
//                        }else{
////                            player.getComponent(PlayerComponent.class).reset();
//                            player.getComponent(PlayerComponent.class).elevate = false;
//                        }

                        player.getComponent(PlayerComponent.class).reset();
                    }
                }
            }
            case GROUND -> {
                player.transform.position.y = parent.transform.position.y -
                        player.getComponent(BoxBoundsComponent.class).dimension.height;
                player.getComponent(PlayerComponent.class).grounded = true;
                player.getComponent(RigidBodyComponent.class).velocity.y = 0;
            }
            default -> System.err.println("Collision Error: not a valid Hit Type: " + type);
        }
    }
}
