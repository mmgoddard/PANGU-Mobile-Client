package com.pangu.mobile.client.background_tasks;

/**
 * @author Mark Goddard
 * @date 08/10/2014
 * @desc Decodes the .ppm byte array to a bitmap
 */
import android.graphics.Bitmap;

public class PanguImage {

    public static Bitmap Image(byte img_data[]) {
        //Check if it is a .ppm image or not
        if (img_data.length < 3) return null;
        if (img_data[0] != 'P' || img_data[1] != '6') return null;
        if (!ppmIsSpace(img_data[2])) return null;

        // Continue the parsing after the first white space.
        int cp = 3, ep = img_data.length;
        int ptr[] = {0};

        // Read the width.
        cp = ppmReadInteger(img_data, cp, ep, ptr);
        if (cp >= ep) return null; // Invalid image
        int img_width = ptr[0];

        // Read the height.
        cp = ppmReadInteger(img_data, cp, ep, ptr);
        if (cp >= ep) return null; // Invalid image
        int img_height = ptr[0];

        // Read the depth.
        cp = ppmReadInteger(img_data, cp, ep, ptr);
        if (cp >= ep) return null; // Invalid image
        int img_depth = ptr[0];

        // Skip the single white space between header and data.
        if (!ppmIsSpace(img_data[cp])) return null;
        cp++;
        if (cp >= ep) return null; // Invalid image

        // For maxval < 256 we have one byte per sample, otherwise
        // we have two bytes. If we don't have enough bytes for this
        // then stop now. This saves us continuously bound-checking.
        int isize = 3 * img_width * img_height;
        if (img_depth >= 256) isize *= 2;
        if (ep - cp < isize) return null;

        //Set up pixel array.
        int[] pixels = new int[img_width * img_height];

        //Read single or double-pixels.
        if (img_depth < 256)
            pixels = ppmByte(img_data, cp, img_width, img_height, pixels, 1);
        else
            pixels = ppmByte(img_data, cp, img_width, img_height, pixels, 2);


        Bitmap bmp = Bitmap.createBitmap(pixels, img_width, img_height, Bitmap.Config.ARGB_8888);
        return bmp;
    }

    //Read PPM RAW with @param bps per sample.
    private static int[] ppmByte(byte s[], int i, int w, int h, int d[], int bps) {
        //Samples are stored RGB.
        for (int p = 0; p < w * h; p++) {
            //Read the samples.
            int r = (int) s[i];
            i+=bps;
            if (r < 0) r += 256;
            int g = (int) s[i];
            i+=bps;
            if (g < 0) g += 256;
            int b = (int) s[i];
            i+=bps;
            if (b < 0) b += 256;

            // Update the pixel array.
            d[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }
        return d;
    }

    private static boolean ppmIsSpace(byte b) {
        if (b == 9 || b == '\n' || b == '\r' || b == ' ') return true;
        return false;
    }

    private static boolean ppmIsDigit(byte b) {
        if (b < '0' || b > '9') return false;
        return true;
    }

    private static int ppmEatWhitespace(byte[] v, int cp, int ep) {
        while (true) {
            // Consume whitespace.
            while (cp < ep && ppmIsSpace(v[cp])) cp++;
            if (cp >= ep || v[cp] != '#') return cp;

            // Eat the comment.
            while (cp < ep && v[cp] != '\n') cp++;
            if (cp >= ep || !ppmIsSpace(v[cp])) return cp;
        }
    }

    // Read and return a decimal integer with leading whitespace.
    private static int ppmReadInteger(byte v[], int cp, int ep, int rptr[]) {
        // Skip leading whitespace.
        cp = ppmEatWhitespace(v, cp, ep);
        if (cp >= ep || !ppmIsDigit(v[cp])) return ep;

        // Read the integer.
        rptr[0] = 0;
        while (cp < ep && ppmIsDigit(v[cp])) {
            rptr[0] = rptr[0] * 10 + (int) (v[cp] - '0');
            cp++;
        }
        return cp;
    }
}