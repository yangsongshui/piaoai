package com.example.yangsong.piaoai.inter;

import android.view.View;

/**
 * Created by yangsong on 2017/3/18.
 */

public interface OnItemCheckListener {
    void onitemCheck(View view, boolean isCheck, int position);

    void onNumCheck(View view, int num, int position);
}
