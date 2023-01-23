package com.wisekrakr.w2dge.game;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.constants.Tags;
import com.wisekrakr.w2dge.game.components.entities.Block;
import com.wisekrakr.w2dge.game.components.entities.Ground;
import com.wisekrakr.w2dge.game.components.entities.Player;
import com.wisekrakr.w2dge.game.components.graphics.Background;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.game.components.physics.BoxBounds;
import com.wisekrakr.w2dge.game.components.physics.RigidBody;
import com.wisekrakr.w2dge.game.components.regions.SnapToGrid;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;

public class GameObjectFactory {

    /**
     * Creates a new GameObject - PLAYER <br>
     * Consists of {@link Player},{@link RigidBody} components.
     *
     * @param position         {@link Vector2} position of the game object
     * @param isInEditingPhase if false, this Game Object will have full motion. If false, only visual.
     * @return new player Game object
     */
    public static GameObject player(Vector2 position, boolean isInEditingPhase) {
        Dimension dimension = new Dimension(GameConstants.PLAYER_WIDTH, GameConstants.PLAYER_HEIGHT);

        GameObject player = new GameObject(
                Tags.PLAYER,
                new Transform(new Vector2(position.x, position.y)),
                dimension,
                new BoxBounds(dimension)
        );

        player.addComponent(new Player());

        if (!isInEditingPhase) {
            player.addComponent(new RigidBody(new Vector2(GameConstants.PLAYER_SPEED, 0f)));
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
                new Ground(),
                new BoxBounds(new Dimension(GameConstants.SCREEN_WIDTH, GameConstants.GROUND_Y))
        );

        ground.setNonSerializable();

        return ground;

    }

    public static GameObject background(GameObject[] backgrounds, String filename, GameObject ground, boolean follow, int index) {
        Background bg = new Background(
                AssetFinder.image(AssetFinder.ImageType.BACKGROUND, filename),
                backgrounds,
                ground.getComponent(Ground.class),
                follow
        );
        int x = (int) (index * bg.sprite.dimension.width);
        int y = 0;

        return new GameObject(Tags.BACKGROUND, new Transform(new Vector2(x, y)), bg.dimension, bg);

    }

    public static GameObject groundBg(GameObject[] backgrounds, GameObject ground, boolean follow, int index) {
        Background bg = new Background(
                AssetFinder.image(AssetFinder.ImageType.GROUNDS, "ground01.png"),
                backgrounds,
                ground.getComponent(Ground.class),
                follow
        );
        int x = (int) (index * bg.sprite.dimension.width);
        int y = (int) bg.sprite.dimension.height;

        return new GameObject(Tags.GROUND_BACKGROUND, new Transform(new Vector2(x, y)), bg.dimension, bg);
    }

    public static GameObject block(Sprite currentSprite, int x, int y, SpriteSheet spriteSheet) {
        return new GameObject(
                Tags.BLOCK,
                new Transform(new Vector2(x, y)),
                new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT),
                // Components
                currentSprite.copy(),
                new Block(
                        new Transform(new Vector2(x, y)),
                        new Dimension(GameConstants.BUTTON_WIDTH, GameConstants.BUTTON_HEIGHT),
                        spriteSheet.sprites.get(0), spriteSheet.sprites.get(1)
                ),
                new BoxBounds(new Dimension(GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT))
        );
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
