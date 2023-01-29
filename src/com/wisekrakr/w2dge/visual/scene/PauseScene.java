package com.wisekrakr.w2dge.visual.scene;

import com.wisekrakr.main.Game;
import com.wisekrakr.w2dge.constants.Colors;
import com.wisekrakr.w2dge.constants.GameConstants;

import javax.swing.*;
import java.awt.*;

public class PauseScene extends Scene{

    JLabel label;

    public PauseScene(String name) {
        super.createScene(name);

        this.type = Game.SceneType.PAUSE;
    }

    @Override
    public void init() {
        label = new JLabel("Testies");
        label.setBounds(300,300,100,100);
        label.setForeground(Colors.babyBlue);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);

        g2d.setColor(Colors.charcoal);
        g2d.fillRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT);

        label.update(g2d);

        getRenderer().render(g2d);
    }
}
