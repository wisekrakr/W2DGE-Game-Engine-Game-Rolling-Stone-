package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.graphics.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene implements GameLoopImpl {

    String name;
    Camera camera;
    List<GameObject>gameObjects;
    Renderer renderer;

    public void Scene(String name){
        this.name = name;
        this.camera = new Camera(new Vector2());
        this.gameObjects = new ArrayList<>();
        this.renderer = new Renderer(this.camera);
        init();
    }

}
