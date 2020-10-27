package com.yc.yclibx.widget.edt;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.yclibx.R;
import com.yc.yclibx.comment.YcEmpty;
import com.yc.yclibx.comment.YcResources;
import com.yc.yclibx.comment.YcUI;
import com.yc.yclibx.release.YcReleaseLayoutUtils;

/**
 * 带显示和限制输入字数的EditText
 */
public class YcEditTextNum extends FrameLayout {
    protected EditText mEditText;
    protected TextView mLimitTv;
    protected TextView mNumTv;
    protected TextChangeListener mTextChangeListener;
    protected String mBeforeData;//改变前的数据
    protected boolean mIsInputData = true;//能否输出数据
    @ColorInt
    private int mTextColor;
    @ColorInt
    private int mTextHintColor;

    public YcEditTextNum(Context context) {
        this(context, null);
    }

    public YcEditTextNum(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YcEditTextNum(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YcEditTextNum);
        int ycMinHeight = a.getInt(R.styleable.YcEditTextNum_ycMinHeight, YcUI.dpToPx(120));
        int maxLength = a.getInt(R.styleable.YcEditTextNum_ycMaxLength, 200);
        String hint = a.getString(R.styleable.YcEditTextNum_ycHint);
        mTextColor = a.getColor(R.styleable.YcEditTextNum_ycTextColor, YcResources.getColor(R.color.colorDefaultText2));
        mTextHintColor = a.getColor(R.styleable.YcEditTextNum_ycHintColor, YcResources.getColor(R.color.colorDefaultText1));
        a.recycle();

        setMinimumHeight(ycMinHeight);
        View view = YcReleaseLayoutUtils.createView(context, R.layout.yc_widget_edit_text);
        mLimitTv = view.findViewById(R.id.widgetEditTextLimitTv);
        mEditText = view.findViewById(R.id.widgetEditTextEdt);
        mNumTv = view.findViewById(R.id.widgetEditTextNumTv);

        mLimitTv.setText("/" + maxLength);
        mEditText.setHint(hint);
        if (mEditText.getText() != null) {
            mBeforeData = mEditText.getText().toString();
        }
        mEditText.setTextColor(mTextColor);
        mEditText.setHintTextColor(mTextHintColor);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onRefresh(s.toString());
            }
        });
        onRefresh(mEditText.getText().toString());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mTextChangeListener != null && (YcEmpty.isEmpty(mBeforeData) != YcEmpty.isEmpty(s.toString()))) {
                    mTextChangeListener.afterTextChanged(s.toString());
                }
                mBeforeData = s.toString();
            }
        });
        addView(view);
    }

    private void onRefresh(String text) {
        if (text.length() > 0) {
            mNumTv.setTextColor(YcResources.getColor(R.color.colorDefaultText2));
        } else {
            mNumTv.setTextColor(YcResources.getColor(R.color.colorDefaultText1));
        }
        mNumTv.setText(text.length() + "");
    }

    public String getEditTextContext() {
        return mEditText.getText().toString();
    }

    public void setEditTextContext(String context) {
        if (!mIsInputData && YcEmpty.isEmpty(context)) {
            mEditText.setText("暂无备注");
        } else {
            mEditText.setText(context);
        }
    }

    public void setChangeClick(TextChangeListener textChangeListener) {
        mTextChangeListener = textChangeListener;
    }

    public void setInputAble(boolean isInputAble) {
        mIsInputData = isInputAble;
        mEditText.setClickable(isInputAble);
        mEditText.setFocusable(isInputAble);
        mEditText.setEnabled(isInputAble);
        if (isInputAble) {
            mLimitTv.setVisibility(VISIBLE);
            mNumTv.setVisibility(VISIBLE);
        } else {
            if (YcEmpty.isEmpty(mEditText.getText().toString())) {
                mEditText.setText("暂无备注");
            }
            mLimitTv.setVisibility(GONE);
            mNumTv.setVisibility(GONE);
        }
    }

    public static interface TextChangeListener {
        void afterTextChanged(String s);
    }
}
