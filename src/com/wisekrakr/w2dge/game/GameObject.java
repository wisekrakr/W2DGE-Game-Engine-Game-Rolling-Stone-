package com.wisekrakr.w2dge.game;

import com.wisekrakr.w2dge.GameLoopImpl;
import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.data.Serializable;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Transform;
import com.wisekrakr.w2dge.math.Vector2;
import com.wisekrakr.w2dge.visual.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameObject extends Serializable implements GameLoopImpl, ComponentImpl, GameObjectImpl {
    public final List<Component<?>> components;
    public final String name;
    public Transform transform;
    public final Dimension dimension;
    private boolean isSerializable = true;

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
        if (component != null){
            this.components.add(component);
            component.gameObject = this;
        }

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
            if (copy != null) {
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
    public String serialize(int tabSize) {
        if (!isSerializable){
            return "";
        }

        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("GameObject", tabSize)); // Game Object

//        builder.append(addStringProperty("Name", name, tabSize + 1, true, true));// Name

        builder.append(transform.serialize(tabSize + 1)); // Transform
        builder.append(addEnding(true, true));

//        builder.append(dimension.serialize(tabSize + 1)); // Dimension
//        builder.append(addEnding(true, true));

        if (components.size() > 0) {
            builder.append(addStringProperty("Name", name, tabSize + 1, true, true));// Name
            builder.append(beginObjectProperty("Components", tabSize + 1));// Components
        }else {
            builder.append(addStringProperty("Name", name, tabSize + 1, true, false));// Name
        }

        int i = 0;
        for (Component<?> c: components){
            String str = c.serialize(tabSize + 2); // get component string
            if (str.compareTo("") != 0){ // if not empty
                builder.append(str); // add to StringBuilder
                if (i != components.size() - 1){
                    builder.append(addEnding(true, true)); // if there are more components - keep it
                }else {
                    builder.append(addEnding(true, false));// if there are no more components - close it
                }
            }
            i++;
        }

        if (components.size() > 0){
            builder.append(closeObjectProperty(tabSize + 1));
        }

        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static GameObject deserialize() {
        Parser.consumeBeginObjectProperty("GameObject");

//        String name = Parser.consumeStringProperty("Name");
//        Parser.consume(',');

        Transform t = Transform.deserialize();
        Parser.consume(',');
//        Parser.consumeEndObjectProperty();

//        Dimension d = Dimension.deserialize();
//        Parser.consume(',');

        String name = Parser.consumeStringProperty("Name");
//        Parser.consume(',');
//        Parser.consumeEndObjectProperty();

        GameObject gameObject = new GameObject(name, t, new Dimension(42,42));

        if (Parser.peek() == ','){
            Parser.consume(',');
            Parser.consumeBeginObjectProperty("Components");
            gameObject.addComponent(Parser.parseComponent());

            while (Parser.peek() == ','){
                Parser.consume(',');
                gameObject.addComponent(Parser.parseComponent());
            }

            Parser.consumeEndObjectProperty();
        }
        Parser.consumeEndObjectProperty();

        return gameObject;
    }



    /**
     * When a Game Object is flagged with this, it will not create serializable content when we save the current content
     * on the screen in a JSON file
     */
    public void setNonSerializable(){
        this.isSerializable = false;
    }


    @Override
    public String toString() {
        return "GameObject{" +
                "name='" + name + '\'' +
                ", components=" + components +
                '}';
    }
}
