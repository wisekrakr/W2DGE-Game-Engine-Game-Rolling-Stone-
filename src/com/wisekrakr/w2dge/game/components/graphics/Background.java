package com.wisekrakr.w2dge.game.components.graphics;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.Ground;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.assets.AssetManager;

import java.awt.*;

public class Background extends Component<Background> {

    public Dimension dimension;
    public Sprite sprite;
    public GameObject[] backgrounds;
    public int timeStep = 0;
    private float speed = GameConstants.BG_SPEED;

    private final Ground ground;
    private final boolean followGround;

    public Background(String filename, GameObject[] backgrounds, Ground ground, boolean followGround) {
        this.backgrounds = backgrounds;
        this.ground = ground;
        this.followGround = followGround;
        this.sprite = AssetManager.getSprite(filename);
        this.dimension = new Dimension(this.sprite.image.getWidth(), this.sprite.image.getHeight());

        if (followGround) {
            this.speed = GameConstants.PLAYER_SPEED - GameConstants.BG_LESSER_SPEED;
        }
    }

    @Override
    public void update(double deltaTime) {
        this.timeStep++; // keeps a hold on what background it is on

        this.gameObject.transform.position.x -= deltaTime * speed;
        this.gameObject.transform.position.x = (float) Math.floor(this.gameObject.transform.position.x);

        // if the background is no longer visible
        if (this.gameObject.transform.position.x < -dimension.width) {
            // move it back
            float maxX = 0; // maximum x value of the other backgrounds
            int otherTimeStep = 0; // time step the other backgrounds are on

            for (GameObject background : backgrounds) {
                maxX = background.transform.position.x; // set to this background x
                otherTimeStep = background.getComponent(Background.class).timeStep; // set to this background time step
            }

            if (otherTimeStep == this.timeStep) {
                this.gameObject.transform.position.x = maxX + this.dimension.width;
            } else {
                this.gameObject.transform.position.x = (float) Math.floor((maxX + dimension.width) - (deltaTime * speed));
            }
        }

        if (this.followGround) {
            this.gameObject.transform.position.y = ground.gameObject.transform.position.y;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        Camera camera = Screen.getInstance().getCamera();
        if (this.followGround) {
            g2d.drawImage(
                    this.sprite.image,
                    (int) this.gameObject.transform.position.x,
                    (int) (this.gameObject.transform.position.y - camera.position.y),
                    (int) this.gameObject.dimension.width, (int) this.gameObject.dimension.height,
                    null
            );
        } else {
            int y = (int) Math.min(ground.gameObject.transform.position.y - camera.position.y, GameConstants.SCREEN_HEIGHT);

            g2d.drawImage(
                    this.sprite.image,
                    (int) this.gameObject.transform.position.x, (int) this.gameObject.transform.position.y,
                    (int) this.dimension.width, GameConstants.SCREEN_HEIGHT,
                    null
            );
            g2d.setColor(Colors.charcoal);
            g2d.fillRect((int) this.gameObject.transform.position.x, y, (int) this.dimension.width, GameConstants.SCREEN_HEIGHT);
        }
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("Background", tabSize));

        builder.append(beginObjectProperty("Ground", tabSize + 1));
        builder.append(closeObjectProperty(tabSize + 1));
        builder.append(addEnding(true, true));

        builder.append(addBooleanProperty("followGround", followGround, tabSize + 1, true, true));

        builder.append(addStringProperty("filename", sprite.fileName, tabSize + 1, true, true));

//        if (backgrounds.length > 0){
//            builder.append(beginObjectProperty("backgrounds", tabSize + 1));// Components
//        }
//
//        int i = 0;
//        for (GameObject bg : backgrounds) {
//            String str = bg.serialize(tabSize + 2); // get background string
//            if (str.compareTo("") != 0) { // if not empty
//                builder.append(str); // add to StringBuilder
//                if (i != backgrounds.length - 1) {
//                    builder.append(addEnding(true, true)); // if there are more backgrounds - keep it
//                } else {
//                    builder.append(addEnding(true, false));// if there are no more backgrounds - close it
//                }
//            }
//            i++;
//        }
//
//        if (backgrounds.length > 0) {
//            builder.append(closeObjectProperty(tabSize + 1));
//        }

        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static Background deserialize() {
        return null;
    }


    @Override
    public Component<Background> copy() {
        return new Background(sprite.fileName, backgrounds, ground, followGround);
    }

    @Override
    public String name() {
        return null;
    }
}
