package com.wisekrakr.main;

import com.wisekrakr.w2dge.visual.Screen;
import com.wisekrakr.w2dge.visual.assets.AssetManager;

/**
 * Class that initializes all Objects needed throughout the game.
 */
public class Game {

    public enum SceneType{
        PAUSE,LOADING, MENU, EDITOR, LEVEL_1
    }

    public Thread mainThread;
    public static boolean isPaused = false;

    public Game() {
        AssetManager.loadAllSpriteSheets(); // todo all sprites in loading screen instead when creating a GameObject

        Screen screen = Screen.getInstance();
        screen.init();

        // todo if all loaded -> change screen
        screen.changeScene(SceneType.EDITOR);

        mainThread = new Thread(screen);
        mainThread.start();

    }
}
