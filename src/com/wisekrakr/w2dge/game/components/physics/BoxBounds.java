package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.CollisionManager;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Vector2;


public class BoxBounds extends Bounds<BoxBounds> {

    public Dimension dimension;
    public Dimension halfDimension;
    public Vector2 center;

    public BoxBounds(Dimension dimension) {
        this.dimension = dimension;
        this.halfDimension = new Dimension(dimension.width / 2.0f, dimension.height / 2.0f);
        this.type = BoundsType.BOX;
    }

    @Override
    protected void calculateCenter() {
        this.center = new Vector2(
                this.gameObject.transform.position.x + this.halfDimension.width,
                this.gameObject.transform.position.y + this.halfDimension.height
        );
    }

    @Override
    public boolean collision(BoxBounds b1, BoxBounds b2) {
        b1.calculateCenter();
        b2.calculateCenter();

        float dx = b2.center.x - b1.center.x;
        float dy = b2.center.y - b1.center.y;

        float combinedHalfWidth = b1.halfDimension.width + b2.halfDimension.width;
        float combinedHalfHeight = b1.halfDimension.height + b2.halfDimension.height;

        if (Math.abs(dx) <= combinedHalfWidth) {
            return Math.abs(dy) <= combinedHalfHeight;
        }

        return false;
    }


    @Override
    public void resolveColliding(GameObject player, CollisionManager.HitType type) {
        BoxBounds playerBounds = player.getComponent(BoxBounds.class);
        playerBounds.calculateCenter();
        this.calculateCenter();

        CollisionManager.collisionResolver(this.gameObject, player, type);
    }

    @Override
    public Component<BoxBounds> copy() {
        return new BoxBounds(dimension.copy());
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty("BoxBounds", tabSize));
        builder.append(dimension.serialize(tabSize + 1));
        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static BoxBounds deserialize() {
        Dimension d = Dimension.deserialize();
        Parser.consumeEndObjectProperty();

        return new BoxBounds(d);
    }

    @Override
    public String name() {
        return getClass().getName();
    }
}
