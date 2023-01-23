package com.wisekrakr.w2dge.math;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.entities.Player;
import com.wisekrakr.w2dge.game.components.physics.Bounds;
import com.wisekrakr.w2dge.game.components.physics.BoxBounds;
import com.wisekrakr.w2dge.game.components.physics.RigidBody;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.awt.*;
import java.util.List;

public class CollisionManager implements GameLoopImpl {

    public enum HitType {
        SPIKE, BOX, GROUND
    }

    private final List<GameObject> gameObjects;

    public CollisionManager(List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(double deltaTime) {
        Scene scene = Screen.getInstance().getCurrentScene();
        GameObject player = scene.player;

        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);

            // Camera follows value of toFollow
            scene.cameraFollow(gameObject);

            // Collision detection
            Bounds<?> bounds = gameObject.getComponent(Bounds.class);

            if (gameObject != player && bounds != null) {
                if (bounds.checkCollision(player.getComponent(Bounds.class), bounds)) {

                    // Handle player collision with different Game Objects

                    // Collision detection for the player with the ground
                    if (gameObject.name.equalsIgnoreCase(Tags.GROUND)) {
                        bounds.resolveCollision(bounds, player, HitType.GROUND);
                    }
                    // Collision detection for the player with a block
                    else if (gameObject.name.equalsIgnoreCase(Tags.BLOCK)) {
                        bounds.resolveCollision(bounds, player, HitType.BOX);
                    }
                }
            }
        }
    }


    public static void collisionResolver(GameObject parent, GameObject player, HitType type) {
        switch (type) {

            case SPIKE:
                break;
            case BOX:
                float dx = parent.getComponent(BoxBounds.class).center.x -
                        player.getComponent(BoxBounds.class).center.x;
                float dy = parent.getComponent(BoxBounds.class).center.y -
                        player.getComponent(BoxBounds.class).center.y;

                float combinedHalfWidth = player.getComponent(BoxBounds.class).halfDimension.width +
                        parent.getComponent(BoxBounds.class).halfDimension.width;
                float combinedHalfHeight = player.getComponent(BoxBounds.class).halfDimension.height +
                        parent.getComponent(BoxBounds.class).halfDimension.height;

                // Overlap x- direction and overlap y-direction
                float overlapX = combinedHalfWidth - Math.abs(dx);
                float overlapY = combinedHalfHeight - Math.abs(dy);

                if (overlapX >= overlapY) {
                    if (dy > 0) {
                        // Collision on the bottom of the player
                        // Collision on the top of the player
                        player.transform.position.y = parent.transform.position.y - parent.dimension.height;
                        player.getComponent(RigidBody.class).velocity.y = 0; //stop falling
                        player.getComponent(Player.class).grounded = true;
                    } else {
                        // Collision on the bottom of the player
                        player.getComponent(Player.class).die();
                    }
                    // Collision on the left or the right
                } else {
                    if (dx < 0 && dy <= 0.3){
                        player.transform.position.y = parent.transform.position.y - parent.dimension.height;
                        player.getComponent(RigidBody.class).velocity.y = 0; //stop falling
                        player.getComponent(Player.class).grounded = true;
                    }else {
                        player.getComponent(Player.class).die();
                    }
                }
                break;
            case GROUND:
                player.transform.position.y = parent.transform.position.y -
                        player.getComponent(BoxBounds.class).dimension.height;

                player.getComponent(Player.class).grounded = true;
                player.getComponent(RigidBody.class).velocity.y = 0;
                break;
            default:
                System.err.println("Collision Error: not a valid Hit Type: " + type);
        }
    }


    @Override
    public void render(Graphics2D g2d) {

    }

    @Override
    public void terminate() {

    }
}
