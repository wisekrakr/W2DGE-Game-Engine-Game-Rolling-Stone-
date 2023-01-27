package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Vector2;


public class BoxBoundsComponent extends BoundsComponent<BoxBoundsComponent> {

    public BoxBoundsComponent(Dimension dimension) {
        super(dimension);

        this.type = BoundsType.BOX;
    }

    @Override
    protected void initialCalculations() {
        this.enclosingRadius = (float) Math.sqrt((this.halfDimension.width * this.halfDimension.width) +
                (this.halfDimension.height * this.halfDimension.height));

        this.center = new Vector2(
                this.gameObject.transform.position.x + this.halfDimension.width,
                this.gameObject.transform.position.y + this.halfDimension.height
        );
    }

    @Override
    public boolean collision(BoundsComponent<?> b1, BoundsComponent<?> b2) {
        b1.initialCalculations();
        b2.initialCalculations();

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
    public Component<BoxBoundsComponent> copy() {
        return new BoxBoundsComponent(dimension.copy());
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty(Names.BOX_BOUNDS, tabSize));
        builder.append(dimension.serialize(tabSize + 1));
        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static BoxBoundsComponent deserialize() {
        Dimension d = Dimension.deserialize();
        Parser.consumeEndObjectProperty();

        return new BoxBoundsComponent(d);
    }

    @Override
    public String name() {
        return getClass().getName();
    }


}
