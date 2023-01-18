package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.Dimensions;
import com.wisekrakr.w2dge.game.components.Component;

import java.awt.*;

public class Ground extends Component<Ground> {


    public Ground() {

    }

    @Override
    public void update(double deltaTime) {
        

    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.translate((int)gameObject.transform.position.x, (int)gameObject.transform.position.y);
        g2d.setColor(Colors.synthWaveCyan);
        g2d.drawLine(0, 0, gameObject.dimension.width - 2, 0);
        g2d.drawLine(0, 1, 0, gameObject.dimension.height - 1);
        g2d.setColor(Color.black);
        g2d.drawLine(gameObject.dimension.width - 1, 0, gameObject.dimension.width - 1, gameObject.dimension.height - 2);
        g2d.drawLine(1, gameObject.dimension.height - 1, gameObject.dimension.width - 1, gameObject.dimension.height - 1);

        g2d.drawRect(
                (int)gameObject.transform.position.x - 10, (int)gameObject.transform.position.y,
                gameObject.dimension.width -1, gameObject.dimension.height-1
        );
    }
}
