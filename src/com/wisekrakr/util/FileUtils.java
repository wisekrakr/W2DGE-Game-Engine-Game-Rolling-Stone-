package com.wisekrakr.util;

import com.wisekrakr.w2dge.data.Parser;
import com.wisekrakr.w2dge.game.GameObject;
import com.wisekrakr.w2dge.visual.scene.Scene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {
    public static byte[] combine(byte[] a, byte[] b){
        int length = a.length + b.length;
        byte[] result = new byte[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static ByteBuffer fileToByteBuffer(String filepath) {
        File file = new File(filepath);
        ByteBuffer fontBuffer = null;

        try {
            InputStream is = new FileInputStream(file);

            // Read input stream into a byte array
            byte[] finalBytes = new byte[0];
            while (is.available() != 0) {
                byte[] byteBuffer = new byte[is.available()];
                is.read(byteBuffer);
                finalBytes = FileUtils.combine(finalBytes, byteBuffer);
            }
            fontBuffer = ByteBuffer.wrap(finalBytes);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
//            System.exit(-1);
        }

        return fontBuffer;
    }

    public static void exportLevel(String fileName, List<GameObject>gameObjects){
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
    }

    public static void importLevel(String fileName, Scene scene) {
        Parser.openLevelFile(fileName);

        GameObject gameObject = Parser.parseGameObject();

        while (gameObject != null){
            scene.addGameObjectToScene(gameObject);

            gameObject = Parser.parseGameObject();
        }
    }
}
