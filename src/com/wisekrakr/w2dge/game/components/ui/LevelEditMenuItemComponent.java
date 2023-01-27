package com.wisekrakr.w2dge.game.components.ui;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;

public class LevelEditMenuItemComponent<T> extends Component<T> {

    protected final Transform transform;
    protected final Dimension dimension;
    protected final SpriteComponent spriteComponent;

    protected final MenuContainerComponent parentContainer;

    public boolean isSelected;

    public LevelEditMenuItemComponent(Transform transform, Dimension dimension, MenuContainerComponent parentContainer, SpriteComponent spriteComponent) {
        this.transform = transform;
        this.dimension = dimension;
        this.parentContainer = parentContainer;
        this.spriteComponent = spriteComponent;
        this.isSelected = false;
    }

    @Override
    public Component<T> copy() {
        return new LevelEditMenuItemComponent<T>(transform.copy(), dimension.copy(), parentContainer, (SpriteComponent) spriteComponent.copy());
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
