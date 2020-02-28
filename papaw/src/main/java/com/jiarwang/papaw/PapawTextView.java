package com.jiarwang.papaw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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

    private PapawHelper mHelper;

    public PapawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PapawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PapawTextView);
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
        float hornHeight = typedArray.getDimensionPixelOffset(R.styleable.PapawTextView_horn_height, 0);
        float radius = typedArray.getDimension(R.styleable.PapawTextView_radius, 0);
        float anchorStart = typedArray.getDimension(R.styleable.PapawTextView_horn_start, 0);
        float anchorCenter = typedArray.getDimension(R.styleable.PapawTextView_horn_center, 0);
        float anchorEnd = typedArray.getDimension(R.styleable.PapawTextView_horn_end, 0);
        float papawAlpha = typedArray.getFloat(R.styleable.PapawTextView_horn_alpha, 0);

        mHelper = new PapawHelper(this)
                .setAlpha(papawAlpha)
                .setRadius(radius)
                .setHornHeight(hornHeight)
                .setHornPoint(horSide, anchorStart, anchorCenter, anchorEnd);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mHelper.drawBackground(canvas);
        super.onDraw(canvas);
    }
}
