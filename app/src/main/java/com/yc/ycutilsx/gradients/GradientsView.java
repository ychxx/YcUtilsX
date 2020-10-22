package com.yc.ycutilsx.gradients;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.yc.ycutilsx.R;

/**
 *
 */
public class GradientsView extends View {
    public GradientsView(Context context) {
        super(context);
    }

    public GradientsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取View的宽高
        int width = getWidth();
        int height = getHeight();

        int colorStart = getResources().getColor(R.color.colorPrimary);
        int color1 = Color.GRAY;
        int colorEnd = getResources().getColor(R.color.colorAccent);

        Paint paint = new Paint();
        int[] colors = new int[]{Color.parseColor("#fffbb04e"), Color.parseColor("#fff46767"), Color.parseColor("#ffff6d00")};
//        float[] positions = new float[]{0.3f, 0.3f, 0.4f};//比例
//        paint.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(), colors, positions, Shader.TileMode.CLAMP));
//        mRenderPaint.setColor(dataSet.getColor());

        LinearGradient backGradient = new LinearGradient(0, 0, 0, height, colors, null, Shader.TileMode.CLAMP);
        paint.setShader(backGradient);
        canvas.drawRect(0, 0, width, height, paint);
    }
}
