package com.example.xcardview;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @Author : wangjian
 * @Data : 2020-03-01 23:41
 * @Describe :
 */
interface XCardViewDelegate {
    void setCardBackground(Drawable drawable);
    Drawable getCardBackground();
    boolean getUseCompatPadding();
    boolean getPreventCornerOverlap();
    void setShadowPadding(int left, int top, int right, int bottom);
    void setMinWidthHeightInternal(int width, int height);
    View getCardView();
}
