package com.wisekrakr.w2dge.visual.graphics;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {

    List<GameObject>gameObjects;
    Camera camera;
    private List<GameObject> uiGameObjects;

    public Renderer(Camera camera) {
        this.camera = camera;
        this.gameObjects = new ArrayList<>();
        this.uiGameObjects = new ArrayList<>();
    }

    public void add(GameObject gameObject){
        this.gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject){
        this.gameObjects.remove(gameObject);
    }

    public void render(Graphics2D g2d){

        for (GameObject gameObject: gameObjects){

            if (uiGameObjects.contains(gameObject)){
                gameObject.render(g2d);
            }else {
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
    }

    /**
     * Add to a List of ui game objects that will be rendered differently in the {@link Renderer#render(Graphics2D)} method
     * @param uiObject {@link GameObject}
     */
    public void partOfUI(GameObject uiObject) {
        uiGameObjects.add(uiObject);
    }
}
