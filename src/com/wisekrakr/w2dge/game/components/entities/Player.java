package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.properties.Actions;
import com.wisekrakr.w2dge.game.components.physics.RigidBody;
import com.wisekrakr.w2dge.game.player.Graphics;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

import java.awt.*;

public class Player extends Component<Player> implements Actions {

    private Graphics graphics;
    public boolean grounded = true;


    @Override
    public void init() {
        addGraphics();
    }

    @Override
    public void update(double deltaTime) {

        // rotate while jumping
        if (!grounded) {
            this.gameObject.transform.rotation += GameConstants.ROTATION_SPEED * deltaTime;
        } else {
            // snaps rotation between 0-360 degrees
            this.gameObject.transform.rotation = gameObject.transform.rotation % 360;

            if (gameObject.transform.rotation > 180 && gameObject.transform.rotation < 360) {
                gameObject.transform.rotation = 0;
            } else if (gameObject.transform.rotation > 0 && gameObject.transform.rotation < 180) {
                gameObject.transform.rotation = 0;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        graphics.render(g2d);
    }

    private void addGraphics() {
        SpriteSheet layerOne = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerOne.png",
                new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT), 13, 13 * 5);
        SpriteSheet layerTwo = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerTwo.png",
                new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT), 13, 13 * 5);
        SpriteSheet layerThree = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerThree.png",
                new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT), 13, 13 * 5);

        graphics = new Graphics(
                this.gameObject,
                layerOne.sprites.get(0), layerTwo.sprites.get(0), layerThree.sprites.get(0),
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
    public void die() {
        this.gameObject.transform.position.x = 0;
        this.gameObject.transform.position.y = 30;
        Screen.getInstance().getCurrentScene().camera.position.x = 0;
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
