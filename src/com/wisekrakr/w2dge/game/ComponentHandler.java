package com.wisekrakr.w2dge.game;

import com.wisekrakr.w2dge.game.components.Component;

import java.util.List;

public interface ComponentHandler {
    /**
     * Loops over all {@link GameObject} components.<br>
     * If the componentClass is assignable from the GameObject class we are currently working with.<br>
     * Returns null if there is no such {@link Component}.
     *
     * @param componentClass {@link Component} child
     * @return {@link Component}
     * @param <T> Any {@link Component} class that is assigned to a {@link GameObject}
     */
    <T extends Component>T getComponent(Class<T>componentClass);

    void addComponent(Component component);

    void addComponents(List<Component> components);

    void removeComponent(Component component);

}
