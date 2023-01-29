package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Vector2;

public class TriangleBoundsComponent extends BoundsComponent<TriangleBoundsComponent> {

    public float x1, x2, x3, y1, y2, y3;

    public TriangleBoundsComponent(Dimension dimension) {
        super(dimension);
        this.type = BoundsType.TRIANGLE;
    }

    @Override
    protected void initialCalculations() {
        double angle = Math.toRadians(gameObject.transform.rotation);

        Vector2 p1 = new Vector2(gameObject.transform.position.x, gameObject.transform.position.y + dimension.height);
        Vector2 p2 = new Vector2(gameObject.transform.position.x + halfDimension.width, gameObject.transform.position.y);
        Vector2 p3 = new Vector2(gameObject.transform.position.x + dimension.width, gameObject.transform.position.y + dimension.height);

        Vector2 origin = new Vector2(gameObject.transform.position.x + halfDimension.width, gameObject.transform.position.y +
                halfDimension.height);

        p1 = rotatePoint(angle, p1, origin);
        p2 = rotatePoint(angle, p2, origin);
        p3 = rotatePoint(angle, p3, origin);

        this.x1 = p1.x;
        this.y1 = p1.y;
        this.x2 = p2.x;
        this.y2 = p2.y;
        this.x3 = p3.x;
        this.y3 = p3.y;

        this.enclosingRadius = Math.max(halfDimension.width, halfDimension.height);

        this.center = new Vector2(x2, y2 + halfDimension.height);
    }


    @Override
    public boolean collision(BoundsComponent<?> b1, BoundsComponent<?> b2) {
        if (b2.broadPhase(b1)) {
            return b2.narrowPhase(b1);
        }
        return false;
    }


    @Override
    protected boolean broadPhase(BoundsComponent<?> b) {
        float radius1 = b.enclosingRadius;
        float radius2 = this.enclosingRadius;

        float centerX = b.gameObject.transform.position.x + b.halfDimension.width;
        float centerY = b.gameObject.transform.position.y + b.halfDimension.height;

        Vector2 distanceBetween = new Vector2(centerX - center.x, centerY - center.y);
        float magSquared = (distanceBetween.x * distanceBetween.x) + (distanceBetween.y + distanceBetween.y); // a2 + b2 = c2.
        float radiiSquared = (radius1 + radius2) * (radius1 + radius2);

        return magSquared <= radiiSquared;
    }

    @Override
    protected boolean narrowPhase(BoundsComponent<?> b) {
        Vector2 p1 = new Vector2(x1, y1);
        Vector2 p2 = new Vector2(x2, y2);
        Vector2 p3 = new Vector2(x3, y3);

        // Origin is the center of box bounds - origin to rotate these points around so that they are at the same axis as the bounds
        Vector2 origin = new Vector2(
                b.gameObject.transform.position.x + (b.halfDimension.width),
                b.gameObject.transform.position.y + (b.halfDimension.height)
        );
        // The angle to rotate these points at.
        float angle = (float) Math.toRadians(b.gameObject.transform.rotation);

        // Rotate points around the center
        p1 = rotatePoint(angle, p1, origin);
        p2 = rotatePoint(angle, p2, origin);
        p3 = rotatePoint(angle, p3, origin);

        // Are the bounds intersection with any of b's bounds
        return (isIntersecting(p1, p2, 0, b, b.gameObject.transform.position) ||
                isIntersecting(p2, p3, 0, b, b.gameObject.transform.position) ||
                isIntersecting(p3, p1, 0, b, b.gameObject.transform.position));
    }

    @Override
    public Component<TriangleBoundsComponent> copy() {
        return new TriangleBoundsComponent(dimension.copy());
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();

        builder.append(beginObjectProperty(Names.TRIANGLE_BOUNDS, tabSize));
        builder.append(dimension.serialize(tabSize + 1));
        builder.append(addEnding(true, false));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static TriangleBoundsComponent deserialize() {
        Dimension d = Dimension.deserialize();
        Parser.consumeEndObjectProperty();

        return new TriangleBoundsComponent(d);
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
