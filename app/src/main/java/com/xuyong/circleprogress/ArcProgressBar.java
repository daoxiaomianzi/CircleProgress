package com.xuyong.circleprogress;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by xuyong on 17/7/17.
 */
public class ArcProgressBar extends View {
    /**
     * 外层背景圆弧画笔
     */
    private Paint mArcBgPaint;
    /**
     * 外层前景圆弧画笔
     */
    private Paint mArcForePaint;
    /**
     * 内层虚线画笔
     */
    private Paint mDottedLinePaint;
    /**
     * 底部字矩形背景画笔
     */
    private Paint mRonudRectPaint;
    /**
     * 中间进度画笔
     */
    private Paint mProgressPaint;
    /**
     * 外层圆弧需要
     */
    private RectF mArcRect;
    /**
     * 圆弧宽度
     */
    private float mArcWidth = 35.0f;
    /**
     * 背景圆弧颜色
     */
    private int mArcBgColor = 0xFFC9D7EC;
    /**
     * 前景圆弧结束颜色
     */
    private int mArcForeEndColor = 0xFF46D0FA;
    /**
     * 前景圆弧开始颜色
     */
    private int mArcForeStartColor = 0xFF3486D7;
    /**
     * 虚线默认颜色
     */
    private int mDottedDefaultColor = 0xFF9799A1;
    /**
     * 虚线变动颜色
     */
    private int mDottedRunColor = 0xFF3895EB;
    /**
     * 圆弧两边的距离
     */
    private int mPdDistance = 50;

    /**
     * 线条数
     */
    private int mDottedLineCount = 100;
    /**
     * 线条宽度
     */
    private int mDottedLineWidth = 40;
    /**
     * 线条高度
     */
    private int mDottedLineHeight = 6;
    /**
     * 圆弧跟虚线之间的距离
     */
    private int mLineDistance = 25;
    /**
     * 进度条最大值
     */
    private int mProgressMax = 100;
    /**
     * 进度文字大小
     */
    private int mProgressTextSize = 35;
    /**
     * 进度文字颜色
     */
    private int mProgressTextRunColor = 0xFFDA6E81;

    /**
     * 进度描述
     */
    private String mProgressDesc;

    //是否使用渐变
    protected boolean useGradient = true;

    private int mScressWidth;
    private int mProgress;
    private float mExternalDottedLineRadius;
    private float mInsideDottedLineRadius;
    private int mArcCenterX;
    private int mArcRadius; // 圆弧半径
    private double bDistance;
    private double aDistance;
    private boolean isRestart = false;
    private int mRealProgress;


    public ArcProgressBar(Context context) {
        this(context, null, 0);
    }

