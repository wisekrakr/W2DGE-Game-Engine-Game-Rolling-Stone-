package com.wisekrakr.main;

import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.constants.Dimensions;

public class Main {
    public static void main(String[] args) {
        Screen screen = new Screen(
                "Rolling Stone", Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT, false
        );
        screen.init();

        Thread mainThread = new Thread(screen);
        mainThread.start();
    }
}