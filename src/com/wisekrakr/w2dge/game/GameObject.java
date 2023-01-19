package com.wisekrakr.w2dge.game;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameObject implements GameLoopImpl, ComponentImpl, GameObjectImpl {
    public final List<Component<?>> components;
    public final String name;
    public Transform transform;
    public final Dimension dimension;

    public GameObject(String name, Transform transform, Dimension dimension) {
        this.name = name;
        this.transform = transform;
        this.dimension = dimension;
        this.components = new ArrayList<>();
    }

    public GameObject(String name, Transform transform, Dimension dimension, Component<?>... components) {
        this.name = name;
        this.transform = transform;
        this.dimension = dimension;
        this.components = new ArrayList<>();

        for (Component<?> c : components) {
            addComponent(c);
        }
    }

    @Override
    public void init() {
        for (Component<?> component : components) {
            component.init();
        }
    }

    @Override
    public void update(double deltaTime) {
        for (Component<?> component : components) {
            component.update(deltaTime);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        for (Component<?> component : components) {
            component.render(g2d);
        }
    }

    @Override
    public void terminate() {
        // TODO a way to dispose of an Object
    }

    @Override
    public void addComponent(Component<?> component) {
        this.components.add(component);
        component.gameObject = this;
    }

    @Override
    public void addComponents(List<Component<?>> components) {
        for (Component<?> c : components) {
            addComponent(c);
        }
    }

    @Override
    public <T extends Component<T>> void removeComponent(Class<T> componentClass) {
        for (Component<?> c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(c);
                return; // so we don't get an error modifying components while looping through it
            }
        }
    }

    @Override
    public <T extends Component<T>> T getComponent(Class<T> componentClass) {
        for (Component<?> c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    this.terminate();
                }
            }
        }
        return null;
    }

    @Override
    public List<Component<?>> getAllComponents() {
        return this.components;
    }

    @Override
    public GameObject copy() {
        GameObject gameObject = new GameObject("Generated " + this.name, this.transform.copy(), this.dimension.copy());
        for (Component<?> c : components) {
            Component<?> copy = c.copy();
            if (copy != null){
                gameObject.addComponent(copy);
            }
        }
        return gameObject;
    }

    @Override
    public boolean inMouseBounds() {
        Screen screen = Screen.getInstance();
        return screen.mouseListener.position.x > this.transform.position.x &&
                screen.mouseListener.position.x <= this.transform.position.x + this.dimension.width &&
                screen.mouseListener.position.y > this.transform.position.y &&
                screen.mouseListener.position.y <= this.transform.position.y + this.dimension.height;
    }

    @Override
    public void centering() {
        this.dimension.center = new Vector2(
                this.dimension.width * this.transform.scale.x / 2.0f,
                this.dimension.height * this.transform.scale.y / 2.0f
        );
    }

    @Override
    public String toString() {
        return "GameObject{" +
                ", name='" + name +
                ", components= " + components + '\'' +
                '}';
    }
}
