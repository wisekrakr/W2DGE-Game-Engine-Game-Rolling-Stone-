package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.CollisionManager;
import com.wisekrakr.w2dge.math.Dimension;
import com.wisekrakr.w2dge.math.Vector2;


public abstract class BoundsComponent<T> extends Component<T> implements BoundsComponentImpl {

    public enum BoundsType {
        BOX, TRIANGLE
    }

    protected static final int INSIDE = 0; //0000
    protected static final int LEFT = 1;   //0001
    protected static final int RIGHT = 2;  //0010
    protected static final int BOTTOM = 4; //0100
    protected static final int TOP = 8;    //1000

    public BoundsType type;

    public Dimension dimension;
    public Dimension halfDimension;
    public Vector2 center;
    protected float enclosingRadius;

    public BoundsComponent(Dimension dimension) {
        this.dimension = dimension;
        this.halfDimension = new Dimension(dimension.width / 2.0f, dimension.height / 2.0f);
    }

    @Override
    public void init() {
        initialCalculations();
    }

    @Override
    public boolean checkCollision(BoundsComponent<?> b1, BoundsComponent<?> b2) {
        return collision(b1, b2);
    }

    @Override
    public void resolveCollision(BoundsComponent<?> boundsComponent, GameObject gameObject, CollisionManager.HitType type) {
        BoxBoundsComponent playerBounds = gameObject.getComponent(BoxBoundsComponent.class);
        playerBounds.initialCalculations();
        this.initialCalculations();

        CollisionManager.collisionResolver(this.gameObject, gameObject, type);
    }

    protected Vector2 rotatePoint(double angle, Vector2 position, Vector2 origin) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        Vector2 newVector = new Vector2(position.x, position.y);
        newVector.x -= origin.x;
        newVector.y -= origin.y;

        float newX = (float) ((newVector.x * cos) - (newVector.y * sin));
        float newY = (float) ((newVector.x * sin) + (newVector.y * cos));

        return new Vector2(newX + origin.x, newY + origin.y);
    }

    /**
     * This is a recursive method (this method is called upon itself) <br>
     * Checks if a box is intersecting with a line <br> <h3>using Cohen Sutherland clipping algorithm</h3>
     *
     * @param p1       represents an endpoint of  line
     * @param p2       represents an endpoint of  line
     * @param depth    recursive in nature, this is to bail, if it goes to far into depth
     * @param bounds   bounds of the object
     * @param position position of the object
     * @return
     */
    protected boolean isIntersecting(Vector2 p1, Vector2 p2, int depth, BoundsComponent<?> bounds, Vector2 position) {
        // Cohen Sutherland clipping algorithm
        if (depth > 5) {
            System.err.println("BoundsComponent.isIntersecting -  Max depth exceed: " + depth);
            return true;
        }

        int code1 = computeRegionCode(p1, bounds);
        int code2 = computeRegionCode(p2, bounds);

        // Check if the line is inside or outside or half in and half out
        if (code1 == 0 && code2 == 0){
            // Line is inside
            return true;
        } else if ((code1 & code2) != 0) {
            // Line is outside
            return false;
        }else if (code1 == 0 || code2 == 0){
            // One point is inside and one point is outside
            return true;
        }

        // If there is no intersection we have to clip it and rerun the algorithm
        int xMax = (int)(position.x + bounds.dimension.width);
        int xMin = (int) position.x;

        // y = slopeX + intersect
        float slope = (p2.y - p1.y) / (p2.x - p1.x); // slope of the line
        float intersect = p2.y - (slope * p2.x); // intersect of the line

        p1.y = calculateFinalYIntersect(code1, p1, xMin, xMax, slope, intersect); // calculate what the final y would be based on the x
        p2.y = calculateFinalYIntersect(code2, p2, xMin, xMax, slope, intersect); // calculate what the final y would be based on the x

        return isIntersecting(p1, p2, ++depth, bounds, position);
    }

    private float calculateFinalYIntersect(int code, Vector2 p, float min, float max, float slope, float intersect){
        // Compute where to clip
        if ((code & LEFT) == LEFT){ // if the point is on the left
            // Add 1 to ensure we're inside the clipping polygon
            p.x = min + 1;
        }else if ((code & RIGHT) == RIGHT){ // if the point is on the right
            // Subtract 1 to ensure we're inside the clipping polygon
            p.x = max - 1;
        }
        return (slope * p.x) + intersect; // calculate what the final y would be based on the x
    }

    /**
     * Compute at what region point the bounds are colliding
     * @param point
     * @param bounds
     * @return
     */
    protected int computeRegionCode(Vector2 point, BoundsComponent<?> bounds) {
        int code = INSIDE;
        Vector2 topLeft = bounds.gameObject.transform.position;

        // Check if the point is to the left or right of bounds
        if (point.x < topLeft.x){
            code |= LEFT;
        } else if (point.x > topLeft.x + bounds.dimension.width) {
            code |= RIGHT;
        }

        // Check if the point is to the top or bottom of bounds
        if (point.y < topLeft.y){
            code |= TOP;
        } else if (point.y > topLeft.y + bounds.dimension.height) {
            code |= BOTTOM;
        }

        return code;
    }

    /**
     * The user can determine initial calculations for the Component <br>
     * - BoxBounds needs a center and TriangleBounds needs
     * to recalculate its {@link com.wisekrakr.w2dge.math.Transform} for instance
     */
    protected abstract void initialCalculations();

    protected boolean broadPhase(BoundsComponent<?> b) {
        return false;
    }

    /**
     * is a line intersection with any bounds positions
     * @param b
     * @return
     */
    protected boolean narrowPhase(BoundsComponent<?> b) {
        return false;
    }

    @Override
    public String name() {
        return getClass().getName();
    }

    @Override
    public String toString() {
        return "BoundsComponent{" +
                "type=" + type +
                ", dimension=" + dimension +
                ", halfDimension=" + halfDimension +
                '}';
    }
}
