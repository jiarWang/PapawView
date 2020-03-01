package com.jiarwang.papaw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @Author : wangjian
 * @Data : 2020-02-26 20:55
 * @Describe :
 * opacity: 0.96;
 * background-image: linear-gradient(90deg, #52EFEC 17%, #B2E9C6 47%, #FFBD6A 86%);
 * border-radius: 12px;
 * border-radius: 12px;
 */
public class PapawTextView extends AppCompatTextView {

    private PapawHelper mPapawHelper;

    public PapawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PapawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PapawTextView);
//        //check attributes you need, for example all paddings
//        int [] attributes = new int [] {android.R.attr.paddingLeft, android.R.attr.paddingTop, android.R.attr.paddingBottom, android.R.attr.paddingRight};
//        //then obtain typed array
//        TypedArray arr = context.obtainStyledAttributes(attrs, attributes);
//
//        //and get values you need by indexes from your array attributes defined above
//        int leftPadding = arr.getDimensionPixelOffset(0, -1);
//        int topPadding = arr.getDimensionPixelOffset(1, -1);
//        int bottomPadding = arr.getDimensionPixelOffset(2, -1);
//        int rightPadding = arr.getDimensionPixelOffset(3, -1);

        int horSide = typedArray.getInt(R.styleable.PapawTextView_horn_side, 0);
        int hornHeight = typedArray.getDimensionPixelOffset(R.styleable.PapawTextView_horn_height, 0);
        int radius = typedArray.getDimensionPixelOffset(R.styleable.PapawTextView_radius, 0);
        int anchorStart = typedArray.getDimensionPixelOffset(R.styleable.PapawTextView_horn_start, 0);
        int anchorCenter = typedArray.getDimensionPixelOffset(R.styleable.PapawTextView_horn_center, 0);
        int anchorEnd = typedArray.getDimensionPixelOffset(R.styleable.PapawTextView_horn_end, 0);
        float papawAlpha = typedArray.getFloat(R.styleable.PapawTextView_horn_alpha, 0);
        final int color = typedArray.getColor(R.styleable.PapawTextView_color, Color.BLACK);

        final Paint paint = PapawHelper.newSimplePaint(Paint.Style.FILL);
        mPapawHelper = new PapawHelper(this) {
            @Override
            protected void onDrawPath(Canvas canvas, Path path, float top, float bottom, float left, float right) {
                int[] colors = new int[]{0XFF52EFEC, 0XFFB2E9C6, 0XFFFFBD6A};
                float[] ps = new float[]{0.17f, 0.47f, 0.86f};
                LinearGradient gradient = new LinearGradient(left, 0, right, 0, colors, ps, Shader.TileMode.CLAMP);
                paint.setShader(gradient);
                if (typedArray.hasValue(R.styleable.PapawLayout_color)){
                    paint.setColor(color);
                }
                super.onDrawPath(canvas, path, top, bottom, left, right);
            }
        }
                .setPaint(paint)
                .setAlpha(papawAlpha)
                .setRadius(radius)
                .setHornHeight(hornHeight)
                .setHornPoint(horSide, anchorStart, anchorCenter, anchorEnd);
//        setPadding(leftPadding + (mPapawHelper.isHorn(PapawHelper.LEFT) ? hornHeight : 0),
//                topPadding + (mPapawHelper.isHorn(PapawHelper.TOP) ? hornHeight : 0),
//                rightPadding + (mPapawHelper.isHorn(PapawHelper.RIGHT) ? hornHeight : 0),
//                bottomPadding + (mPapawHelper.isHorn(PapawHelper.BOTTOM) ? hornHeight : 0)
//        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPapawHelper.drawBackground(canvas);
//        canvas.translate(mHelper.getHornPadding(PapawHelper.RIGHT) - mHelper.getHornPadding(PapawHelper.LEFT),
//                mHelper.getHornPadding(PapawHelper.BOTTOM) - mHelper.getHornPadding(PapawHelper.TOP));

        super.onDraw(canvas);
    }
}
