package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.properties.Actions;
import com.wisekrakr.w2dge.game.components.physics.RigidBodyComponent;
import com.wisekrakr.w2dge.game.loops.Graphics;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.assets.AssetManager;

import java.awt.*;

public class PlayerComponent extends Component<PlayerComponent> implements Actions {

    private Graphics graphics;
    public boolean grounded = true;
    public boolean elevate = false;

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

        // rotate while jumping
        if (!grounded) {
            this.gameObject.transform.rotation += GameConstants.ROTATION_SPEED * deltaTime;
            Screen.getScene().backgroundColor(this.bgColor, this.groundColor);
            Screen.getScene().gameItemColor(true);
        } else {
            // snaps rotation between 0-360 degrees
            this.gameObject.transform.rotation = gameObject.transform.rotation % 360;

            if (gameObject.transform.rotation > 180 && gameObject.transform.rotation < 360) {
                gameObject.transform.rotation = 0;
            } else if (gameObject.transform.rotation > 0 && gameObject.transform.rotation < 180) {
                gameObject.transform.rotation = 0;
            }
            Screen.getScene().backgroundColor(null,null);
            Screen.getScene().gameItemColor(false);
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
        this.gameObject.getComponent(RigidBodyComponent.class).velocity.x = 0;
        this.gameObject.getComponent(RigidBodyComponent.class).velocity.y = GameConstants.JUMP_FORCE;
    }

    @Override
    public void jump() {
        if (gameObject.getComponent(RigidBodyComponent.class).velocity.y == 0){
            this.gameObject.getComponent(RigidBodyComponent.class).velocity.y = GameConstants.JUMP_FORCE;
        }
    }

    @Override
    public void reset() {
        this.gameObject.transform.position.x = GameConstants.PLAYER_START_X;
        this.gameObject.transform.position.y = GameConstants.PLAYER_START_Y;
        this.gameObject.getComponent(RigidBodyComponent.class).velocity.y = 0;
        this.gameObject.transform.rotation = 0;

        Screen.getScene().resetCamera();
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
