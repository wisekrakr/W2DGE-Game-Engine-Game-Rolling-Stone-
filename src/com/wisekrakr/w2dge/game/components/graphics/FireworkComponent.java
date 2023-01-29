package com.wisekrakr.w2dge.game.components.graphics;

import com.wisekrakr.w2dge.game.components.Component;

import java.awt.*;
import java.util.List;

@Deprecated
public class FireworkComponent extends Component<FireworkComponent> {

    private final List<SpriteComponent>fireworkSheet;

    public FireworkComponent(List<SpriteComponent> fireworkSheet) {
        this.fireworkSheet = fireworkSheet;
    }

    @Override
    public void render(Graphics2D g2d) {
        for (SpriteComponent currentSpriteComponent : fireworkSheet) {

            g2d.drawImage(
                    currentSpriteComponent.image,
                    (int) (this.gameObject.transform.position.x),
                    (int) (this.gameObject.transform.position.y),
                    currentSpriteComponent.image.getWidth(), currentSpriteComponent.image.getHeight(),
                    null
            );
        }
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
