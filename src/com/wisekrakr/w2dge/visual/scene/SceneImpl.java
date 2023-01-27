package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.visual.graphics.Renderer;

import java.awt.*;

public interface SceneImpl {

    /**
     * Resets camera position x to 0
     */
    void resetCamera();

    /**
     * Replaces background and ground color of this {@link Scene}
     * @param bgColor color of the background of the scene
     * @param groundColor color of the ground of the scene
     */
    void backgroundColor(Color bgColor, Color groundColor);

    /**
     * Initializes backgrounds for the current scene
     * @param nrOfBackgrounds Number of paralax background scrolling
     * @param filenameBackground file name for the background image (not the path)
     * @param filenameGround file name for the ground image (not the path)
     */
    void initBackgrounds(int nrOfBackgrounds, String filenameBackground, String filenameGround);


    /**
     * Adds a game object to the scene. Adds to a List of {@link GameObject} that will initialize, render and update
     * in the scene <br>
     * Add game object to {@link Renderer}.
     * @param gameObject
     */
    void addGameObjectToScene(GameObject gameObject);

    /**
     * Removes a game object from the game objects list of this scene and {@link Renderer}
     *
     * @param gameObject
     */
    void removeGameObjectToScene(GameObject gameObject);
}
