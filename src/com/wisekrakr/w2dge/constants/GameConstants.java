package com.wisekrakr.w2dge.constants;

public class GameConstants {
    private static final float WIDTH = 42;
    private static final float HEIGHT = 42;

    // =======================================================
    // Physics Constants
    // =======================================================
    public static final int GRAVITY = 2000;
    public static final int TERMINAL_VELOCITY = 2000;

    // =======================================================
    // Window properties
    // =======================================================
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final String SCREEN_TITLE = "Rolling Stone";

    // =======================================================
    // Background image constants
    // =======================================================
    public static final int BG_WIDTH = 512;
    public static final int BG_HEIGHT = 512;
    public static final int GROUND_BG_WIDTH = 256;
    public static final int GROUND_BG_HEIGHT = 256;
    public static final float BG_SPEED = 80f;
    public static final float BG_LESSER_SPEED = 35f;


    // =======================================================
    // Player constants
    // =======================================================
    public static final float PLAYER_START_X = 500;
    public static final float PLAYER_START_Y = 350;
    public static final float PLAYER_WIDTH = WIDTH - 2;
    public static final float PLAYER_HEIGHT = HEIGHT - 2;
    public static final int PLAYER_SPEED = 350;
    public static final float ROTATION_SPEED = 10f;
    public static final float JUMP_FORCE = -650f;
    public static final int FLY_FORCE = -45;
    public static final int FLY_TERMINAL_VELOCITY = 200;


    // =======================================================
    // Tile constants
    // =======================================================
    public static final float TILE_WIDTH = WIDTH;
    public static final float TILE_HEIGHT = HEIGHT;
    public static final float ONE_TENTH_TILE_WIDTH = 4;
    public static final float GRID_WIDTH = WIDTH;
    public static final float GRID_HEIGHT = HEIGHT;
    public static final int GRID_Y_LINES = 31;
    public static final int GRID_X_LINES = 20;

    // =======================================================
    // Camera constants
    // =======================================================
    public static final int GROUND_Y = 714;
    public static final float GROUND_HEIGHT = 3 * GameConstants.TILE_WIDTH;
    public static final int CAMERA_OFFSET_Y = 350;
    public static final int CAMERA_OFFSET_X = 300;
    public static final int CAMERA_OFFSET_GROUND_Y = 200;
    public static final int CAMERA_OFFSET_GROUND_X = 10;
    public static final int CAMERA_BOX_TOP_Y = 250;
    public static final int CAMERA_BOX_BOTTOM_Y = 450;

    // =======================================================
    // Level editor UI constants
    // =======================================================
    public static final float BUTTON_WIDTH = 58;
    public static final float BUTTON_HEIGHT = 58;
    public static final float BIG_BUTTON_WIDTH = 90;
    public static final int MENU_CONTAINER_Y = 460;
    public static final int BUTTON_OFFSET_X = 300;
    public static final int BUTTON_OFFSET_Y = 480;
    public static final int BUTTON_HORIZONTAL_SPACING = 8;
    public static final int BUTTON_VERTICAL_SPACING = 5;

    public static final float TAB_WIDTH = 75;
    public static final float TAB_HEIGHT = 38;
    public static final int TAB_OFFSET_X = 300;
    public static final int TAB_OFFSET_Y = 422;
    public static final int TAB_HORIZONTAL_SPACING = 10;

    // =======================================================
    // Miscellaneous
    // =======================================================
    public static final char[] WHITESPACE = {'\n', ' ', '\t', '\r'};

}
