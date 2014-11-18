package com.pangu.mobile.client.background_tasks;

/**
 * @author Mark Goddard
 * @date 08/10/2014
 * @desc Decodes the .ppm byte array to a bitmap
 */
import android.graphics.Bitmap;

public class PanguImage {

    //Default constructor.
    public PanguImage() { }

    public static Bitmap Image(byte image_data[]) {
        // Return immediately if it isn't a PPM RAW image.
        if (image_data.length < 3) return null;
        if (image_data[0] != 'P' || image_data[1] != '6') return null;
        if (!ppmIsSpace(image_data[2])) return null;

        // Continue the parsing after the first white space.
        int cp = 3, ep = image_data.length;

        // Unfortunately Java doesn't have pointers or references ...
        int iptr[] = {0};

        // Read the width.
        cp = ppmReadInteger(image_data, cp, ep, iptr);
        if (cp >= ep) return null; // Invalid image
        int w = iptr[0];

        // Read the height.
        cp = ppmReadInteger(image_data, cp, ep, iptr);
        if (cp >= ep) return null; // Invalid image
        int h = iptr[0];

        // Read the depth.
        cp = ppmReadInteger(image_data, cp, ep, iptr);
        if (cp >= ep) return null; // Invalid image
        int dep = iptr[0];

        // Skip the single white space between header and data.
        if (!ppmIsSpace(image_data[cp])) return null;
        cp++;
        if (cp >= ep) return null; // Invalid image

        // For maxval < 256 we have one byte per sample, otherwise
        // we have two bytes. If we don't have enough bytes for this
        // then stop now. This saves us continuously bound-checking.
        int isize = 3 * w * h;
        if (dep >= 256) isize *= 2;
        if (ep - cp < isize) return null;

        // Hack up a fake image so that we know our code is still
        // working. Once the PPM has been completely decoded we can
        // replace this fake image with the real thing.
        int[] pixels = new int[w * h];

        // Initialise to blue so that we can see what we've missed.
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                pixels[y * w + x] = 0xff0000ff;

        // Read single or double-pixels.
        if (dep < 256)
            ppmOneByte(image_data, cp, w, h, dep, pixels);
        else
            ppmTwoByte(image_data, cp, w, h, dep, pixels);


        Bitmap bmp = Bitmap.createBitmap(pixels, w, h, Bitmap.Config.ARGB_8888);

        return bmp;
    }

    // Read PPM RAW with one byte per sample.
    private static void ppmOneByte(byte s[], int i, int w, int h, int m, int d[]) {
        // Samples are stored RGB.
        for (int p = 0; p < w * h; p++) {
            // Read the samples.
            int r = (int) s[i];
            i++;
            if (r < 0) r += 256;
            int g = (int) s[i];
            i++;
            if (g < 0) g += 256;
            int b = (int) s[i];
            i++;
            if (b < 0) b += 256;

            // Update the pixel array.
            d[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }
    }

    // Read PPM RAW with two bytes per sample.
    private static void ppmTwoByte(byte s[], int i, int w, int h, int m, int d[]) {
        // Samples are stored RGB.
        for (int p = 0; p < w * h; p++) {
            // Read the samples: skip the low bits.
            int r = (int) s[i];
            i += 2;
            if (r < 0) r += 256;
            int g = (int) s[i];
            i += 2;
            if (g < 0) g += 256;
            int b = (int) s[i];
            i += 2;
            if (b < 0) b += 256;

            // Update the pixel array.
            d[p] = 0xff000000 | (r << 16) | (g << 8) | b;
        }
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