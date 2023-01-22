package com.wisekrakr.w2dge.visual;

import com.wisekrakr.main.Game;
import com.wisekrakr.util.Time;
import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.input.GameInputListener;
import com.wisekrakr.w2dge.input.KeyListener;
import com.wisekrakr.w2dge.input.MouseListener;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.visual.scene.LevelEditorScene;
import com.wisekrakr.w2dge.visual.scene.LevelScene;
import com.wisekrakr.w2dge.visual.scene.Scene;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractScreen extends JFrame implements Runnable, GameLoopImpl {

    public static AbstractScreen currentScreen;
    public boolean isInEditorPhase = false;
    protected boolean isRunning = true;

    public MouseListener mouseListener;
    public KeyListener keyListener;
    public GameInputListener inputListener;

    protected Scene currentScene = null;

    /**
     * The image we use to draw objects on to, and then we'll draw at once in one draw call onto the actual screen,
     * inside the render method
     */
    protected Image doubleBufferImage = null;

    protected Graphics doubleBufferGraphics = null;

    public AbstractScreen(String title, Dimension dimension, boolean isResizable) {
        super(title);

        this.setTitle(title);
        this.setSize((int) dimension.width, (int) dimension.height);
        this.setResizable(isResizable);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public Scene getCurrentScene() {
        return Screen.getInstance().currentScene;
    }

    public void changeScene(Game.SceneType scene) {
        switch (scene) {
            case LOADING:
                break;
            case MENU:
                break;
            case EDITOR: // LevelEditorScene
                isInEditorPhase = true;
                this.currentScene = new LevelEditorScene("Level editor");
                break;
            case LEVEL_1: // LevelScene
                isInEditorPhase = false;
                this.currentScene = new LevelScene("Level");
                break;
            default:
                System.out.println("This is not a scene");
                this.currentScene = null;
        }
        this.currentScene.init();
    }

    protected void addInputListener(){
        this.inputListener = new GameInputListener();

        this.mouseListener = inputListener.mouseListener;
        this.keyListener = inputListener.keyListener;

        this.addMouseListener(mouseListener); // mouse clicking and dragging
        this.addMouseMotionListener(mouseListener); // mouse movement
        this.addKeyListener(keyListener); // keyboard input

    }

    @Override
    public void update(double deltaTime) {
        Screen.getInstance().currentScene.update(deltaTime);
        render((Graphics2D) getGraphics());
    }

    @Override
    public void render(Graphics2D g2d) {
        if (this.doubleBufferImage == null) {
            this.doubleBufferImage = createImage(getWidth(), getHeight());
            this.doubleBufferGraphics = this.doubleBufferImage.getGraphics();
        }

        renderOffScreen(doubleBufferGraphics);

        g2d.drawImage(doubleBufferImage, 0, 0, getWidth(), getHeight(), null);
    }

    private void renderOffScreen(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Screen.getInstance().currentScene.render(g2d);
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;

        try {
            while (isRunning) {
                double time = Time.getTime();
                double deltaTime = time - lastFrameTime;
                lastFrameTime = time;

                update(deltaTime);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void terminate() {
        isRunning = false;
        dispose();
    }

}
