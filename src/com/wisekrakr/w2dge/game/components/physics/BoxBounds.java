package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;

@Deprecated
public class BoxBounds extends Component<BoxBounds> {

    public Dimension dimension;

    public BoxBounds(Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public Component<BoxBounds> copy() {
        return new BoxBounds(dimension.copy());
    }
}
