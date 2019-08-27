package com.yc.yclibx.html;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

/**
 * Html相关
 */

public class YcHtml {
    /**
     * 用TextView显示html
     *
     * @param textView 控件
     * @param context  上下文
     * @param html     html数据
     */
    public static void showTextView(TextView textView, Context context, String html) {
        textView.setText(Html.fromHtml(html, new YcHtmlImageGetter(textView, context), null));
    }
}
