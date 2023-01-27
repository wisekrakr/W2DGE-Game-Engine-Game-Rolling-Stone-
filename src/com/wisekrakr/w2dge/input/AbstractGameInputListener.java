package com.wisekrakr.w2dge.input;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.event.MouseEvent;

public abstract class AbstractGameInputListener {

    public MouseListener mouseListener;
    public KeyListener keyListener;
    public GameObject player;

    public AbstractGameInputListener() {
        this.mouseListener = new MouseListener();
        this.keyListener = new KeyListener();
    }

    public void setController(GameObject gameObject) {
        this.player = gameObject;
    }


    public boolean leftMousePressed(){
        return Screen.getInputListener().mouseListener.mousePressed &&
                Screen.getInputListener().mouseListener.mouseButton == MouseEvent.BUTTON1;
    }

    public boolean middleMousePressed(){
        return Screen.getInputListener().mouseListener.mousePressed &&
                Screen.getInputListener().mouseListener.mouseButton == MouseEvent.BUTTON2;
    }

    public boolean rightMousePressed(){
        return Screen.getInputListener().mouseListener.mousePressed &&
                Screen.getInputListener().mouseListener.mouseButton == MouseEvent.BUTTON3;
    }
}
