package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.util.AssetFinder;
import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.Dimensions;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.visual.graphics.SpriteSheet;
import com.wisekrakr.w2dge.game.components.entities.Player;
import com.wisekrakr.w2dge.game.components.graphics.Graphics;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;

import java.awt.*;

public class LevelEditorScene extends Scene {

    private GameObject player;
    private GameObject ground;

    public LevelEditorScene(String name) {
        super.Scene(name);
    }

    @Override
    public void init() {
        player = new GameObject(
                "Player",
                new Transform(new Vector2(100, 300)),
                new Dimension(Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT)
        );

        player.addComponent(new Player());

        SpriteSheet layerOne = AssetFinder.spriteSheet("layerOne.png", Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT);
        SpriteSheet layerTwo = AssetFinder.spriteSheet("layerTwo.png", Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT);
        SpriteSheet layerThree = AssetFinder.spriteSheet("layerThree.png", Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT);

        player.addComponent(
                new Graphics(
                        layerOne.sprites.get(0),
                        layerTwo.sprites.get(0),
                        layerThree.sprites.get(0),
                        Colors.babyBlue,
                        Colors.iris
                )
        );

        renderer.add(player);

//        ground = new GameObject(
//                "Ground",
//                new Transform(new Vector2(100, 300)),
//                new Dimension(Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT)
//        );
    }

    @Override
    public void update(double deltaTime) {
        player.update(deltaTime);

        player.transform.rotation += 3f * deltaTime;

    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Colors.synthWaveBlue);
        g2d.fillRect(0, 0, Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);

        this.renderer.render(g2d);

    }

    @Override
    public void terminate() {

    }
}
