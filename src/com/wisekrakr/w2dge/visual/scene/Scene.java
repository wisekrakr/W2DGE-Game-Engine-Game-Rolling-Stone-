package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
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
    GameObjectFactory factory;
    public GameObject player;
    public GameObject ground;
    String toFollow;

    public void Scene(String name) {
        this.name = name;
        this.camera = new Camera(new Vector2());
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer(this.camera);
        this.factory = new GameObjectFactory(this.gameObjects, this.renderer);
        this.toFollow = Tags.PLAYER;
    }


    @Override
    public void init() {

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

            if (gameObject.name.equalsIgnoreCase(this.toFollow) && !Screen.getInstance().isInEditorPhase) {
                camera.follow(gameObject);
            }
        }
    }

    /**
     * This will show the game object in the scene (on the screen)<br>
     * Add game object to total alive game objects.<br>
     * Add game object to {@link Renderer}.
     *
     * @param gameObject {@link GameObject}
     */
    protected void addGameObjectToScene(GameObject gameObject) {
        gameObjects.add(gameObject);
        renderer.add(gameObject);
    }
}
