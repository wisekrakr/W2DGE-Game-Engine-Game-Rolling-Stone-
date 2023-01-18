package com.wisekrakr.main;

import com.wisekrakr.w2dge.visual.Screen;

public class Main {
    public static void main(String[] args) {
        Screen screen = Screen.getInstance();
        screen.init();

        Thread mainThread = new Thread(screen);
        mainThread.start();
    }
}