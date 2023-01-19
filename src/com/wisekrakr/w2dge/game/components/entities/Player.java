package com.wisekrakr.w2dge.game.components.entities;

import com.wisekrakr.w2dge.game.components.Component;

public class Player extends Component<Player> {

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        gameObject.centering();
    }

    @Override
    public Component<Player> copy() {
        return null;
    }
}
