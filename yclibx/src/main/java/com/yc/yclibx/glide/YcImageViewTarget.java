package com.yc.yclibx.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.ImageViewTarget;

/**
 *
 */

public class YcImageViewTarget extends ImageViewTarget<Bitmap> {
    private LoadSuccessListen mImageTargetSuccessListen;
    private LoadFailListen mImageTargetFailListen;
    public YcImageViewTarget(ImageView view) {
        super(view);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        super.onLoadFailed(errorDrawable);
        if(mImageTargetFailListen!=null){
            mImageTargetFailListen.fail();
        }
    }
    @Override
    protected void setResource(@Nullable Bitmap resource) {
        if (mImageTargetSuccessListen != null) {
            mImageTargetSuccessListen.setResource(view, resource);
        } else {
            view.setImageBitmap(resource);
        }
    }

    public void setImageTargetListen(LoadSuccessListen imageTargetListen) {
        this.mImageTargetSuccessListen = imageTargetListen;
    }

    public interface LoadSuccessListen {
        void setResource(ImageView imageView, Bitmap resource);
    }

    public interface LoadFailListen {
        void fail();
    }
}
