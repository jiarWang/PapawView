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

    private View v;
    private Paint mPaint = newSimplePaint(Paint.Style.FILL);
    private Paint mBorderPaint;
    private final Path mPath = new Path();
    private float mHornHeight = 10f;

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

    public PapawHelper setBorderPaint(Paint borderPaint) {
        mBorderPaint = borderPaint;
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
        float bordWidth = mBorderPaint == null ? 0 : mBorderPaint.getStrokeWidth();
        drawPath(canvas, mPath, bordWidth);
        drawBorder(canvas, mPath, bordWidth);
    }

    private void drawPath(Canvas canvas, Path path, float paintWidth) {
        if (mPaint == null) return;
        float top = isHorn(PapawHelper.TOP) ? mHornHeight : 0;
        float bottom = v.getHeight() - (isHorn(PapawHelper.BOTTOM) ? mHornHeight : 0);
        float left = isHorn(PapawHelper.LEFT) ? mHornHeight : 0;
        float right = v.getWidth() - (isHorn(PapawHelper.RIGHT) ? mHornHeight : 0);
        top += paintWidth + 0.5;
        left += paintWidth + 0.5;
        right -= paintWidth + 0.5;
        bottom -= paintWidth + 0.5;
        makePath(mPath, top, bottom, left, right);
        onDrawPath(canvas, path, top, bottom, left, right);
    }

    protected void makePath(Path path, float top, float bottom, float left, float right) {
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

        path.reset();
        //top
        path.addArc(ltRectF, 180, 90);
        if (isHorn(PapawHelper.TOP)) lineHorn(path, tl, tr, PapawHelper.TOP, mHornHeight);
        lineTo(path, tr);
        //right
        path.arcTo(rtRectF, 270, 90);
        if (isHorn(PapawHelper.RIGHT)) lineHorn(path, rt, rb, PapawHelper.RIGHT, mHornHeight);
        lineTo(path, rb);
        //bottom
        path.arcTo(rbRectF, 0, 90);
        if (isHorn(PapawHelper.BOTTOM)) lineHorn(path, br, bl, PapawHelper.BOTTOM, mHornHeight);
        lineTo(path, bl);
        //left
        path.arcTo(lbRectF, 90, 90);
        if (isHorn(PapawHelper.LEFT)) lineHorn(path, lb, lt, PapawHelper.LEFT, mHornHeight);
        lineTo(path, lt);
        path.close();
    }

    private void drawBorder(Canvas canvas, Path path, float paintWidth) {
        if (mBorderPaint == null) return;
        float top = isHorn(PapawHelper.TOP) ? mHornHeight : 0;
        float bottom = v.getHeight() - (isHorn(PapawHelper.BOTTOM) ? mHornHeight : 0);
        float left = isHorn(PapawHelper.LEFT) ? mHornHeight : 0;
        float right = v.getWidth() - (isHorn(PapawHelper.RIGHT) ? mHornHeight : 0);
//        float shadowRadius = getBorderPaintShadowRadius();
//        top += (mBorderPaint.getStrokeWidth() / 2 + shadowRadius);
//        left += (mBorderPaint.getStrokeWidth() / 2 + shadowRadius);
//        right -= (mBorderPaint.getStrokeWidth() / 2 + shadowRadius);
//        bottom -= (mBorderPaint.getStrokeWidth() / 2 + shadowRadius);

        top += (paintWidth / 2) + 0.5;
        left += (paintWidth / 2) + 0.5;
        right -= (paintWidth / 2) + 0.5;
        bottom -= (paintWidth / 2) + 0.5;
        makePath(mPath, top, bottom, left, right);
        onDrawBorder(canvas, path, top, bottom, left, right);
    }


    protected void onDrawBorder(Canvas canvas, Path path, float top, float bottom, float left, float right) {
        if(mBorderPaint == null) return;
        canvas.drawPath(mPath, mBorderPaint);
    }

    protected void onDrawPath(Canvas canvas, Path path, float top, float bottom, float left, float right) {
        if (mPaint == null) return;
        canvas.drawPath(path, mPaint);
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
     *
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
     *
     * @param path
     * @param side
     * @param startPoint
     * @param endPoint
     * @return return true if you need draw line in you own way
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

    public static Paint newSimplePaint(Paint.Style style){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(style);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }
}
