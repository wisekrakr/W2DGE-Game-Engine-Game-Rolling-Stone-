package com.wisekrakr.w2dge.game.components.controls;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.input.GameInputListener;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

public class CameraControlsComponent extends Component<CameraControlsComponent> {

    private Vector2 prevPosition;

    public CameraControlsComponent() {
        this.prevPosition = new Vector2(0, 0);
    }

    @Override
    public void update(double deltaTime) {
        GameInputListener inputListener = Screen.getInputListener();

        // Right Button = dragging camera
        if (inputListener.rightMousePressed()) {
            float dx = inputListener.mouseListener.position.x + inputListener.mouseListener.dx - prevPosition.x;
            float dy = inputListener.mouseListener.position.y + inputListener.mouseListener.dy - prevPosition.y;

            Screen.getScene().camera.position.x -= dx;
            Screen.getScene().camera.position.y -= dy;
        }

        prevPosition.x = inputListener.mouseListener.position.x + inputListener.mouseListener.dx;
        prevPosition.y = inputListener.mouseListener.position.y + inputListener.mouseListener.dy;
    }

    @Override
    public Component<CameraControlsComponent> copy() {
        CameraControlsComponent cameraControls = new CameraControlsComponent();
        cameraControls.prevPosition = prevPosition.copy();
        return cameraControls;
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
