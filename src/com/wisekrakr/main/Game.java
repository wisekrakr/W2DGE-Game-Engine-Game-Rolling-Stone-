package com.wisekrakr.main;

import com.wisekrakr.w2dge.visual.Screen;

/**
 * Class that initializes all Objects needed throughout the game.
 */
public class Game {

    public enum SceneType{
        LOADING, MENU, EDITOR, LEVEL_1
    }

    public final Thread mainThread;

    public Game() {
        Screen screen = Screen.getInstance();
        screen.init();
        screen.changeScene(SceneType.EDITOR);

        mainThread = new Thread(screen);
        mainThread.start();
    }
}
