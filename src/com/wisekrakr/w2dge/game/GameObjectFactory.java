package com.wisekrakr.w2dge.game;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.game.components.entities.Ground;
import com.wisekrakr.w2dge.game.components.entities.Player;
import com.wisekrakr.w2dge.game.components.graphics.Graphics;
import com.wisekrakr.w2dge.game.components.physics.RigidBody;
import com.wisekrakr.w2dge.game.components.regions.Grid;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.graphics.Renderer;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

import java.util.List;

public class GameObjectFactory {

    private final List<GameObject> gameObjects;
    private final Renderer renderer;

    public GameObjectFactory(List<GameObject> gameObjects, Renderer renderer) {
        this.gameObjects = gameObjects;
        this.renderer = renderer;
    }

    /**
     * Creates a new GameObject - PLAYER <br>
     * Consists of {@link Player},{@link RigidBody},{@link Graphics} components.
     * Adds it to the engine to act and draw.
     *
     * @param position         {@link Vector2} position of the game object
     * @param isInEditingPhase if false, this Game Object will have full motion. If false, only visual.
     * @return new player Game object
     */
    public GameObject player(Vector2 position, boolean isInEditingPhase) {

        GameObject player = new GameObject(
                Tags.PLAYER,
                new Transform(new Vector2(position.x, position.y)),
                new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT)
        );

        player.addComponent(new Player());

        SpriteSheet layerOne = AssetFinder.spriteSheet("layerOne.png", GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);
        SpriteSheet layerTwo = AssetFinder.spriteSheet("layerTwo.png", GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);
        SpriteSheet layerThree = AssetFinder.spriteSheet("layerThree.png", GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        player.addComponent(
                new Graphics(
                        layerOne.sprites.get(0),
                        layerTwo.sprites.get(0),
                        layerThree.sprites.get(0),
                        Colors.babyBlue,
                        Colors.iris
                )
        );

        if (!isInEditingPhase) {
            player.addComponent(new RigidBody(new Vector2(GameConstants.SPEED, 0f)));
        }

        return player;
    }

    /**
     * Creates a new GameObject - GROUND <br>
     * Consists of the {@link Ground} component. Adds it to the engine to act and draw.
     *
     * @return new ground Game object
     */
    public GameObject ground(int groundY) {
        GameObject ground = new GameObject(
                Tags.GROUND,
                new Transform(new Vector2(0, groundY)),
                new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT)
        );

        ground.addComponent(new Ground());

        return ground;
    }

    public Grid grid(){
        return new Grid();
    }
}
