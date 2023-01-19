package com.wisekrakr.w2dge.math;

import com.wisekrakr.w2dge.InterprocessImpl;

public class Transform implements InterprocessImpl<Transform> {
    public Vector2 position;
    public float rotation;
    public Vector2 scale;

    public Transform(Vector2 position) {
        this.position = position;
        this.scale = new Vector2(1.0f, 1.0f);
        this.rotation = 0.0f;
    }

    @Override
    public Transform copy() {
        Transform transform = new Transform(this.position.copy());
        transform.scale = this.scale.copy();
        transform.rotation = this.rotation;
        return transform;
    }

    @Override
    public String toString() {
        return "Position= " + position;
    }
}
