package com.yc.yclibx.widget.tv;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcResources;

/**
 *
 */
public class YcImageTextView extends RelativeLayout {
    public YcImageTextView(Context context) {
        this(context, null);
    }

    public YcImageTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YcImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);


        ImageView imageView = new ImageView(context);
        int imageViewHeight = (int) a.getDimension(R.styleable.ImageTextView_ImageTextView_imageDefaultHeight, 20);
        int imageViewWidth = (int) a.getDimension(R.styleable.ImageTextView_ImageTextView_imageDefaultWidth, 20);
        int imageViewResource = a.getResourceId(R.styleable.ImageTextView_ImageTextView_imageDefaultSrc, R.drawable.circle_bg_white);
        ViewGroup.LayoutParams paramsIv = new ViewGroup.LayoutParams(imageViewWidth, imageViewHeight);
        imageView.setLayoutParams(paramsIv);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(imageViewResource);
        linearLayout.addView(imageView);

        TextView textView = new TextView(context);
        String defaultText = a.getString(R.styleable.ImageTextView_ImageTextView_textDefault);
        int defaultTextColor = a.getColor(R.styleable.ImageTextView_ImageTextView_textDefaultColor, YcResources.getColor(R.color.colorDefaultText1));
        float defaultTextSize = a.getDimension(R.styleable.ImageTextView_ImageTextView_textDefaultSize, 18);
        int marginTopBottom =  a.getDimensionPixelSize(R.styleable.ImageTextView_ImageTextView_marginTopBottom, 8);

        textView.setText(defaultText);
        textView.setTextColor(defaultTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultTextSize);
        textView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams paramsTv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTv.setMargins(0, marginTopBottom, 0, 0);
        textView.setLayoutParams(paramsTv);
        linearLayout.addView(textView);

        LayoutParams paramsLl = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        paramsLl.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(linearLayout, paramsLl);
        a.recycle();
    }
}
