package com.jiarwang.papaw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @Author : wangjian
 * @Data : 2020-02-23 23:21
 * @Describe :
 */
public class PapawLayout extends CoordinatorLayout {

    private PapawHelper mPapawHelper;
    public PapawLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PapawLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PapawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        //        //check attributes you need, for example all paddings
//        int [] attributes = new int [] {android.R.attr.paddingLeft, android.R.attr.paddingTop, android.R.attr.paddingBottom, android.R.attr.paddingRight};
////        //then obtain typed array
//        TypedArray arr = context.obtainStyledAttributes(attrs, attributes);
////
////        //and get values you need by indexes from your array attributes defined above
//        int leftPadding = arr.getDimensionPixelOffset(0, -1);
//        int topPadding = arr.getDimensionPixelOffset(1, -1);
//        int bottomPadding = arr.getDimensionPixelOffset(2, -1);
//        int rightPadding = arr.getDimensionPixelOffset(3, -1);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PapawLayout);
        int horSide = typedArray.getInt(R.styleable.PapawLayout_horn_side, 0);
        int hornHeight = typedArray.getDimensionPixelOffset(R.styleable.PapawLayout_horn_height, 0);
        int radius = typedArray.getDimensionPixelOffset(R.styleable.PapawLayout_radius, 0);
        int anchorStart = typedArray.getDimensionPixelOffset(R.styleable.PapawLayout_horn_start, 0);
        int anchorCenter = typedArray.getDimensionPixelOffset(R.styleable.PapawLayout_horn_center, 0);
        int anchorEnd = typedArray.getDimensionPixelOffset(R.styleable.PapawLayout_horn_end, 0);
        float papawAlpha = typedArray.getFloat(R.styleable.PapawLayout_horn_alpha, 0);
        final int color = typedArray.getColor(R.styleable.PapawLayout_color, Color.BLACK);
        mPapawHelper = new PapawHelper(this){
            @Override
            public void onDrawPath(Canvas canvas, Paint paint, Path path) {
                if (typedArray.hasValue(R.styleable.PapawLayout_color)){
                    paint.setShader(null);
                    paint.setColor(color);
                }
                super.onDrawPath(canvas, paint, path);
            }
        }
                .setAlpha(papawAlpha)
                .setRadius(radius)
                .setHornHeight(hornHeight)
                .setHornPoint(horSide, anchorStart, anchorCenter, anchorEnd);
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPapawHelper.drawBackground(canvas);
        super.onDraw(canvas);
    }
}