    public ArcProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiAttributes(context, attrs);
        initView();
    }

    //开放api//
    public void setmArcBgColor(int mArcBgColor) {
        this.mArcBgColor = mArcBgColor;
    }

    public void setmArcForeEndColor(int mArcForeEndColor) {
        this.mArcForeEndColor = mArcForeEndColor;
    }

    public void setmArcForeStartColor(int mArcForeStartColor) {
        this.mArcForeStartColor = mArcForeStartColor;
    }

    public void setmDottedDefaultColor(int mDottedDefaultColor) {
        this.mDottedDefaultColor = mDottedDefaultColor;
    }

    public void setmDottedRunColor(int mDottedRunColor) {
        this.mDottedRunColor = mDottedRunColor;
    }

    public void setmPdDistance(int mPdDistance) {
        this.mPdDistance = mPdDistance;
    }

    public void setmDottedLineCount(int mDottedLineCount) {
        this.mDottedLineCount = mDottedLineCount;
    }

    public void setmDottedLineWidth(int mDottedLineWidth) {
        this.mDottedLineWidth = mDottedLineWidth;
    }

    public void setmDottedLineHeight(int mDottedLineHeight) {
        this.mDottedLineHeight = mDottedLineHeight;
    }

    public void setmLineDistance(int mLineDistance) {
        this.mLineDistance = mLineDistance;
    }

    public void setmProgressMax(int mProgressMax) {
        this.mProgressMax = mProgressMax;
    }

    public void setmProgressTextSize(int mProgressTextSize) {
        this.mProgressTextSize = mProgressTextSize;
    }

    public void setmProgressTextRunColor(int mProgressTextRunColor) {
        this.mProgressTextRunColor = mProgressTextRunColor;
    }

    public void setmProgressDesc(String mProgressDesc) {
        this.mProgressDesc = mProgressDesc;
    }

    public void setUseGradient(boolean useGradient) {
        this.useGradient = useGradient;
    }

    //开放api//

    private void intiAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcProgressBar);
        mPdDistance = a.getInteger(R.styleable.ArcProgressBar_arcDistance, mPdDistance);
        mArcBgColor = a.getColor(R.styleable.ArcProgressBar_arcBgColor, mArcBgColor);
        mArcForeStartColor = a.getColor(R.styleable.ArcProgressBar_arcForeStartColor, mArcForeStartColor);
        mArcForeEndColor = a.getColor(R.styleable.ArcProgressBar_arcForeEndColor, mArcForeEndColor);
        mDottedDefaultColor = a.getColor(R.styleable.ArcProgressBar_dottedDefaultColor, mDottedDefaultColor);
        mDottedRunColor = a.getColor(R.styleable.ArcProgressBar_dottedRunColor, mDottedRunColor);
        mDottedLineCount = a.getInteger(R.styleable.ArcProgressBar_dottedLineCount, mDottedLineCount);
        mDottedLineWidth = a.getInteger(R.styleable.ArcProgressBar_dottedLineWidth, mDottedLineWidth);
        mDottedLineHeight = a.getInteger(R.styleable.ArcProgressBar_dottedLineHeight, mDottedLineHeight);
        mLineDistance = a.getInteger(R.styleable.ArcProgressBar_lineDistance, mLineDistance);
        mProgressMax = a.getInteger(R.styleable.ArcProgressBar_progressMax, mProgressMax);
        mProgressTextSize = a.getInteger(R.styleable.ArcProgressBar_progressTextSize, mProgressTextSize);
        mProgressDesc = a.getString(R.styleable.ArcProgressBar_progressDesc);
        mProgressTextRunColor = a.getColor(R.styleable.ArcProgressBar_progressTextRunColor, mProgressTextRunColor);
        useGradient = a.getBoolean(R.styleable.ArcProgressBar_arcUseGradient, useGradient);
        a.recycle();
    }

    private void initView() {
        int[] screenWH = getScreenWH();
        mScressWidth = screenWH[0];
        // 外层背景圆弧的画笔
        mArcBgPaint = new Paint();
        mArcBgPaint.setAntiAlias(true);
        mArcBgPaint.setStyle(Paint.Style.STROKE);
        mArcBgPaint.setStrokeWidth(mArcWidth);
        mArcBgPaint.setColor(mArcBgColor);
        mArcBgPaint.setStrokeCap(Paint.Cap.ROUND);
        // 外层前景圆弧的画笔
        mArcForePaint = new Paint();
        mArcForePaint.setAntiAlias(true);
        mArcForePaint.setStyle(Paint.Style.STROKE);
        mArcForePaint.setStrokeWidth(mArcWidth);
        if (useGradient) {
            LinearGradient gradient = new LinearGradient(0, 0, mScressWidth - 2 * mPdDistance, mScressWidth - 2 * mPdDistance, mArcForeEndColor, mArcForeStartColor, Shader.TileMode.CLAMP);
            mArcForePaint.setShader(gradient);
        } else {
            mArcForePaint.setColor(mArcForeStartColor);
        }
        mArcForePaint.setStrokeCap(Paint.Cap.ROUND);
        // 内测虚线的画笔
        mDottedLinePaint = new Paint();
        mDottedLinePaint.setAntiAlias(true);
        mDottedLinePaint.setStrokeWidth(mDottedLineHeight);
        mDottedLinePaint.setColor(mDottedDefaultColor);
        //
        mRonudRectPaint = new Paint();
        mRonudRectPaint.setAntiAlias(true);
        mRonudRectPaint.setColor(mDottedRunColor);
        mRonudRectPaint.setStyle(Paint.Style.FILL);
        // 中间进度画笔
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = mScressWidth - 2 * mPdDistance;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mArcCenterX = (int) (w / 2.f);

        mArcRect = new RectF();
        mArcRect.top = 0;
        mArcRect.left = 0;
        mArcRect.right = w;
        mArcRect.bottom = h;
        mArcRect.inset(mArcWidth / 2, mArcWidth / 2);//设置矩形的宽度
        mArcRadius = (int) (mArcRect.width() / 2);

        double sqrt = Math.sqrt(mArcRadius * mArcRadius + mArcRadius * mArcRadius);
        bDistance = Math.cos(Math.PI * 45 / 180) * mArcRadius;
        aDistance = Math.sin(Math.PI * 45 / 180) * mArcRadius;

        // 内部虚线的外部半径
        mExternalDottedLineRadius = mArcRadius - mArcWidth / 2 - mLineDistance;
        // 内部虚线的内部半径
        mInsideDottedLineRadius = mExternalDottedLineRadius - mDottedLineWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mArcBgPaint.setColor(mArcBgColor);
        canvas.drawText("信用额度", mArcCenterX - mProgressPaint.measureText("信用额度") / 2,
                mArcCenterX - (mProgressPaint.descent() + mProgressPaint.ascent()) / 2 - 60, mProgressPaint);
        drawDottedLineArc(canvas);
        drawRunDottedLineArc(canvas);
        drawRunText(canvas);
        canvas.rotate(135, mArcCenterX, mArcCenterX);
        canvas.drawArc(mArcRect, 0, 270, false, mArcBgPaint);//画背景圆弧
//        canvas.drawRect(mArcRect, mArcBgPaint);//画直角矩形
//        canvas.drawCircle(400, 400, 100, mArcForePaint);//画圆
        canvas.drawArc(mArcRect, 0, 0, false, mArcForePaint);//画前景圆弧
//        canvas.drawColor(Color.TRANSPARENT);//设置画布背景
//        canvas.drawLine(100, 100, 400, 400, mArcBgPaint);//画直线
//        canvas.drawPoint(500, 500, mArcBgPaint);//画点
        mProgressPaint.setColor(getResources().getColor(R.color.gray));
        mProgressPaint.setTextSize(dp2px(getResources(), 18));
        drawRunFullLineArc(canvas);
        if (isRestart) {
            drawDottedLineArc(canvas);
        }
    }

    private void drawRunText(Canvas canvas) {
        String progressStr = (mRealProgress * 25) + "";
        if (!TextUtils.isEmpty(mProgressDesc)) {
            progressStr = mProgressDesc;
        }
        mProgressPaint.setTextSize(dp2px(getResources(), mProgressTextSize));
        mProgressPaint.setColor(mProgressTextRunColor);
        canvas.drawText(progressStr, mArcCenterX - mProgressPaint.measureText(progressStr) / 2,
                mArcCenterX - (mProgressPaint.descent() + mProgressPaint.ascent()) / 2 + 40, mProgressPaint);
    }

    public void restart() {
        isRestart = true;
        this.mRealProgress = 0;
        this.mProgressDesc = "";
        invalidate();
    }

    /**
     * 设置中间进度描述
     *
     * @param desc
     */
    public void setProgressDesc(String desc) {
        this.mProgressDesc = desc;
        postInvalidate();
    }

    /**
     * 设置最大进度
     *
     * @param max
     */
    public void setMaxProgress(int max) {
        this.mProgressMax = max;
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        // 进度100% = 控件的75%
        this.mRealProgress = progress;
        isRestart = false;
        this.mProgress = ((mDottedLineCount * 3 / 4) * progress) / mProgressMax;
        postInvalidate();
    }


    /**
     * 虚线变动
     *
     * @param canvas
     */
    private void drawRunDottedLineArc(Canvas canvas) {
        mDottedLinePaint.setColor(mDottedRunColor);
        float evenryDegrees = (float) (2.0f * Math.PI / mDottedLineCount);

        float startDegress = (float) (225 * Math.PI / 180) + evenryDegrees / 2;

        for (int i = 0; i < mProgress; i++) {
            float degrees = i * evenryDegrees + startDegress;

            float startX = mArcCenterX + (float) Math.sin(degrees) * mInsideDottedLineRadius;
            float startY = mArcCenterX - (float) Math.cos(degrees) * mInsideDottedLineRadius;

            float stopX = mArcCenterX + (float) Math.sin(degrees) * mExternalDottedLineRadius;
            float stopY = mArcCenterX - (float) Math.cos(degrees) * mExternalDottedLineRadius;

            canvas.drawLine(startX, startY, stopX, stopY, mDottedLinePaint);
        }
    }

    /**
     * 实线变动
     *
     * @param canvas
     */
    private void drawRunFullLineArc(Canvas canvas) {
        for (int i = 0; i < mProgress; i++) {
            canvas.drawArc(mArcRect, 0, 270 * mProgress / 75, false, mArcForePaint);
        }
    }

    private void drawDottedLineArc(Canvas canvas) {
        mDottedLinePaint.setColor(mDottedDefaultColor);
        // 360 * Math.PI / 180
        float evenryDegrees = (float) (2.0f * Math.PI / mDottedLineCount);

        float startDegress = (float) (135 * Math.PI / 180);
        float endDegress = (float) (225 * Math.PI / 180);

        for (int i = 0; i < mDottedLineCount; i++) {
            float degrees = i * evenryDegrees;
            // 过滤底部90度的弧长
            if (degrees > startDegress && degrees < endDegress) {
                continue;
            }

            float startX = mArcCenterX + (float) Math.sin(degrees) * mInsideDottedLineRadius;
            float startY = mArcCenterX - (float) Math.cos(degrees) * mInsideDottedLineRadius;

            float stopX = mArcCenterX + (float) Math.sin(degrees) * mExternalDottedLineRadius;
            float stopY = mArcCenterX - (float) Math.cos(degrees) * mExternalDottedLineRadius;


            canvas.drawLine(startX, startY, stopX, stopY, mDottedLinePaint);
        }
    }

    private int[] getScreenWH() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int[] wh = {displayMetrics.widthPixels, displayMetrics.heightPixels};
        return wh;
    }

    private float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }
}
