package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.components.Component;

public class ElevateComponent extends Component<ElevateComponent> {

    @Override
    public Component<ElevateComponent> copy() {
        return new ElevateComponent();
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();
        builder.append(beginObjectProperty(Names.ELEVATE, tabSize));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static ElevateComponent deserialize(){
        Parser.consumeEndObjectProperty();
        return new ElevateComponent();
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
