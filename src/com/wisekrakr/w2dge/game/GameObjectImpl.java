package com.wisekrakr.w2dge.game;

public interface GameObjectImpl {

    /**
     * Centers the Game Object, so all physics will go through the middle of this Game Object (x-0 and y-0 are in the center)
     */
    void centering();

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
