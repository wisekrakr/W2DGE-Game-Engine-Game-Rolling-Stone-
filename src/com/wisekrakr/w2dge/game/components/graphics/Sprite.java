package com.wisekrakr.w2dge.game.components.graphics;

import com.wisekrakr.w2dge.assets.AssetManager;
import com.wisekrakr.w2dge.game.components.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite extends Component<Sprite> {

    public BufferedImage image;
    public String fileName;

    public Sprite(String fileName) {
        this.fileName = fileName;

        try {
            if (AssetManager.hasSprite(fileName)){
                throw new Exception("Asset already exists: " + fileName);
            }
            this.image = ImageIO.read(new File(fileName));

        }catch (Exception e){
            e.printStackTrace();
            System.err.println("Could not create Sprite: " + fileName);
        }
    }

    public Sprite(BufferedImage image){
        this.image = image;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(this.image,
                (int)gameObject.transform.position.x, (int)gameObject.transform.position.y,
                gameObject.dimension.width, gameObject.dimension.height,
                null
        );
    }
}
