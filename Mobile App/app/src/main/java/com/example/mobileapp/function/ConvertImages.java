package com.example.mobileapp.function;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ConvertImages {
    public static String ImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArr);
        byte[] Bytes = byteArr.toByteArray();
        return Base64.encodeToString(Bytes,Base64.DEFAULT);
    }

    public static Bitmap StringToImage(String userImg) {
        Bitmap bitmap = null;
        byte[] image = Base64.decode(userImg,Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        return  bitmap;
    }
}
