package com.wisekrakr.w2dge.game.components.graphics;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.GroundComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.Camera;
import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.assets.AssetManager;

import java.awt.*;

public class BackgroundComponent extends Component<BackgroundComponent> {

    public Dimension dimension;
    public SpriteComponent spriteComponent;
    public GameObject[] backgrounds;
    public int timeStep = 0;
    private float speed = GameConstants.BG_SPEED;

    private final GroundComponent groundComponent;
    private final boolean followGround;

    public BackgroundComponent(String filename, GameObject[] backgrounds, GroundComponent groundComponent, boolean followGround) {
        this.backgrounds = backgrounds;
        this.groundComponent = groundComponent;
        this.followGround = followGround;
        this.spriteComponent = AssetManager.getSprite(filename);
        this.dimension = new Dimension(this.spriteComponent.image.getWidth(), this.spriteComponent.image.getHeight());

        if (followGround) {
            this.speed = GameConstants.PLAYER_SPEED - GameConstants.BG_LESSER_SPEED;
        }
    }

    @Override
    public void update(double deltaTime) {
        this.timeStep++; // keeps a hold on what background it is on

        this.gameObject.transform.position.x -= deltaTime * speed;
        this.gameObject.transform.position.x = (float) Math.floor(this.gameObject.transform.position.x); // make an int

        // if the background is no longer visible
        if (this.gameObject.transform.position.x < -dimension.width) {
            // move it back
            float maxX = 0; // maximum x value of the other backgrounds
            int otherTimeStep = 0; // time step the other backgrounds are on

            for (GameObject background : backgrounds) {
                if (background.transform.position.x > maxX){
                    maxX = background.transform.position.x; // set to this background x
                    otherTimeStep = background.getComponent(BackgroundComponent.class).timeStep; // set to this background time step
                }
            }

            if (otherTimeStep == this.timeStep) {
                this.gameObject.transform.position.x = maxX + this.dimension.width;
            } else {
                this.gameObject.transform.position.x = (float) Math.floor((maxX + dimension.width) - (deltaTime * speed));
            }
        }

        if (this.followGround) {
            this.gameObject.transform.position.y = groundComponent.gameObject.transform.position.y;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        Camera camera = Screen.getInstance().getCamera();
        if (this.followGround) {
            g2d.drawImage(
                    this.spriteComponent.image,
                    (int) this.gameObject.transform.position.x,
                    (int) (this.gameObject.transform.position.y - camera.position.y),
                    (int) this.gameObject.dimension.width, (int) this.gameObject.dimension.height,
                    null
            );
        } else {
            int y = (int) Math.min(groundComponent.gameObject.transform.position.y - camera.position.y, GameConstants.SCREEN_HEIGHT);

            g2d.drawImage(
                    this.spriteComponent.image,
                    (int) this.gameObject.transform.position.x, (int) this.gameObject.transform.position.y,
                    (int) this.dimension.width, GameConstants.SCREEN_HEIGHT,
                    null
            );
            g2d.setColor(Screen.getScene().groundColor);
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

        builder.append(addStringProperty("filename", spriteComponent.fileName, tabSize + 1, true, true));

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



    @Override
    public Component<BackgroundComponent> copy() {
        return new BackgroundComponent(spriteComponent.fileName, backgrounds, groundComponent, followGround);
    }

    @Override
    public String name() {
        return null;
    }
}
