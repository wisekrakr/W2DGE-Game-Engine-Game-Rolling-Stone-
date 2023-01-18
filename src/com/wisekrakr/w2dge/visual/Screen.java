package com.wisekrakr.w2dge.visual;

import javax.swing.*;
import java.awt.*;

public class Screen extends AbstractScreen {

    public Screen(String title, int width, int height, boolean isResizable) {
        super(title, width, height, isResizable);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void init() {
        this.addMouseListener();
        this.addKeyListener();

        changeScene(0);
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
