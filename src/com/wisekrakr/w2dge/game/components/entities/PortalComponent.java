package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.util.MathUtils;
import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.PlayerState;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.physics.BoundsComponent;
import com.wisekrakr.w2dge.visual.Screen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PortalComponent extends Component<PortalComponent> {

    private static final int FRAME_WIDTH = 100;
    private static final int FRAME_HEIGHT = 100;
    private static final int NUM_FRAMES = 40;

    private final PlayerState state;
    private GameObject player;
    private BoundsComponent<?> bounds;

    private BufferedImage spriteSheet;
    private BufferedImage[] sprites;
    private int currentFrame;

    public PortalComponent(PlayerState state) {
        this.state = state;
    }

    @Override
    public void init() {
        this.player = Screen.getScene().player;
        this.bounds = this.gameObject.getComponent(BoundsComponent.class);

        try {
            spriteSheet = ImageIO.read(new File("assets/spritesheets/others/explosion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        sprites = new BufferedImage[NUM_FRAMES];
        for (int i = 0; i < NUM_FRAMES; i++) {
            sprites[i] = spriteSheet.getSubimage(
                    FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }
    }

    @Override
    public void update(double deltaTime) {
        currentFrame = (currentFrame + 1) % NUM_FRAMES;

        if (this.player != null) {
            if (!this.player.getComponent(PlayerComponent.class).state.equals(this.state) &&
                    this.bounds.checkCollision(this.player.getComponent(BoundsComponent.class), bounds)) {

                this.player.getComponent(PlayerComponent.class).state = this.state;

                if (this.state == PlayerState.TELEPORTING) {
                    Map<Float, GameObject> distances = calculateDistances();
                    Float minDistance = Collections.min(distances.keySet()); // find the lowest float distance of a portal game object
                    this.player.getComponent(PlayerComponent.class).teleport(distances.get(minDistance)); // teleport to that game object
                }else if (this.state == PlayerState.FLYING){
//                    this.player.getComponent(PlayerComponent.class).fly(); // player fly
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (player != null && player.getComponent(PlayerComponent.class).state == PlayerState.TELEPORTING && state == PlayerState.NORMAL){
            g2d.drawImage(
                        sprites[currentFrame],
                        (int) gameObject.transform.position.x, (int) gameObject.transform.position.y,
                        (int) gameObject.dimension.width, (int) gameObject.dimension.height, null);
        }
    }

    /**
     * Loops through all game objects. Searches for those with {@link PortalComponent}<br>
     * Calculates the distance between player and Portal game object <br>
     * If the distance is more than 10, put both distance and Portal game object in Hashmap, so we can pick out the closest
     * Game object and teleport towards it.
     *
     * @return
     */
    private Map<Float, GameObject> calculateDistances(){
        Map<Float, GameObject>distances = new HashMap<>();
        for (GameObject g : Screen.getScene().getGameObjects()) {
            if (g.getComponent(PortalComponent.class) != null) { // multiple PortalComponents
                float distance = MathUtils.distance(this.gameObject, g); // distance between Portal object and player

                if (distance > 10) { // find the closest
                    distances.put(distance, g); // put in distance and game object in hashmap
                }
            }
        }

        return distances;
    }

    @Override
    public Component<PortalComponent> copy() {
        return new PortalComponent(this.state);
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();
        builder.append(beginObjectProperty(Names.PORTAL, tabSize));
        builder.append(addIntProperty("PlayerState", state.ordinal(), tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static PortalComponent deserialize() {
        int stateInt = Parser.consumeIntProperty("PlayerState");
        PlayerState savedState = null;

        for (int i = 0; i < PlayerState.values().length; i++) {
            if (i == stateInt) {
                savedState = PlayerState.values()[i];
            }
        }

        Parser.consumeEndObjectProperty();
        return new PortalComponent(savedState);
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
