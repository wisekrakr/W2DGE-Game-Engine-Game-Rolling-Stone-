package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;

public class LevelEditMenuItem<T> extends Component<T> {

    protected final Transform transform;
    protected final Dimension dimension;
    protected final Sprite sprite;

    protected final LevelEditMenuContainer parentContainer;

    public boolean isSelected;

    public LevelEditMenuItem(Transform transform, Dimension dimension, LevelEditMenuContainer parentContainer, Sprite sprite) {
        this.transform = transform;
        this.dimension = dimension;
        this.parentContainer = parentContainer;
        this.sprite = sprite;
        this.isSelected = false;
    }

    @Override
    public Component<T> copy() {
        return new LevelEditMenuItem<T>(transform.copy(), dimension.copy(), parentContainer, (Sprite) sprite.copy());
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
