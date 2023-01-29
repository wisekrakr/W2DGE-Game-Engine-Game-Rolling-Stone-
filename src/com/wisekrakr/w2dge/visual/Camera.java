package com.wisekrakr.w2dge.visual;

import com.wisekrakr.w2dge.constants.GameConstants;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.math.Vector2;

public class Camera {

    public Vector2 position;
    public float rotation;

    public Camera(Vector2 position) {
        this.position = position;
    }

    /**
     * Follows a certain {@link GameObject} with a certain {@link com.wisekrakr.w2dge.constants.Tags}
     * @param gameObject
     */
    public void follow(GameObject gameObject) {

        // camera follows player
//        if (gameObject.transform.position.x - position.x > GameConstants.CAMERA_OFFSET_X) {
//            position.x = (float) (gameObject.transform.position.x + (rotation * Math.PI / 180) - GameConstants.CAMERA_OFFSET_X);
//        }
        position.x = (float) (gameObject.transform.position.x + (rotation * Math.PI / 180) - GameConstants.CAMERA_OFFSET_X);


        if (gameObject.transform.position.y - position.y > GameConstants.CAMERA_OFFSET_Y) {
            position.y = (float) (gameObject.transform.position.y + (rotation * Math.PI / 180) - GameConstants.CAMERA_OFFSET_Y);
        }

    }

    /**
     * Keeps the camera in bounds
     */
    public void bounds(Integer offsetX, Integer offsetY) {
        if (offsetX != null){
            if (position.x > offsetX) {
                position.x = offsetX;
            }
        }
        if (offsetY != null){
            if (position.y > offsetY) {
                position.y = offsetY;
            }
        }
    }

    public void rotate(){
        this.rotation += 90;
    }
}
