package com.wisekrakr.w2dge.game.components;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.InterprocessImpl;
import com.wisekrakr.w2dge.data.Serializable;
import com.wisekrakr.w2dge.game.GameObject;

import java.awt.*;

public abstract class Component<T> extends Serializable implements  GameLoopImpl, InterprocessImpl<Component<T>> {

    public GameObject gameObject;

    public abstract String name();

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
    public void terminate() { }
    @Override
    public Component<T> copy() {
        return null;
    }
    @Override
    public String serialize(int tabSize) {
        return "";
    }


}
