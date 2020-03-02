package com.example.xcardview;

/**
 * @Author : wangjian
 * @Data : 2020-03-01 23:40
 * @Describe :
 */
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.RequiresApi;

@RequiresApi(17)
class XCardViewApi17Impl extends XCardViewBaseImpl {

    @Override
    public void initStatic() {
        XRoundRectDrawableWithShadow.sRoundRectHelper =
                new XRoundRectDrawableWithShadow.RoundRectHelper() {
                    @Override
                    public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                              Paint paint) {
                        canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
                    }
                };
    }
}
