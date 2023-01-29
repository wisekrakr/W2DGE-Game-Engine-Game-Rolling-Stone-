package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;


public class GroundComponent extends Component<GroundComponent> {

    @Override
    public void update(double deltaTime) {
        gameObject.transform.position.x = Screen.getScene().camera.position.x - GameConstants.CAMERA_OFFSET_GROUND_X;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Colors.synthWaveCyan);
        g2d.drawRect(
                (int) gameObject.transform.position.x - 10, (int) gameObject.transform.position.y,
                (int) (gameObject.dimension.width + 20), (int) (gameObject.dimension.height - 1)
        );
    }

    @Override
    public Component<GroundComponent> copy() {
        return new GroundComponent();
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
