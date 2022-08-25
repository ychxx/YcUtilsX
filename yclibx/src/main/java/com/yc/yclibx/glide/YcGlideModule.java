package com.yc.yclibx.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Glide的配置
 * 注：必须有@GlideModule注解
 */
@GlideModule
public class YcGlideModule extends AppGlideModule {
    public static int TIMEOUT = -1;

    //禁止解析Manifest文件,可以提升初始化速度，避免一些潜在错误
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDefaultRequestOptions(new RequestOptions().timeout(TIMEOUT));
//        builder.setDefaultRequestOptions(YcImageLoad.getDefaultRequestOptions());
    }
}
