package com.yc.yclibx.glide;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;


import java.io.File;

/**
 *
 */

public interface IYcImage {
    public void load(Context context, File file, ImageView imageView);
    public void load(Context context, @DrawableRes int imgResId, ImageView imageView);
    public void load(Context context,String imgPath, ImageView imageView);
    public void load(File file);

    public void load(@DrawableRes int imgResId);

    public void load(String imgPath);

}
