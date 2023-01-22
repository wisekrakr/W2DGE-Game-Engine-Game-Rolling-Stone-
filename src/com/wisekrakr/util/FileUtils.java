package com.wisekrakr.util;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    public static byte[] combine(byte[] a, byte[] b){
        int length = a.length + b.length;
        byte[] result = new byte[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static ByteBuffer fileToByteBuffer(String filename) {
        ByteBuffer fontBuffer = null;

        try {
            ZipFile zipFile = new ZipFile("assets/levels/" + filename + ".zip");
            ZipEntry jsonFile = zipFile.getEntry(filename + ".json");
            InputStream stream = zipFile.getInputStream(jsonFile);

            // Read input stream into a byte array
            byte[] finalBytes = new byte[0];
            while (stream.available() != 0) {
                byte[] byteBuffer = new byte[stream.available()];
                stream.read(byteBuffer);
                finalBytes = FileUtils.combine(finalBytes, byteBuffer);
            }
            fontBuffer = ByteBuffer.wrap(finalBytes);

            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return fontBuffer;
    }

    private static void openLevelFile(String filename) {
        File tmp = new File("assets/levels/" + filename + ".zip");
        if (!tmp.exists()) {
            return;
        }
        Parser.bytes = FileUtils.fileToByteBuffer(filename).array();
    }

    /**
     * Open any file
     * @param filename Name of file only
     * @param dir Directory path
     * @param ext Extension (.json or .zip, etc)
     */
    public static void openFile(String filename, String dir, String ext) {

        try {
            InputStream stream = new FileInputStream(dir + filename + ext);

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


    public static void exportLevelToFile(String fileName, List<GameObject>gameObjects){
        try {
            FileOutputStream fos = new FileOutputStream("assets/levels/" + fileName + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            zos.putNextEntry(new ZipEntry(fileName + ".json"));

            int i = 0;

            for (GameObject g: gameObjects){
                String str = g.serialize(0); // object turned into String

                if (str.compareTo("") != 0){ // if line is empty
                    zos.write(str.getBytes()); // write line
                    if (i != gameObjects.size() - 1){ // new line
                        zos.write(",\n".getBytes()); // write new line
                    }
                }
                i++;
            }
            zos.closeEntry();
            zos.close();
            fos.close();
        }catch (Throwable t){
            throw new IllegalArgumentException("Exporting file interrupted", t);
        }

        System.out.println("Successfully exported level file");
    }

    public static void importFileToLevel(String fileName, Scene scene) {
        openLevelFile(fileName);

        GameObject gameObject = Parser.parseGameObject();

        while (gameObject != null){
            scene.addGameObjectToScene(gameObject);

            gameObject = Parser.parseGameObject();
        }
    }
}
