package com.yc.yclibx.widget.edt;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.yc.yclibx.comment.YcEmpty;

/**
 *
 */
public class EditTextChangeView extends androidx.appcompat.widget.AppCompatEditText {
    private String mBeforeData;
    private TextChanged mTextChanged;

    public EditTextChangeView(Context context) {
        this(context, null);
    }

    public EditTextChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (getText() != null) {
            mBeforeData = getText().toString();
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mTextChanged != null && (YcEmpty.isEmpty(mBeforeData) != YcEmpty.isEmpty(s.toString()))) {
                    mTextChanged.call(s.toString());
                }
                mBeforeData = s.toString();
            }
        });
    }

    public void setInputAble(boolean isAble) {
        setClickable(isAble);
        setFocusable(isAble);
        setEnabled(isAble);
        setFocusableInTouchMode(isAble);
    }

    public void setTextChanged(TextChanged textChanged) {
        this.mTextChanged = textChanged;
    }

    public static interface TextChanged {
        void call(String data);
    }
}
