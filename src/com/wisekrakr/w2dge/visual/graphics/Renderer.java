package com.wisekrakr.w2dge.visual.graphics;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renderer implements GameLoopImpl {

    private final Map<Integer, List<GameObject>> gameObjects;
    private final Camera camera;
    public DebugRenderer debugRenderer;
    private final List<GameObject> uiGameObjects;

    public boolean isDebugging = false;

    public Renderer(Camera camera, DebugRenderer debugRenderer) {
        this.camera = camera;
        this.debugRenderer = debugRenderer;
        this.gameObjects = new HashMap<>();
        this.uiGameObjects = new ArrayList<>();
    }

    @Override
    public void init() {

    }

    public void initDebugging(){
        isDebugging = true;
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render(Graphics2D g2d) {
        int lowestZIndex = Integer.MAX_VALUE;
        int highestZIndex = Integer.MIN_VALUE;

        // making sure everything is in the proper order
        // looping through lowest and highest zIndexes
        for (Integer i : gameObjects.keySet()) {
            if (i < lowestZIndex) {
                lowestZIndex = i;
            }
            if (i > highestZIndex) {
                highestZIndex = i;
            }
        }

        int currentZIndex = lowestZIndex;

        while (currentZIndex <= highestZIndex) {
            // if there is no container yet
            if (gameObjects.get(currentZIndex) == null) {
                // increment and keep searching
                currentZIndex++;
                continue;
            }

            for (GameObject gameObject : gameObjects.get(currentZIndex)) {
                if (uiGameObjects.contains(gameObject)) {
                    gameObject.render(g2d);
                } else {
                    Transform oldTransform = new Transform(gameObject.transform.position);
                    oldTransform.rotation = gameObject.transform.rotation;
                    oldTransform.scale = new Vector2(gameObject.transform.scale.x, gameObject.transform.scale.y);

                    gameObject.transform.position = new Vector2(
                            gameObject.transform.position.x - camera.position.x,
                            gameObject.transform.position.y - camera.position.y
                    );

                    gameObject.render(g2d);
                    gameObject.transform = oldTransform;
                }
            }
            currentZIndex++;
        }
    }

    public void drawImage(Graphics2D g2d, BufferedImage image, GameObject gameObject, int bufferX, int bufferY) {
        g2d.drawImage(image, gameObject.transform(bufferX, bufferY, true), null);
    }

    public void add(GameObject gameObject) {
        this.gameObjects.computeIfAbsent(gameObject.zIndex, index -> new ArrayList<>());
        this.gameObjects.get(gameObject.zIndex).add(gameObject);
    }

    public void remove(GameObject gameObject) {
        this.gameObjects.get(gameObject.zIndex).remove(gameObject);
    }

    /**
     * Add to a List of ui game objects that will be rendered differently in the {@link Renderer#render(Graphics2D)} method
     *
     * @param uiObject {@link GameObject}
     */
    public void partOfUI(GameObject uiObject) {
        this.uiGameObjects.add(uiObject);
    }

    @Override
    public void terminate() {
        this.gameObjects.clear();
        this.uiGameObjects.clear();
    }

}
