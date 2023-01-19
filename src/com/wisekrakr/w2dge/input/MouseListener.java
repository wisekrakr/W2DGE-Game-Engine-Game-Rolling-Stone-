package com.wisekrakr.w2dge.input;

import com.wisekrakr.w2dge.math.Vector2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {

    public boolean mousePressed = false;
    public boolean mouseDragged = false;
    public int mouseButton = -1;
    public Vector2 position = new Vector2(-1.0f, -1.0f); // mouse position
    /**
     * drag distance
     */
    public float dx = -1.0f, dy = -1.0f;

    @Override
    public void mousePressed(MouseEvent e) {
        this.mouseButton = e.getButton();
        this.mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mousePressed = false;
        this.mouseDragged = false;
        this.dx = 0;
        this.dy = 0;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.position.x = e.getX();
        this.position.y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mouseDragged = true;
        this.dx = e.getX() - this.position.x;
        this.dy = e.getY() - this.position.y;
    }
}
