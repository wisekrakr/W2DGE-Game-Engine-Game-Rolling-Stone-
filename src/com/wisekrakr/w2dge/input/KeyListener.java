package com.wisekrakr.w2dge.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.TreeSet;

public class KeyListener extends KeyAdapter implements java.awt.event.KeyListener {

    private final boolean[] keyPressed = new boolean[128];
    private final Set<Integer>pressedKeys = new TreeSet<>();
    public int keyCode = 0;

    @Override
    public void keyPressed(KeyEvent e) {
        keyCode = e.getKeyCode();

        if (!pressedKeys.contains(keyCode)){
            pressedKeys.add(keyCode);

            keyPressed[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
        keyCode = 0;

        pressedKeys.remove(e.getKeyCode());
    }

    public boolean isKeyPressed(int keyCode) {
        return keyPressed[keyCode];
    }

}
