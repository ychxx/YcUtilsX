package com.yc.yclibx.file;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import com.yc.yclibx.R;

/**
 *
 */

public class YcImageLoadBuild {
    @DrawableRes
    private int imageError = R.drawable.img_loading;
    @DrawableRes
    private int imageLoading = R.drawable.img_loading;

    public interface Listeners {
        void call(String msg);
    }
    public interface Listeners2 {
        void call(Drawable drawable);
    }
    public interface Listeners3 {
        void call(Bitmap msg);
    }
}
