package com.lengwe.iVolunteer;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConveter {

    // Convert bitmap image to a byte array
    public static byte[] getByteFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes_image = stream.toByteArray();
        return bytes_image;
    }

    public static Bitmap getBitmapFromByte(byte[] bytes) {
        Bitmap bitmap_image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap_image;
    }

    public static Uri getImageUriFromBitmap(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap getBitmapFromUri(Uri uri, ContentResolver contentResolver) {
        Bitmap bitmap_image = Bitmap.createBitmap(5, 5, Bitmap.Config.RGB_565);
        try {
            bitmap_image = MediaStore.Images.Media.getBitmap(contentResolver, uri);
            return bitmap_image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap_image;
    }
}
