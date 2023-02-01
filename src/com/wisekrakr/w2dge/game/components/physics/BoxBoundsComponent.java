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
                this.gameObject.transform.position.x + this.buffer.x + this.halfDimension.width ,
                this.gameObject.transform.position.y + this.buffer.y + this.halfDimension.height
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
        BoxBoundsComponent bounds = new BoxBoundsComponent(dimension.copy());
        bounds.buffer = new Vector2(this.buffer.x, this.buffer.y);
        return bounds;
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty(Names.BOX_BOUNDS, tabSize));
        builder.append(dimension.serialize(tabSize + 1));
        builder.append(addFloatProperty("xBuffer", this.buffer.x, tabSize + 1, true, true));
        builder.append(addFloatProperty("yBuffer", this.buffer.y, tabSize + 1, true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static BoxBoundsComponent deserialize() {
        Dimension d = Dimension.deserialize();
        float xBuffer = Parser.consumeFloatProperty("xBuffer");
        Parser.consume(',');
        float yBuffer = Parser.consumeFloatProperty("yBuffer");
        Parser.consumeEndObjectProperty();

        BoxBoundsComponent bounds = new BoxBoundsComponent(d);
        bounds.buffer = new Vector2(xBuffer, yBuffer);

        return bounds;
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }


}
