package com.example.androidapplication.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {
    public static String encodeUriToBase64(Context context, Uri uri, int targetWidth, int targetHeight, int quality) {
        try {
            ContentResolver contentResolver = context.getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null) {
                return null; // Return null if unable to open input stream
            }

            // Decode and resize the image
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();

            int imageWidth = options.outWidth;
            int imageHeight = options.outHeight;
            int scaleFactor = Math.min(imageWidth / targetWidth, imageHeight / targetHeight);

            options = new BitmapFactory.Options();
            options.inSampleSize = scaleFactor;
            inputStream = contentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            inputStream.close();

            // Compress the resized image
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace(); // Handle file not found exception
        } catch (IOException e) {
            e.printStackTrace(); // Handle IO exception
        }
        return null; // Return null if any exception occurs
    }
    public static Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
