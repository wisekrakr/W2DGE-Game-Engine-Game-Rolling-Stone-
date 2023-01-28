package com.wisekrakr.w2dge.input;

import com.wisekrakr.w2dge.game.GameObject;

import java.awt.event.KeyEvent;
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

    //*****************************//
    //          Mouse inputs
    //*****************************//
    public boolean leftMousePressed(){
        return mouseListener.mousePressed && mouseListener.mouseButton == MouseEvent.BUTTON1;
    }

    public boolean middleMousePressed(){
        return mouseListener.mousePressed &&  mouseListener.mouseButton == MouseEvent.BUTTON2;
    }

    public boolean rightMousePressed(){
        return mouseListener.mousePressed && mouseListener.mouseButton == MouseEvent.BUTTON3;
    }

    //*****************************//
    //          Keyboard inputs
    //*****************************//

    public boolean escapePressed(){
        return keyListener.isKeyPressed(KeyEvent.VK_ESCAPE);
    }
}
