package com.popfisher.progresscollection.progressviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * 普通圆弧进度条
 */
public class NormalArcProgressView extends View {

    private static final int ROUND_COLOR_DEFAULT = 0x50000000;
    private static final int ROUND_WIDTH_DEFAULT = 2; //dp
    private static final int ROUND_PROGRESS_COLOR_DEFAULT = 0xff3860ff;

    /** 顶部作为顺时针旋转计数起点，值270, 右边是0，左边是180，底部是90 */
    private static final int CLOCKWISE_START_POINT_TOP = 270;
    /** 顶部作为顺时针旋转计数起点，值-90, 左边是-180，右边是0，底部是-270 */
    private static final int COUNTERCLOCKWISE_START_POINT_TOP = -90;
    /** 弧长 */
    private static final int ARC_LENGTH = 270;
    /** 总弧长 */
    private static final int TOTAL_LENGTH = 360;
    /** 每次旋转的距离：旋转的角度 */
    private static final int ROTATE_SPEED = 10;

    /** 定义一支画笔 */
    private Paint mPaint;

    /** 圆环的颜色 */
    private int mRoundColor;
    /** 圆环的宽度 */
    private float mRoundWidth;
    /** 一半圆环的宽度 */
    private float mHalfRoundWidth;
    /** 圆环进度的颜色 */
    private int mRoundProgressColor;

    /** 进度旋转方向，顺时针(CLOCKWISE)或者逆时针(COUNTERCLOCKWISE) */
    private int mRotateOrientation;
    public static final int COUNTERCLOCKWISE = 0;
    public static final int CLOCKWISE = 1;

    private int mCenterX = 0;     // 获取圆心的x坐标
    private int mRadius =0;       // 圆环的半径
    private RectF mSrcRect = new RectF();
    private boolean isStartProgress = false;
    private int mDrawStartAngle = 0;

    public NormalArcProgressView(Context context) {
        this(context, null);
    }

    public NormalArcProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NormalArcProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mRoundColor = ROUND_COLOR_DEFAULT;
        mRoundProgressColor = ROUND_PROGRESS_COLOR_DEFAULT;

        final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        mRoundWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ROUND_WIDTH_DEFAULT, metrics);
        mHalfRoundWidth = mRoundWidth / 2;
        mRotateOrientation = CLOCKWISE;
        mDrawStartAngle = mRotateOrientation == CLOCKWISE ? CLOCKWISE_START_POINT_TOP : COUNTERCLOCKWISE_START_POINT_TOP;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCenterX == 0) {
            mCenterX = getWidth() / 2;                         // 获取圆心的x坐标
        }
        if (mRadius == 0) {
            mRadius = (int) (mCenterX - mHalfRoundWidth -1);       // 圆环的半径
        }
        if (mSrcRect.isEmpty()) {
            mSrcRect.set(0 + mHalfRoundWidth + 1, 0 + mHalfRoundWidth + 1, getWidth() - mHalfRoundWidth - 1, getHeight() - mHalfRoundWidth - 1);
        }
        // 画最外层的大圆环
        drawOuterCircle(canvas, mCenterX, mRadius);
        // 画进度圆弧
        if (isStartProgress) {
            drawProgressArc(canvas);
        }
    }

    private void drawOuterCircle(final Canvas canvas, final int centerX, final int radius) {
        mPaint.setColor(mRoundColor);                        // 设置圆环的颜色
        mPaint.setStyle(Paint.Style.STROKE);                 // 设置空心
        mPaint.setStrokeWidth(mRoundWidth);                  // 设置圆环的宽度
        mPaint.setAntiAlias(true);                           // 消除锯齿
        canvas.drawCircle(centerX, centerX, radius, mPaint); // 画出圆环
    }

    private void drawProgressArc(final Canvas canvas) {
        mPaint.setColor(mRoundProgressColor);
        if (mRotateOrientation == CLOCKWISE) {
            mDrawStartAngle = (mDrawStartAngle + ROTATE_SPEED) % TOTAL_LENGTH;
            canvas.drawArc(mSrcRect, mDrawStartAngle, ARC_LENGTH, false, mPaint);
        } else {
            mDrawStartAngle = (mDrawStartAngle - ROTATE_SPEED) % TOTAL_LENGTH;
            canvas.drawArc(mSrcRect,  mDrawStartAngle , -ARC_LENGTH, false, mPaint);
        }
        postInvalidate();
    }

    public void startProgress() {
        isStartProgress = true;
        invalidate();
    }

    public void stopProgress() {
        isStartProgress = false;
        invalidate();
    }

    public void setRoundColor(int roundColor) {
        mRoundColor = roundColor;
    }

    public void setRoundWidth(float roundWidth) {
        mRoundWidth = roundWidth;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        mRoundProgressColor = roundProgressColor;
    }

    public void setRotateOrientation(int rotateOrientation) {
        mRotateOrientation = rotateOrientation;
        mDrawStartAngle = mRotateOrientation == CLOCKWISE ? CLOCKWISE_START_POINT_TOP : COUNTERCLOCKWISE_START_POINT_TOP;
    }
}
