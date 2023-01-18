package com.wisekrakr.w2dge.math;

import java.awt.geom.AffineTransform;

public class Transform {
    public Vector2 position;
    public Vector2 velocity;
    public float rotation;
    public Vector2 scale;

    public Transform(Vector2 position) {
        this.position = position;
        this.scale = new Vector2(1.0f,1.0f);
        this.rotation = 0.0f;

    }

    @Override
    public String toString() {
        return "Position= " + position;
    }
}
