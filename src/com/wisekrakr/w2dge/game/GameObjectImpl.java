package com.wisekrakr.w2dge.game;

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
}
