package com.wisekrakr.w2dge.data;

import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.entities.GameItemComponent;
import com.wisekrakr.w2dge.game.components.entities.PortalComponent;
import com.wisekrakr.w2dge.game.components.graphics.SpriteComponent;
import com.wisekrakr.w2dge.game.components.physics.BoxBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.ElevateComponent;
import com.wisekrakr.w2dge.game.components.physics.TriangleBoundsComponent;
import com.wisekrakr.w2dge.game.components.physics.TriggerComponent;

public class Parser {
    public static int offset = 0;
    public static int line = 1;
    public static byte[] bytes;

    public static void skipWhitespace() {
        while (!atEnd() && (peek() == ' ' || peek() == '\n' || peek() == '\t' || peek() == '\r' || (byte) peek() == 0)) {
            if (peek() == '\n') {
                Parser.line++;
            }
            advance();
        }
    }

    /**
     * @return the current character
     */
    public static char peek() {
        return (char) bytes[offset];
    }

    public static char advance() {
        char c = (char) bytes[offset];
        offset++;
        return c;
    }

    /**
     * Searches for a character
     *
     * @param character the value to be removed
     */
    public static void consume(char character) {
        char actual = peek();
        if (actual != character) {
            System.err.println("Error: Expected '" + character + "' but instead got '" + actual + "' at line: " + Parser.line);
            System.exit(-1);
        }
        offset++;
    }

    /**
     * @return true if the end of the file is reached
     */
    public static boolean atEnd() {
        return offset == bytes.length;
    }

    /**
     * @return an integer if not negative and adds it to a StringBuilder
     */
    public static int parseInt() {
        skipWhitespace();
        char c;
        StringBuilder builder = new StringBuilder();

        // while not at the end, looking at a digit that is not negative
        while (!atEnd() && isDigit(peek()) || peek() == '-') {
            c = advance();
            builder.append(c);
        }

        return Integer.parseInt(builder.toString());
    }

    /**
     * @return a double without decimal point and if it is not negative and adds it to a StringBuilder
     */
    public static double parseDouble() {
        skipWhitespace();
        char c;
        StringBuilder builder = new StringBuilder();

        // while not at the end, looking at a digit that is not negative or has decimal point
        while (!atEnd() && isDigit(peek()) || peek() == '-' || peek() == '.') {
            c = advance();
            builder.append(c);
        }

        return Double.parseDouble(builder.toString());
    }

    /**
     * @return a float without the 'f' character behind the value
     */
    public static float parseFloat() {
        float f = (float) parseDouble();
        consume('f');
        return f;

    }

    /**
     * @return a string value within a file if not at the end of the file
     */
    public static String parseString() {
        skipWhitespace();
        char c;
        StringBuilder builder = new StringBuilder();
        consume('"');

        // while not at the end, and is not a quotation mark
        while (!atEnd() && peek() != '"') {
            c = advance();
            builder.append(c);
        }
        consume('"');

        return builder.toString();
    }

    /**
     * Parsing a boolean value within a file
     *
     * @return true if either true or false is part of the file
     */
    public static boolean parseBoolean() {
        skipWhitespace();
        StringBuilder builder = new StringBuilder();

        if (!atEnd() && peek() == 't') { // while not at the end, looking at a digit that is 't' for true
            builder.append("true");
            consume('t');
            consume('r');
            consume('u');
            consume('e');
        } else if (!atEnd() && peek() == 'f') { // while not at the end, looking at a digit that is 'f' for false
            builder.append("false");
            consume('f');
            consume('a');
            consume('l');
            consume('s');
            consume('e');
        } else {
            System.err.println("Expecting 'true' or 'false' instead got: " + peek() + " at line: " + Parser.line);
        }

        return builder.toString().compareTo("true") == 0;
    }

    /**
     * A character between 0-9
     *
     * @param c primitive character
     * @return true if the char is between 0-9
     */
    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static GameObject parseGameObject() {
        if (bytes.length == 0 || atEnd()) {
            return null;
        }

        if (peek() == ',') {
            Parser.consume(',');
        }
        skipWhitespace();

        if (atEnd()) {
            return null;
        }

        return GameObject.deserialize();
    }

    public static Component<?> parseComponent() {
        String componentTitle = Parser.parseString();
        skipWhitespace();
        Parser.consume(':');
        skipWhitespace();
        Parser.consume('{');

        switch (componentTitle) {
            case Serializable.Names.SPRITE -> {
                return SpriteComponent.deserialize();
            }
            case Serializable.Names.BOX_BOUNDS -> {
                return BoxBoundsComponent.deserialize();
            }
            case Serializable.Names.TRIANGLE_BOUNDS -> {
                return TriangleBoundsComponent.deserialize();
            }
            case Serializable.Names.ELEVATE -> {
                return ElevateComponent.deserialize();
            }
            case Serializable.Names.TRIGGER -> {
                return TriggerComponent.deserialize();
            }
            case Serializable.Names.PORTAL -> {
                return PortalComponent.deserialize();
            }
            case Serializable.Names.GAME_ITEM -> {
                return GameItemComponent.deserialize();
            }

            default -> {
                System.err.println("Could not find component '" + componentTitle + "' at line: " + Parser.line);
                System.exit(-1);
            }

        }

        return null;
    }


    public static String consumeStringProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseString();
    }

    public static int consumeIntProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseInt();
    }

    public static float consumeFloatProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseFloat();
    }

    public static double consumeDoubleProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseDouble();
    }

    public static boolean consumeBooleanProperty(String name) {
        skipWhitespace();
        checkString(name);
        consume(':');
        return parseBoolean();
    }

    /**
     * Consumes JSON file end property
     */
    public static void consumeEndObjectProperty() {
        skipWhitespace();
        consume('}');
    }

    /**
     * Consumes JSON file properties with a certain name
     *
     * @param name Name of the property within the file
     */
    public static void consumeBeginObjectProperty(String name) {
        skipWhitespace();
        checkString(name);
        skipWhitespace();
        consume(':');
        skipWhitespace();
        consume('{');
    }

    /**
     * Checks if we got the String that was expected
     *
     * @param str
     */
    private static void checkString(String str) {
        String title = Parser.parseString();
        if (title.compareTo(str) != 0) {
            System.err.println("Expected '" + str + "' instead got '" + title + "' at line: " + Parser.line);
            System.exit(-1);
        }
    }
}
