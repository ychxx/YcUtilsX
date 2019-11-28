package com.yc.yclibx.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.core.text.util.LinkifyCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcResources;
import com.yc.yclibx.file.YcImgUtils;

/**
 *
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public abstract class YcAdapterHelperOperation {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public YcAdapterHelperOperation setBackground(@IdRes int viewId, @NonNull Drawable drawable) {
        getView(viewId).setBackground(drawable);
        return this;
    }

    public YcAdapterHelperOperation setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        getView(viewId).setBackgroundColor(color);
        return this;
    }
    public YcAdapterHelperOperation setBackgroundResColor(@IdRes int viewId, @ColorRes int color) {
        getView(viewId).setBackgroundColor(YcResources.getColor(color));
        return this;
    }

    public YcAdapterHelperOperation setBackgroundResDrawable(@IdRes int viewId, @DrawableRes int backgroundRes) {
        getView(viewId).setBackgroundResource(backgroundRes);
        return this;
    }

    @SuppressLint("SetTextI18n")
    public YcAdapterHelperOperation setText(@IdRes int viewId, CharSequence value) {
        if(TextUtils.isEmpty(value)){
            ((TextView) getView(viewId)).setText("");
        }else {
            ((TextView) getView(viewId)).setText(value);
        }
        return this;
    }

    public YcAdapterHelperOperation setTextRes(@IdRes int viewId, @StringRes int resId) {
        ((TextView) getView(viewId)).setText(resId);
        return this;
    }

    public YcAdapterHelperOperation setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        ((TextView) getView(viewId)).setTextColor(textColor);
        return this;
    }

    public YcAdapterHelperOperation setTextColorRes(@IdRes int viewId, @ColorRes int textColorRes) {
        ((TextView) getView(viewId)).setTextColor(YcResources.getColor(textColorRes));
        return this;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public YcAdapterHelperOperation setTextColorRes(@IdRes int viewId, @ColorRes int textColorRes, @Nullable Resources.Theme theme) {
        ((TextView) getView(viewId)).setTextColor(YcResources.getResources().getColor(textColorRes, theme));
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public YcAdapterHelperOperation setImageIcon(@IdRes int viewId, @NonNull Icon icon) {
        ((ImageView) getView(viewId)).setImageIcon(icon);
        return this;
    }

    public YcAdapterHelperOperation setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ((ImageView) getView(viewId)).setImageResource(imageResId);
        return this;
    }

    public YcAdapterHelperOperation setImageDrawable(@IdRes int viewId, @NonNull Drawable drawable) {
        ((ImageView) getView(viewId)).setImageDrawable(drawable);
        return this;
    }

    public YcAdapterHelperOperation setImageBitmap(@IdRes int viewId, @NonNull Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }
    public YcAdapterHelperOperation setImageNet(Context context,@IdRes int viewId, @NonNull String imgUrl) {
        ImageView imageView = getView(viewId);
        YcImgUtils.loadNetImg(context,imgUrl,imageView);
        return this;
    }
    public YcAdapterHelperOperation setImageLocal(Context context,@IdRes int viewId, @NonNull String imgPath) {
        ImageView imageView = getView(viewId);
        YcImgUtils.loadLocalImg(context,imgPath,imageView);
        return this;
    }
    public YcAdapterHelperOperation setAlpha(@IdRes int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        getView(viewId).setAlpha(value);
        return this;
    }

    public YcAdapterHelperOperation setVisible(@IdRes int viewId, boolean visible) {
        getView(viewId).setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public YcAdapterHelperOperation setVisible(@IdRes int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public YcAdapterHelperOperation setEnabled(@IdRes int viewId, boolean enabled) {
        getView(viewId).setEnabled(enabled);
        return this;
    }

    public YcAdapterHelperOperation setFocusable(@IdRes int viewId, boolean focusable) {
        getView(viewId).setFocusable(focusable);
        return this;
    }

    public YcAdapterHelperOperation setFocusableInTouchMode(@IdRes int viewId, boolean focusableInTouchMode) {
        getView(viewId).setFocusableInTouchMode(focusableInTouchMode);
        return this;
    }

    public YcAdapterHelperOperation addAllLinks(@IdRes int viewId) {
        addLinks(viewId, Linkify.ALL);
        return this;
    }

    public YcAdapterHelperOperation addLinks(@IdRes int viewId, @LinkifyCompat.LinkifyMask int mask) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, mask);
        return this;
    }

    public YcAdapterHelperOperation setTypeface(@IdRes int viewId, @NonNull Typeface typeface) {
        TextView view = getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public YcAdapterHelperOperation setTypeface(@NonNull Typeface typeface, @IdRes int... viewIds) {
        for (int viewId : viewIds) {
            setTypeface(viewId, typeface);
        }
        return this;
    }

    public YcAdapterHelperOperation setTypeface(@IdRes int viewId, Typeface typeface, int style) {
        TextView view = getView(viewId);
        view.setTypeface(typeface, style);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public YcAdapterHelperOperation setProgress(@IdRes int viewId, int progress) {
        ((ProgressBar) getView(viewId)).setProgress(progress);
        return this;
    }

    public YcAdapterHelperOperation setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public YcAdapterHelperOperation setMax(@IdRes int viewId, int max) {
        ((ProgressBar) getView(viewId)).setMax(max);
        return this;
    }

    public YcAdapterHelperOperation setRating(@IdRes int viewId, float rating) {
        ((RatingBar) getView(viewId)).setRating(rating);
        return this;
    }

    public YcAdapterHelperOperation setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public YcAdapterHelperOperation setTag(@IdRes int viewId, @NonNull Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    public YcAdapterHelperOperation setTag(@IdRes int viewId, int key, @NonNull Object tag) {
        getView(viewId).setTag(key, tag);
        return this;
    }

    public YcAdapterHelperOperation setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        } else if (view instanceof CheckedTextView) {
            ((CheckedTextView) view).setChecked(checked);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public YcAdapterHelperOperation setAdapter(@IdRes int viewId, @NonNull Adapter adapter) {
        ((AdapterView) getView(viewId)).setAdapter(adapter);
        return this;
    }

    public YcAdapterHelperOperation setAdapter(@IdRes int viewId, @NonNull RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        ((RecyclerView) getView(viewId)).setAdapter(adapter);
        return this;
    }

    public YcAdapterHelperOperation setOnClickListener(@IdRes int viewId, @NonNull View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public YcAdapterHelperOperation setOnTouchListener(@IdRes int viewId, @NonNull View.OnTouchListener listener) {
        getView(viewId).setOnTouchListener(listener);
        return this;
    }

    public YcAdapterHelperOperation setOnLongClickListener(@IdRes int viewId, @NonNull View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }

    public YcAdapterHelperOperation setOnItemClickListener(@IdRes int viewId,
                                                           @NonNull AdapterView.OnItemClickListener listener) {
        ((AdapterView) getView(viewId)).setOnItemClickListener(listener);
        return this;
    }

    public YcAdapterHelperOperation setOnItemLongClickListener(@IdRes int viewId,
                                                               @NonNull AdapterView.OnItemLongClickListener listener) {
        ((AdapterView) getView(viewId)).setOnItemLongClickListener(listener);
        return this;
    }

    public YcAdapterHelperOperation setOnItemSelectedClickListener(@IdRes int viewId, @NonNull AdapterView.OnItemSelectedListener listener) {
        ((AdapterView) getView(viewId)).setOnItemSelectedListener(listener);
        return this;
    }

    public abstract <T extends View> T getView(@IdRes int viewId);
}
