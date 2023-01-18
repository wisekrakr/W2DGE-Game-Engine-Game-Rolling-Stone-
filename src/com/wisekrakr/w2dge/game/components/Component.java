package com.wisekrakr.w2dge.game.components;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.game.GameObject;

import java.awt.*;

public abstract class Component<T> implements GameLoopImpl {

    public GameObject gameObject;

    @Override
    public void init() {

    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render(Graphics2D g2d) {

    }

    @Override
    public void terminate() {

    }
}
