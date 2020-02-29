package com.jiarwang.papaw;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author : wangjian
 * @Data : 2020-02-27 22:49
 * @Describe :
 */
public class PapawHelper {

    protected static final int NONE = 0X0000;
    protected static final int LEFT = 0X0001;
    protected static final int TOP = 0X0010;
    protected static final int RIGHT = 0X0100;
    protected static final int BOTTOM = 0X1000;

    private RectF[] rectPool = {new RectF(), new RectF(), new RectF(), new RectF()};
    private PointF[] mPointFS = {new PointF(), new PointF(), new PointF(), new PointF(),
            new PointF(), new PointF(), new PointF(), new PointF()};
    private float[] points = new float[12];

    private final Paint defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private View v;
    private Paint mPaint = defaultPaint;
    private final Path mPath = new Path();
    private float mHornHeight = 10f;

    {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha((int) (255 * 0.71));
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    public PapawHelper(View view) {
        if (view instanceof ViewGroup) {
            view.setWillNotDraw(false);
        }
        this.v = view;
    }

    private int mHornSide = PapawHelper.NONE;
    private float mRadius = 0;


    public PapawHelper setPaint(Paint paint) {
        mPaint = paint;
        return this;
    }

    public PapawHelper setAlpha(float alpha) {
        mPaint.setAlpha((int) (255 - alpha * 255));
        return this;
    }

    public int getAlpha() {
        return mPaint.getAlpha();
    }

    public PapawHelper setHornHeight(float hornHeight) {
        this.mHornHeight = hornHeight;
        return this;
    }

    public PapawHelper setRadius(float radius) {
        mRadius = radius;
        return this;
    }

    private static Path lineTo(final Path path, PointF pointF) {
        path.lineTo(pointF.x, pointF.y);
        return path;
    }

    @IntDef({TOP, RIGHT, BOTTOM, LEFT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Side {
    }

    protected void drawBackground(Canvas canvas) {
        float top = isHorn(PapawHelper.TOP) ? mHornHeight : 0;
        float bottom = v.getHeight() - (isHorn(PapawHelper.BOTTOM) ? mHornHeight : 0);
        float left = isHorn(PapawHelper.LEFT) ? mHornHeight : 0;
        float right = v.getWidth() - (isHorn(PapawHelper.RIGHT) ? mHornHeight : 0);
//
//        top += v.getPaddingTop();
//        left += v.getPaddingLeft();
//        right -= v.getPaddingRight();
//        bottom -= v.getPaddingBottom();


        RectF ltRectF = getRectFromPool(0, left, top, 2 * mRadius);
        RectF rtRectF = getRectFromPool(1, right - 2 * mRadius, top, 2 * mRadius);
        RectF rbRectF = getRectFromPool(2, right - 2 * mRadius, bottom - 2 * mRadius, 2 * mRadius);
        RectF lbRectF = getRectFromPool(3, left, bottom - 2 * mRadius, 2 * mRadius);

        PointF tl = getPointFromPool(0, left + mRadius, top);
        PointF tr = getPointFromPool(1, right - mRadius, top);
        PointF bl = getPointFromPool(2, left + mRadius, bottom);
        PointF br = getPointFromPool(3, right - mRadius, bottom);
        PointF rt = getPointFromPool(4, right, top + mRadius);
        PointF rb = getPointFromPool(5, right, bottom - mRadius);
        PointF lt = getPointFromPool(6, left, top + mRadius);
        PointF lb = getPointFromPool(7, left, bottom - mRadius);

        mPath.reset();
        //top
        mPath.addArc(ltRectF, 180, 90);
        if (isHorn(PapawHelper.TOP)) lineHorn(mPath, tl, tr, PapawHelper.TOP, mHornHeight);
        lineTo(mPath, tr);
        //right
        mPath.arcTo(rtRectF, 270, 90);
        if (isHorn(PapawHelper.RIGHT)) lineHorn(mPath, rt, rb, PapawHelper.RIGHT, mHornHeight);
        lineTo(mPath, rb);
        //bottom
        mPath.arcTo(rbRectF, 0, 90);
        if (isHorn(PapawHelper.BOTTOM)) lineHorn(mPath, br, bl, PapawHelper.BOTTOM, mHornHeight);
        lineTo(mPath, bl);
        //left
        mPath.arcTo(lbRectF, 90, 90);
        if (isHorn(PapawHelper.LEFT)) lineHorn(mPath, lb, lt, PapawHelper.LEFT, mHornHeight);
        lineTo(mPath, lt);
        mPath.close();

        if (defaultPaint == mPaint) {
            int[] colors = new int[]{0XFF52EFEC, 0XFFB2E9C6, 0XFFFFBD6A};
            float[] ps = new float[]{0.17f, 0.47f, 0.86f};
            LinearGradient gradient = new LinearGradient(left, 0, right, 0, colors, ps, Shader.TileMode.CLAMP);
            mPaint.setShader(gradient);
        }
        onDrawPath(canvas, mPaint, mPath);
    }

    protected void onDrawPath(Canvas canvas, Paint paint, Path path) {
        canvas.drawPath(path, paint);
    }


    /**
     * @param lx
     * @param ly
     * @param width
     * @return
     */
    private RectF getRectFromPool(int index, float lx, float ly, float width) {
        rectPool[index].set(lx, ly, lx + width, ly + width);
        return rectPool[index];
    }

    private PointF getPointFromPool(int index, float x, float y) {
        mPointFS[index].set(x, y);
        return mPointFS[index];
    }


    private final void lineHorn(final Path path, PointF start, PointF end, @Side int side, float d) {
        if (lineHorn(path, side, start, end)) return;
        switch (side) {
            case PapawHelper.TOP:
                lineHorn(path, PapawHelper.TOP, start, end, d, points[0], points[1], points[2]);
                break;
            case PapawHelper.RIGHT:
                lineHorn(path, PapawHelper.RIGHT, start, end, d, points[3], points[4], points[5]);
                break;
            case PapawHelper.BOTTOM:
                lineHorn(path, PapawHelper.BOTTOM, start, end, d, points[6], points[7], points[8]);
                break;
            case PapawHelper.LEFT:
                lineHorn(path, PapawHelper.LEFT, start, end, d, points[9], points[10], points[11]);
                break;
            default:
        }
    }

    /**
     * set horn info
     * @param hornSide
     * @param start
     * @param center
     * @param end
     * @return
     */
    public PapawHelper setHornPoint(@Side int hornSide, float start, float center, float end) {
        mHornSide = mHornSide | hornSide;
        if (hornSide == NONE) return this;
        if (isHorn(TOP)) {
            realHornPoint(TOP, start, center, end);
        }
        if (isHorn(RIGHT)) {
            realHornPoint(RIGHT, start, center, end);
        }
        if (isHorn(BOTTOM)) {
            realHornPoint(BOTTOM, start, center, end);
        }
        if (isHorn(LEFT)) {
            realHornPoint(LEFT, start, center, end);
        }
        return this;
    }

    /**
     * @param hornSide
     * @param start
     * @param center
     * @param end
     * @hiden
     */
    private final void realHornPoint(@Side int hornSide, float start, float center, float end) {
        if (hornSide == NONE) return;
        int startIndex = 0;
        switch (hornSide) {
            case TOP:
                startIndex = 0;
                break;
            case RIGHT:
                startIndex = 1;
                break;
            case BOTTOM:
                startIndex = 2;
                break;
            case LEFT:
                startIndex = 3;
                break;
            default:
        }
        points[startIndex * 3] = start;
        points[startIndex * 3 + 1] = center;
        points[startIndex * 3 + 2] = end;
    }

    public PapawHelper cleanPoints() {
        for (int i = 0; i < points.length; i++) {
            points[i] = 0;
        }
        return this;
    }


    private void lineHorn(final Path path, @Side int side, PointF startPoint, PointF endPoint, float d, float l1, float l2, float l3) {
        switch (side) {
            case PapawHelper.TOP:
                path.lineTo(startPoint.x + l1, startPoint.y);
                path.lineTo(startPoint.x + l2, startPoint.y - d);
                path.lineTo(startPoint.x + l3, startPoint.y);
                break;
            case PapawHelper.RIGHT:
                path.lineTo(startPoint.x, startPoint.y + l1);
                path.lineTo(startPoint.x + d, startPoint.y + l2);
                path.lineTo(startPoint.x, startPoint.y + l3);
                break;
            case PapawHelper.BOTTOM:
                path.lineTo(endPoint.x + l3, startPoint.y);
                path.lineTo(endPoint.x + l2, startPoint.y + d);
                path.lineTo(endPoint.x + l1, startPoint.y);
                break;
            case PapawHelper.LEFT:
                path.lineTo(startPoint.x, endPoint.y + l3);
                path.lineTo(startPoint.x - d, endPoint.y + l2);
                path.lineTo(startPoint.x, endPoint.y + l1);
                break;
            default:
        }
    }

    /**
     * line a side by you self
     * @param path
     * @param side
     * @param startPoint
     * @param endPoint
     * @return  return true if you need draw line in you own way
     */
    protected boolean lineHorn(final Path path, @Side int side, PointF startPoint, PointF endPoint) {
        return false;
    }


    public boolean isHorn(int hornSide) {
        return (mHornSide & hornSide) == hornSide;
    }

    /**
     * @param hornSide
     * @return
     */
    public int getHornPadding(int hornSide) {
        int hornPadding = 0;
        hornPadding += (isHorn(TOP) ? mHornHeight : 0);
        hornPadding += (isHorn(LEFT) ? mHornHeight : 0);
        hornPadding += (isHorn(RIGHT) ? mHornHeight : 0);
        hornPadding += (isHorn(BOTTOM) ? mHornHeight : 0);
        return hornPadding;
    }
}
