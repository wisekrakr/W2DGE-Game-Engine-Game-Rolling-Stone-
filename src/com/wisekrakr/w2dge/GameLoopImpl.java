package com.wisekrakr.w2dge;

import java.awt.*;

public interface GameLoopImpl {

    void init();
    void update(double deltaTime);
    void render(Graphics2D g2d);
    void terminate();
}
