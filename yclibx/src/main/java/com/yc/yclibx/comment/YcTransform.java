package com.yc.yclibx.comment;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import androidx.annotation.DrawableRes;
import com.yc.yclibx.file.YcFileUtils;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * 转换
 */

public class YcTransform {
    /**
     * 将图片转为String
     *
     * @param imgPath 图片路径（包含后缀）
     */
    public static String imgPathToString(String imgPath) {
        if (!YcFileUtils.checkFileExists(imgPath)) {
            YcLog.e("转换的图片文件不存在");
            return "";
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            return imgBitmapToString(bitmap);
        }
    }

    /**
     * 图片转为String
     *
     * @param bitmap
     * @return
     */
    public static String imgBitmapToString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            //得到图片的String
            return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            YcLog.e("Bitmap转String失败");
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 资源图片转成bitmap
     *
     * @param context  上下文
     * @param imgIdRes 图片资源id(drawable或mipmap)
     */
    public static Bitmap imgIdResToBitmap(Context context, @DrawableRes int imgIdRes) {
        Bitmap bitmap;
        try {
            Resources res = context.getResources();
            bitmap = BitmapFactory.decodeResource(res, imgIdRes);
            return bitmap;
        } catch (Exception e) {
            YcLog.e("string转换Bitmap失败");
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap imgPathToBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(imgPath));
        } catch (Exception e) {
            YcLog.e("path转换Bitmap失败");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * string转成bitmap
     */
    public static Bitmap imgStringToBitmap(String data) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(data, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            return bitmap;
        } catch (Exception e) {
            YcLog.e("string转换Bitmap失败");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     */
    public static String imgUriToAbsolutePath(Context context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            //是否是外部存储文档
            if ("com.android.externalstorage.documents".equals(imageUri.getAuthority())) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if ("com.android.providers.downloads.documents".equals(imageUri.getAuthority())) {//是否下载
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if ("com.google.android.apps.photos.content".equals(imageUri.getAuthority()))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
