package com.wisekrakr.w2dge.game.components.controls;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class CameraControls extends Component<CameraControls> {

    private Vector2 prevPosition;

    public CameraControls() {
        this.prevPosition = new Vector2(0,0);
    }

    @Override
    public void update(double deltaTime) {
        Screen screen = Screen.getInstance();

        // Shift - Right Button = dragging camera
        if(screen.mouseListener.mousePressed &&
                screen.mouseListener.mouseButton == MouseEvent.BUTTON3 &&
                screen.keyListener.isKeyPressed(KeyEvent.VK_SHIFT)){
            float dx = screen.mouseListener.position.x + screen.mouseListener.dx - prevPosition.x;
            float dy = screen.mouseListener.position.y + screen.mouseListener.dy - prevPosition.y;

            screen.getCurrentScene().camera.position.x -= dx;
            screen.getCurrentScene().camera.position.y -= dy;
        }

        prevPosition.x = screen.mouseListener.position.x + screen.mouseListener.dx;
        prevPosition.y = screen.mouseListener.position.y + screen.mouseListener.dy;
    }

    @Override
    public Component<CameraControls> copy() {
        CameraControls cameraControls = new CameraControls();
        cameraControls.prevPosition = prevPosition.copy();
        return cameraControls;
    }
}
