package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.awt.*;


public class Ground extends Component<Ground> {

    @Override
    public void update(double deltaTime) {
        if (!Screen.getInstance().isInEditorPhase) {
            Scene scene = Screen.getInstance().getCurrentScene();
            GameObject player = scene.player;

            // collision detection for the player with the ground
            if (player.transform.position.y + player.dimension.height > gameObject.transform.position.y) {
                player.transform.position.y = gameObject.transform.position.y - player.dimension.height;
            }

            gameObject.transform.position.x = scene.camera.position.x - GameConstants.CAMERA_OFFSET_GROUND_X;
        } else {
            gameObject.transform.position.x = Screen.getInstance().getCurrentScene().camera.position.x
                    - GameConstants.CAMERA_OFFSET_GROUND_X;
        }

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
    public Component<Ground> copy() {
        return new Ground();
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
