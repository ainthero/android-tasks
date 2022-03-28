package com.example.glitcha;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Glitcher {

    static double amount, seed, iterations;
    static byte[] imageByte;
    static int jpgHeaderLength;

    public static Bitmap glitch(Bitmap bitmap, int amount1, int seed1, int iterations1, int quality1) {
        amount = amount1 / 100.0;
        seed = seed1 / 100.0;
        iterations = iterations1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality1, bos);
        imageByte = bos.toByteArray();
        jpgHeaderLength = getJpegHeaderSize();
        for (int i = 0; i < iterations; i++) {
            glitchJpegBytes(i);
        }
        return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

    }

    private static void glitchJpegBytes(int i) {
        int maxIndex = imageByte.length - jpgHeaderLength - 4;
        double pxMin = (maxIndex / iterations * i);
        double pxMax = (maxIndex / iterations * (i + 1));
        double delta = pxMax - pxMin;
        double pxIndex = (pxMin + delta * seed);
        if (pxIndex > maxIndex) {
            pxIndex = maxIndex;
        }
        int index = (int) Math.floor(jpgHeaderLength + pxIndex);
        imageByte[index] = (byte) Math.floor(amount * 256);
    }

    private static int getJpegHeaderSize() {
        int result = 417;
        for (int i = 0; i < imageByte.length - 1; i++) {
            if ((imageByte[i]&0xFF) == 255 && (imageByte[i + 1]&0xFF) == 218) {
                result = i + 2;
                break;
            }
        }
        return result;
    }
}
