package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.physics.Bounds;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.graphics.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene implements GameLoopImpl {

    String name;
    public Camera camera;
    List<GameObject> gameObjects;
    Renderer renderer;
    public GameObject player;
    public GameObject ground;
    String toFollow;

    public void createScene(String name) {
        this.name = name;
        this.camera = new Camera(new Vector2());
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer(this.camera);
        this.toFollow = Tags.PLAYER;
    }

    @Override
    public void init() {
        for (GameObject gameObject : gameObjects) {
            gameObject.init();
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(g2d);
        }
    }

    @Override
    public void terminate() {

    }

    @Override
    public void update(double deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);

            // Camera follows value of toFollow
            cameraFollow(gameObject);

            // Collision detection
            Bounds bounds = gameObject.getComponent(Bounds.class);
            if (gameObject != player && bounds != null){
                if (Bounds.collision(player.getComponent(Bounds.class), bounds)){
                    System.out.println("Colliding with: " + gameObject.name);
                }
            }
        }
    }

    private void cameraFollow(GameObject gameObject){
        if (gameObject.name.equalsIgnoreCase(this.toFollow) && !Screen.getInstance().isInEditorPhase) {
            camera.follow(gameObject);
        }
    }

    /**
     * This will show the game object in the scene (on the screen)<br>
     * Add game object to total alive game objects.<br>
     * Add game object to {@link Renderer}.
     *
     * @param gameObject {@link GameObject}
     */
    public void addGameObjectToScene(GameObject gameObject) {
        gameObjects.add(gameObject);
        renderer.add(gameObject);
        for (Component<?> c: gameObject.getAllComponents()){
            c.init();
        }
    }
}
