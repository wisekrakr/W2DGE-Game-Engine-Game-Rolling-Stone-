package com.wisekrakr.w2dge.data;

import com.wisekrakr.util.FileUtils;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.game.components.Component;
import com.wisekrakr.w2dge.game.components.graphics.Sprite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Parser {
    private static int offset = 0;
    private static int line = 1;
    private static byte[] bytes;

    public static void openLevelFile(String filename) {
        File tmp = new File("assets/levels/" + filename + ".zip");
        if (!tmp.exists()) {
//            bytes = new byte[0];
            return;
        }
//        offset = 0;
//        line = 1;

        try {
            ZipFile zipFile = new ZipFile("assets/levels/" + filename + ".zip");
            ZipEntry jsonFile = zipFile.getEntry(filename + ".json");
            InputStream stream = zipFile.getInputStream(jsonFile);

            // Read input stream into a byte array
//            byte[] finalBytes = new byte[0];
//            while (stream.available() != 0) {
//                byte[] byteBuffer = new byte[stream.available()];
//                stream.read(byteBuffer);
//                finalBytes = FileUtils.combine(finalBytes, byteBuffer);
//            }
            Parser.bytes = stream.readAllBytes();

            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void openFile(String filename) {

        try {
            InputStream stream = new FileInputStream(new File("assets/levels/" + filename + ".json"));

            // Read input stream into a byte array
            byte[] finalBytes = new byte[0];
            while (stream.available() != 0) {
                byte[] byteBuffer = new byte[stream.available()];
                stream.read(byteBuffer);
                finalBytes = FileUtils.combine(finalBytes, byteBuffer);
            }
            Parser.bytes = finalBytes;

            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

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
//
//        assert builder.toString().length() != 0 : "Tried to parse double of 0 length at line '" + Parser.line +
//                "' index: '" + Parser.offset + "'";

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

        switch (componentTitle) {
            case "Sprite":
                skipWhitespace();
                Parser.consume(':');
                skipWhitespace();
                Parser.consume('{');
                return Sprite.deserialize();
            default:
                System.err.println("Could not find component '" + componentTitle + "' at line: " + Parser.line);
        }

        return null;
    }

    //    public static JComponent parseJComponent() {
//        String componentTitle = Parser.parseString();
//        skipWhitespace();
//        Parser.consume(':');
//        skipWhitespace();
//        Parser.consume('{');
//
//        switch (componentTitle) {
//            case "Button":
//                return Button.deserialize();
//            case "FileExplorerButton":
//                return FileExplorerButton.deserialize();
//            case "SaveLevelButton":
//                return SaveLevelButton.deserialize();
//            case "ZIndexButton":
//                return ZIndexButton.deserialize();
//            case "LineBreak":
//                return LineBreak.deserialize();
//            case "NewLevelButton":
//                return NewLevelButton.deserialize();
//            case "TestLevelButton":
//                return TestLevelButton.deserialize();
//            case "ButtonQuestionBlock":
//                return ButtonQuestionBlock.deserialize();
//            default:
//                assert false : "Could not find component '" + componentTitle + "' at line: " + Parser.line;
//        }
//
//        return null;
//    }
    public enum PropertyType {
        STRING, INTEGER, DOUBLE, FLOAT, BOOLEAN
    }

    public static Object consumeProperty(String name, PropertyType type) {
        skipWhitespace();
        checkString(name);
        consume(':');

        switch (type) {
            case STRING:
                return parseString();
            case INTEGER:
                return parseInt();
            case DOUBLE:
                return parseDouble();
            case FLOAT:
                return parseFloat();
            case BOOLEAN:
                return parseBoolean();
            default:
                System.err.println("Cannot parse this property " + type);
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
