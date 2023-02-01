package com.wisekrakr.w2dge.game.components.entities.properties;

import com.wisekrakr.w2dge.game.GameObject;

public interface Actions {
    void jump();
    void teleport(GameObject gameObject);
    void fly();
    void climb();
    void reset();
}
