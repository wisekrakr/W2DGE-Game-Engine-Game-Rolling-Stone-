package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.properties.Actions;
import com.wisekrakr.w2dge.game.components.physics.RigidBody;
import com.wisekrakr.w2dge.game.player.Graphics;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.assets.AssetManager;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.awt.*;

public class Player extends Component<Player> implements Actions {

    private Graphics graphics;
    public boolean grounded = true;

    private Color playerColor;
    private Color bgColor;
    private Color groundColor;

    @Override
    public void init() {
        addGraphics();

        this.bgColor = Colors.randomColor();
        this.groundColor = Colors.randomColor();
    }

    @Override
    public void update(double deltaTime) {
        Scene scene = Screen.getInstance().getCurrentScene();

        // rotate while jumping
        if (!grounded) {
            this.gameObject.transform.rotation += GameConstants.ROTATION_SPEED * deltaTime;

            scene.backgroundColor(this.bgColor, this.groundColor);
        } else {
            // snaps rotation between 0-360 degrees
            this.gameObject.transform.rotation = gameObject.transform.rotation % 360;

            if (gameObject.transform.rotation > 180 && gameObject.transform.rotation < 360) {
                gameObject.transform.rotation = 0;
            } else if (gameObject.transform.rotation > 0 && gameObject.transform.rotation < 180) {
                gameObject.transform.rotation = 0;
            }
            scene.backgroundColor(null,null);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        graphics.render(g2d);
    }

    private void addGraphics() {

        graphics = new Graphics(
                this.gameObject,
                AssetManager.layerOne.sprites.get(0), AssetManager.layerTwo.sprites.get(0), AssetManager.layerThree.sprites.get(0),
                Colors.babyBlue, Colors.iris
        );
    }

    @Override
    public void climb() {

    }

    @Override
    public void jump() {
        if (gameObject.getComponent(RigidBody.class).velocity.y == 0){
            this.gameObject.getComponent(RigidBody.class).velocity.y = GameConstants.JUMP_FORCE;
        }
    }

    @Override
    public void reset() {
        Screen.getInstance().getCurrentScene().resetToStart();
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
