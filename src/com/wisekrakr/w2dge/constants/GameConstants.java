package com.wisekrakr.w2dge.constants;

public class GameConstants {

    // =======================================================
    // Physics Constants
    // =======================================================
    public static final int GRAVITY = 300;
    public static final int TERMINAL_VELOCITY = 750;

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

    // =======================================================
    // Player constants
    // =======================================================
    public static final int PLAYER_WIDTH = 42;
    public static final int PLAYER_HEIGHT = 42;
    public static final int SPEED = 40;
    public static final int JUMP_FORCE = -65;
    public static final int FLY_FORCE = -45;
    public static final int FLY_TERMINAL_VELOCITY = 200;


    // =======================================================
    // Tile constants
    // =======================================================
    public static final int TILE_WIDTH = 42;
    public static final int ONE_TENTH_TILE_WIDTH = 4;
    public static final int GRID_WIDTH = 42;
    public static final int GRID_HEIGHT = 42;


    // =======================================================
    // Camera constants
    // =======================================================
    public static final int GROUND_Y = 650;
    public static final int GROUND_HEIGHT = 3 * GameConstants.TILE_WIDTH;
    public static final int CAMERA_OFFSET_Y = 350;
    public static final int CAMERA_OFFSET_X = 300;
    public static final int CAMERA_OFFSET_GROUND_Y = 150;
    public static final int CAMERA_OFFSET_GROUND_X = 10;
    public static final int CAMERA_BOX_TOP_Y = 250;
    public static final int CAMERA_BOX_BOTTOM_Y = 450;

    // =======================================================
    // Level editor UI constants
    // =======================================================
    public static final int BUTTON_WIDTH = 60;
    public static final int BIG_BUTTON_WIDTH = 90;
    public static final int MENU_CONTAINER_Y = 535;
    public static final int BUTTON_OFFSET_X = 400;
    public static final int BUTTON_OFFSET_Y = 560;
    public static final int BUTTON_HORIZONTAL_SPACING = 10;

    public static final int TAB_WIDTH = 75;
    public static final int TAB_HEIGHT = 38;
    public static final int TAB_OFFSET_X = 380;
    public static final int TAB_OFFSET_Y = 497;
    public static final int TAB_HORIZONTAL_SPACING = 10;

    // =======================================================
    // Miscellaneous
    // =======================================================
    public static final char[] WHITESPACE = {'\n', ' ', '\t', '\r'};

}
