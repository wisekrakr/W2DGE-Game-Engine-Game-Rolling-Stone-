package com.wisekrakr.w2dge.game;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.game.components.entities.Ground;
import com.wisekrakr.w2dge.game.components.entities.Player;
import com.wisekrakr.w2dge.game.components.graphics.Graphics;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.game.components.physics.RigidBody;
import com.wisekrakr.w2dge.game.components.regions.SnapToGrid;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

public class GameObjectFactory {

      /**
     * Creates a new GameObject - PLAYER <br>
     * Consists of {@link Player},{@link RigidBody},{@link Graphics} components.
     *
     * @param position         {@link Vector2} position of the game object
     * @param isInEditingPhase if false, this Game Object will have full motion. If false, only visual.
     * @return new player Game object
     */
    public static GameObject player(Vector2 position, boolean isInEditingPhase) {

        GameObject player = new GameObject(
                Tags.PLAYER, new Transform(new Vector2(position.x, position.y)),
                new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT)
        );

        player.addComponent(new Player());

        SpriteSheet layerOne = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerOne.png",
                GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT, 13, 13 * 5);
        SpriteSheet layerTwo = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerTwo.png",
                GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT, 13, 13 * 5);
        SpriteSheet layerThree = AssetFinder.spriteSheet(AssetFinder.ImageType.PLAYER, "layerThree.png",
                GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT, 13, 13 * 5);

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

        player.setNonSerializable();

        return player;
    }

    /**
     * Creates a new GameObject - GROUND <br>
     * Consists of the {@link Ground} component. Adds it to the engine to act and draw.
     *
     * @return new ground Game object
     */
    public static GameObject ground() {
        GameObject ground = new GameObject(
                Tags.GROUND, new Transform(new Vector2(0, GameConstants.GROUND_Y)),
                new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT),
                new Ground());

        ground.setNonSerializable();

        return ground;

    }

    /**
     * Creates a new GameObject - CURSOR<br>
     * Consists of the following components: {@link SnapToGrid}, {@link Sprite}
     *
     * @return new cursor GameObject
     */
    public static GameObject mouserCursor() {
        return new GameObject(Tags.CURSOR, new Transform(new Vector2()),
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                new SnapToGrid()
        );
    }
}
