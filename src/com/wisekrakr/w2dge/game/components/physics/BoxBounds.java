package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.math.Dimension;

public class BoxBounds extends Component<BoxBounds> {

    public Dimension dimension;

    public BoxBounds(Dimension dimension) {
        this.dimension = dimension;
    }


}
