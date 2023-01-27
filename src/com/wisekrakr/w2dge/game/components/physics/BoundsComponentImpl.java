package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.math.CollisionManager;

public interface BoundsComponentImpl {

    /**
     * {@link BoundsComponent} method. Checks all extended Bound classes collision
     * @param b1 First object with Bounds Component
     * @param b2 Second object with Bounds Component
     * @return true if colliding
     */
    boolean checkCollision(BoundsComponent<?> b1, BoundsComponent<?> b2);

    /**
     * Handles collisions in child class of Bounds
     * @param b1 First object with Bound Component
     * @param b2 Second object with Bound Component
     * @return true if colliding
     */
    boolean collision(BoundsComponent<?> b1, BoundsComponent<?> b2);

    /**
     * {@link BoundsComponent} method. Handles resolving all collisions of child classes of Bounds
     *
     * @param boundsComponent Bounds of GameObject that is not the Player
     * @param player          Player GameObject
     * @param hitType
     */
    void resolveCollision(BoundsComponent<?> boundsComponent, GameObject player, CollisionManager.HitType hitType);

    /**
     * Handles colliding logic of a child class of {@link BoundsComponent}
     *
     * @param player GameObject
     * @param type
     */
//    void resolveColliding(GameObject player, CollisionManager.HitType type);
}
