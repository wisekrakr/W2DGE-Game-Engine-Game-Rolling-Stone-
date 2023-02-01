package com.wisekrakr.w2dge.game;

import java.awt.geom.AffineTransform;

public interface GameObjectImpl {

    /**
     * Is the mouse hovering over this particular Game Object
     * @return true if the mouse is hovering
     */
    boolean inMouseBounds();

    /**
     * Copies certain aspects of the Game Object
     * @return this game object's copy
     */
    GameObject copy();

    /**
     * A method that helps with drawing a {@link com.wisekrakr.w2dge.game.GameObject} rotation. <br>
     * We also set the rotation of a GameObjects with this method.
     *
     * @return {@link AffineTransform}
     */
    AffineTransform transform(int bufferX, int bufferY, boolean toRadians);
}
