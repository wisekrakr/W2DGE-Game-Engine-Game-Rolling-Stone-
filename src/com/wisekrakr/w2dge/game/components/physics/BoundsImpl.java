package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.math.CollisionManager;

public interface BoundsImpl {

    /**
     * {@link Bounds} method. Checks all extended Bound classes collision
     * @param b1 First object with Bounds Component
     * @param b2 Second object with Bounds Component
     * @return true if colliding
     */
    boolean checkCollision(Bounds<?> b1, Bounds<?> b2);

    /**
     * Handles collisions in child class of Bounds
     * @param b1 First object with BoxBound Component
     * @param b2 Second object with BoxBound Component
     * @return true if colliding
     */
    boolean collision(BoxBounds b1, BoxBounds b2);

    /**
     * {@link Bounds} method. Handles resolving all collisions of child classes of Bounds
     *
     * @param bounds Bounds of GameObject that is not the Player
     * @param player Player GameObject
     * @param box
     */
    void resolveCollision(Bounds<?> bounds, GameObject player, CollisionManager.HitType box);

    /**
     * Handles colliding logic of a child class of {@link Bounds}
     *
     * @param player GameObject
     * @param type
     */
    void resolveColliding(GameObject player, CollisionManager.HitType type);
}
