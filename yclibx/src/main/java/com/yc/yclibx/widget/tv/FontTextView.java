package com.yc.yclibx.widget.tv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcEmpty;

/**
 * 在xml里配置字体
 */
public class FontTextView extends AppCompatTextView {

    public FontTextView(Context context) {
        this(context, null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        String fontName = a.getString(R.styleable.FontTextView_fontTextViewFont);
        if(YcEmpty.isEmpty(fontName)){
            fontName = "dinmittelschriftstd.otf";
        }
        Typeface typeface = getTypeface(fontName, context);
//        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.dinmittelschriftstd);
        setTypeface(typeface);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public Typeface getTypeface(String name, Context context) {
        return Typeface.createFromAsset(context.getAssets(), String.format("fonts/%s", name));
    }
}
