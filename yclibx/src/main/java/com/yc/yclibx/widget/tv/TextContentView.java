package com.yc.yclibx.widget.tv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yc.yclibx.comment.YcEmpty;

/**
 * 用于内容，防止android自动排版，提前换行
 */
@SuppressLint("AppCompatCustomView")
public class TextContentView extends TextView {
    private String mData;
    private int mViewWidth;

    public TextContentView(Context context) {
        this(context, null);
    }

    public TextContentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        postDelayed(() -> autoSplitText(getPaint(), mData, mViewWidth), 50);
    }

    public void setData(String data) {
        this.mData = data;
        postDelayed(() -> autoSplitText(getPaint(), mData, mViewWidth), 50);
    }


    public String getData() {
        return mData;
    }

    public void autoSplitText(Paint tvPaint, String data, float textViewWidth) {
        if (YcEmpty.isEmpty(data) || textViewWidth <= 0) {
            return;
        }
        //将原始文本按行拆分
        String[] rawTextLines = data.replaceAll("\r", "").split("\n");
        StringBuilder sbNewText = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= textViewWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                sbNewText.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    lineWidth += tvPaint.measureText(String.valueOf(ch));
                    if (lineWidth <= textViewWidth) {
                        sbNewText.append(ch);
                    } else {
                        sbNewText.append("\n");
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            sbNewText.append("\n");
        }
        //把结尾多余的\n去掉
        if (!data.endsWith("\n")) {
            sbNewText.deleteCharAt(sbNewText.length() - 1);
        }
        setText(sbNewText.toString());
    }
}
