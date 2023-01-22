package com.wisekrakr.w2dge.visual;

import com.wisekrakr.w2dge.constants.GameConstants;

import javax.swing.*;
import java.awt.*;

public class Screen extends AbstractScreen {

    public Screen(String title, int width, int height, boolean isResizable) {
        super(title, width, height, isResizable);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static Screen getInstance() {
        if (currentScreen == null) {
            currentScreen = new Screen(
                    GameConstants.SCREEN_TITLE, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT, false
            );
        }
        return (Screen) currentScreen;
    }

    @Override
    public void init() {
        this.addInputListener();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
    }

    @Override
    public void terminate() {
        super.terminate();
    }
}
