package com.yc.yclibx.widget.popup;

import android.content.Context;
import android.view.View;
import android.widget.ListAdapter;

import androidx.appcompat.widget.ListPopupWindow;

import com.yc.yclibx.YcManage;
import com.yc.yclibx.comment.YcOnDestroy;

/**
 *
 */
public class YcBaseListPopupWindow implements YcOnDestroy {
    private ListPopupWindow mListPopupWindow;
    private ListAdapter mPopupWindowAdapter;
    private Context mContext;
    private boolean mIsChangeAdapter = true;
//    public YcBaseListPopupWindow(Context context) {
//        mContext = context;
//    }

    public YcBaseListPopupWindow(Context context, YcManage ycManage) {
        mContext = context;
        ycManage.add(this);
    }

    private void create() {
        if (mListPopupWindow == null) {
            mListPopupWindow = new ListPopupWindow(mContext);
            mListPopupWindow.setWidth(ListPopupWindow.WRAP_CONTENT);
            mListPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
            mListPopupWindow.setModal(true);//点击非PopupWindow区域是否消失,true 消失，false 不消失
        }
        if (mIsChangeAdapter && mPopupWindowAdapter != null) {
            mListPopupWindow.setAdapter(mPopupWindowAdapter);
            mIsChangeAdapter = false;
        }
    }

    public void show(View anchorView) {
        create();
        mListPopupWindow.setAnchorView(anchorView);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListPopupWindow.show();
    }

    public void setAdapter(ListAdapter listAdapter) {
        mIsChangeAdapter = true;
        mPopupWindowAdapter = listAdapter;
    }

    @Override
    public void onDestroy() {
        if (mListPopupWindow != null) {
            mListPopupWindow.dismiss();
        }
    }
}
