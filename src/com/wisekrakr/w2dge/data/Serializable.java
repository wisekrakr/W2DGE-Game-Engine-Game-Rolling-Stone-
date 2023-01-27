package com.wisekrakr.w2dge.data;

public abstract class Serializable {
    public static class Names {
        public static final String GAME_OBJECT = "GameObject";
        public static final String SPRITE  = "Sprite";
        public static final String BOX_BOUNDS = "BoxBounds";
        public static final String TRIANGLE_BOUNDS = "TriangleBounds";
        public static final String GAME_ITEM = "Game Item";
        public static final String TRANSFORM  = "Transform";
        public static final String DIMENSION  = "Dimension";
        public static final String VECTOR2  = "Vector2";
    }

    public abstract String serialize(int tabSize);


    public static String addStringProperty(String name, String value, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + "\"" + value + "\"" + addEnding(newline, comma);
    }

    public static String addIntProperty(String name, int value, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + value + addEnding(newline, comma);
    }

    public static String addFloatProperty(String name, float value, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + value + "f" + addEnding(newline, comma);
    }

    public static String addDoubleProperty(String name, double value, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + value + addEnding(newline, comma);
    }

    public static String addBooleanProperty(String name, boolean val, int tabSize, boolean newline, boolean comma) {
        return addTabs(tabSize) + "\"" + name + "\": " + val + addEnding(newline, comma);
    }

    public static String beginObjectProperty(String name, int tabSize) {
        return addTabs(tabSize) + "\"" + name + "\": {" + addEnding(true, false);
    }

    public static String closeObjectProperty(int tabSize) {
        return addTabs(tabSize) + "}";
    }

    public static String addTabs(int tabSize) {
        return "\t".repeat(Math.max(0, tabSize));
    }

    public static String addEnding(boolean newline, boolean comma) {
        String str = "";
        if (comma) str += ",";
        if (newline) str += "\n";
        return str;
    }
}
