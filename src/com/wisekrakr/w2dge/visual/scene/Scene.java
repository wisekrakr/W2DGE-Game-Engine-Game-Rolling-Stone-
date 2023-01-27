package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.main.Game;
import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.GameObjectFactory;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.CollisionManager;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.graphics.DebugRenderer;
import com.wisekrakr.w2dge.visual.graphics.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene implements GameLoopImpl {

    private String name;
    public Game.SceneType type;
    public Camera camera;
    public List<GameObject> gameObjects;
    private CollisionManager collisionManager;
    public Renderer renderer;
    public DebugRenderer debugRenderer;
    public GameObject player;
    public GameObject ground;
    private String toFollow;

    public Color bgColor;
    public Color groundColor;
    private Color originalBgColor;
    private Color originalGroundColor;

    public void createScene(String name) {
        this.name = name;
        this.camera = new Camera(new Vector2());
        this.gameObjects = new ArrayList<>();
        this.debugRenderer = new DebugRenderer();
        this.renderer = new Renderer(this.camera, debugRenderer);
        this.collisionManager = new CollisionManager();
        this.toFollow = Tags.PLAYER;

        backgroundColor(this.originalBgColor = Colors.randomColor(), this.originalGroundColor = Colors.randomColor());
    }

    @Override
    public void init() {
        renderer.isDebugging = false;

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
    public void update(double deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(deltaTime);
            cameraFollow(gameObject);
            collisionManager.update(gameObject);
        }
    }

    public void resetCamera() {
        this.camera.position.x = 0;
    }

    public void backgroundColor(Color bgColor, Color groundColor) {
        if (bgColor != null) {
            this.bgColor = bgColor;
        } else if (groundColor != null) {
            this.groundColor = groundColor;
        } else {
            this.bgColor = originalBgColor;
            this.groundColor = originalGroundColor;
        }
    }

    private void cameraFollow(GameObject gameObject) {
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

        for (Component<?> c : gameObject.getAllComponents()) {
            c.init();
        }
    }

    /**
     * Removes a game object from the game objects list of this scene and {@link Renderer}
     *
     * @param gameObject
     */
    public void removeGameObjectToScene(GameObject gameObject) {
        gameObjects.remove(gameObject);
        renderer.remove(gameObject);
    }

    public void initBackgrounds(int nrOfBackgrounds, String filenameBackground, String filenameGround) {

        GameObject[] backgrounds = new GameObject[nrOfBackgrounds];
        GameObject[] groundBackgrounds = new GameObject[nrOfBackgrounds];

        for (int i = 0; i < nrOfBackgrounds; i++) {
            GameObject background = GameObjectFactory.background(backgrounds, filenameBackground, ground, false, i);
            renderer.partOfUI(background);

            backgrounds[i] = background;

            GameObject groundBg = GameObjectFactory.groundBg(groundBackgrounds, filenameGround, ground, true, i);
            renderer.partOfUI(groundBg);

            groundBackgrounds[i] = groundBg;

            addGameObjectToScene(background);
            addGameObjectToScene(groundBg);
        }
    }

    @Override
    public void terminate() {

    }

}
