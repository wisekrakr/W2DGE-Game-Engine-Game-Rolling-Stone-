package com.wisekrakr.w2dge.game.components.physics;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.components.Component;

public class TriggerComponent extends Component<TriggerComponent> {

    @Override
    public Component<TriggerComponent> copy() {
        return new TriggerComponent();
    }

    @Override
    public String serialize(int tabSize) {
        StringBuilder builder = new StringBuilder();
        builder.append(beginObjectProperty(Names.TRIGGER, tabSize));
        builder.append(closeObjectProperty(tabSize));

        return builder.toString();
    }

    public static TriggerComponent deserialize() {
        Parser.consumeEndObjectProperty();
        return new TriggerComponent();
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }
}
