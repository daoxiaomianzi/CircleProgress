package com.xuyong.circleprogress;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author xuyong
 * @date 17/7/17
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
    private Paint mRoundRectPaint;
    /**
     * 底部字画笔
     */
    private Paint mBottomTextPaint;
    /**
     * 中间分数整数画笔
     */
    private Paint mIntScorePaint;
    /**
     * 中间分数小数画笔
     */
    private Paint mDecScorePaint;

    /**
     * 更新日期画笔
     */
    private Paint mDatePaint;
    /**
     * 外层圆弧需要
     */
    private RectF mArcRect;
    /**
     * 圆弧宽度
     */
    private final float mArcWidth = 12.0f;
    /**
     * 背景圆弧颜色
     */
    private int mArcBgColor = 0xFF95A4B9;
    /**
     * 前景圆弧结束颜色
     */
    private int mArcForeEndColor = 0xFF286CE5;
    /**
     * 前景圆弧开始颜色
     */
    private int mArcForeStartColor = 0xFF76A6FF;
    /**
     * 虚线默认颜色
     */
    private int mDottedDefaultColor = 0xFF9799A1;
    /**
     * 更新日期颜色
     */
    private final int mDateColor = 0xFFB3B3B3;

    private final int mRoundRectStartColor = 0xFF81A5FF;
    private final int mRoundRectEndColor = 0xFF4883F0;
    /**
     * 线条数
     */
    private int mDottedLineCount = 120;
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
    private int mLineDistance = 8;
    /**
     * 进度条最大值
     */
    private int mProgressMax = 100;
    /**
     * 更新日期
     */
    private String mUpdateDate = "2021.03.10更新";
    /**
     * 分数排行百分比
     */
    private String mScoreRank = "超过99%的同城司机";
    /**
     * 分数
     */
    private String mScore = "850.59";

    /**
     * 是否使用渐变
     */
    protected boolean useGradient = true;

    private int mProgress;
    private float mExternalDottedLineRadius;
    private float mInsideDottedLineRadius;
    private int mArcCenterX;
    private int mRealProgress;

    private int mScoreProgress;

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

    private void intiAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ArcProgressBar);
        mArcBgColor = a.getColor(R.styleable.ArcProgressBar_arcBgColor, mArcBgColor);
        mArcForeStartColor = a.getColor(R.styleable.ArcProgressBar_arcForeStartColor, mArcForeStartColor);
        mArcForeEndColor = a.getColor(R.styleable.ArcProgressBar_arcForeEndColor, mArcForeEndColor);
        mDottedDefaultColor = a.getColor(R.styleable.ArcProgressBar_dottedDefaultColor, mDottedDefaultColor);
        mDottedLineCount = a.getInteger(R.styleable.ArcProgressBar_dottedLineCount, mDottedLineCount);
        mDottedLineWidth = a.getInteger(R.styleable.ArcProgressBar_dottedLineWidth, mDottedLineWidth);
        mDottedLineHeight = a.getInteger(R.styleable.ArcProgressBar_dottedLineHeight, mDottedLineHeight);
        mLineDistance = a.getInteger(R.styleable.ArcProgressBar_lineDistance, mLineDistance);
        mProgressMax = a.getInteger(R.styleable.ArcProgressBar_progressMax, mProgressMax);
        useGradient = a.getBoolean(R.styleable.ArcProgressBar_arcUseGradient, useGradient);
        a.recycle();
    }

    private void initView() {
        // 外层背景圆弧的画笔
        mArcBgPaint = new Paint();
        mArcBgPaint.setAntiAlias(true);
        mArcBgPaint.setStyle(Paint.Style.STROKE);
        mArcBgPaint.setStrokeWidth(dp2px(getResources(), mArcWidth / 3));
        mArcBgPaint.setColor(mArcBgColor);
        mArcBgPaint.setStrokeCap(Paint.Cap.ROUND);
        // 外层前景圆弧的画笔
        mArcForePaint = new Paint();
        mArcForePaint.setAntiAlias(true);
        mArcForePaint.setStyle(Paint.Style.STROKE);
        mArcForePaint.setStrokeWidth(dp2px(getResources(), mArcWidth));
        mArcForePaint.setStrokeCap(Paint.Cap.ROUND);
        // 内测虚线的画笔
        mDottedLinePaint = new Paint();
        mDottedLinePaint.setAntiAlias(true);
        mDottedLinePaint.setStrokeWidth(dp2px(getResources(), mDottedLineHeight));
        mDottedLinePaint.setColor(mDottedDefaultColor);
        //
        mRoundRectPaint = new Paint();
        mRoundRectPaint.setAntiAlias(true);
        mRoundRectPaint.setColor(Color.WHITE);
        mRoundRectPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mRoundRectPaint.setStyle(Paint.Style.FILL);
        // 中间整数分数画笔
        mIntScorePaint = new Paint();
        mIntScorePaint.setAntiAlias(true);
        mIntScorePaint.setColor(Color.WHITE);
        mIntScorePaint.setTextSize(dp2px(getResources(), 56));
        //中间小数分数画笔
        mDecScorePaint = new Paint();
        mDecScorePaint.setAntiAlias(true);
        mDecScorePaint.setColor(Color.WHITE);
        mDecScorePaint.setTextSize(dp2px(getResources(), 20));
        //更新日期画笔
        mDatePaint = new Paint();
        mDatePaint.setAntiAlias(true);
        mDatePaint.setTextSize(dp2px(getResources(), 14));
        mDatePaint.setColor(mDateColor);
        //底部文字画笔
        mBottomTextPaint = new Paint();
        mBottomTextPaint.setTextSize(dp2px(getResources(), 16));
        mBottomTextPaint.setColor(Color.WHITE);
        mBottomTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
        //设置矩形的宽度
        mArcRect.inset(dp2px(getResources(), mArcWidth) / 2, dp2px(getResources(), mArcWidth) / 2);
        // 圆弧半径
        int mArcRadius = (int) (mArcRect.width() / 2);
        // 内部虚线的外部半径
        mExternalDottedLineRadius = mArcRadius - dp2px(getResources(), mArcWidth) / 2 - dp2px(getResources(), mLineDistance);
        // 内部虚线的内部半径
        mInsideDottedLineRadius = mExternalDottedLineRadius - dp2px(getResources(), mDottedLineWidth);
        if (useGradient) {
            SweepGradient sweepGradient = new SweepGradient(mArcCenterX, mArcCenterX, mArcForeStartColor, mArcForeEndColor);
            mArcForePaint.setShader(sweepGradient);
        } else {
            mArcForePaint.setColor(mArcForeStartColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDottedLineArc(canvas);
        drawRunText(canvas);
        drawInitView(canvas);
        drawRunFullLineArc(canvas);
    }

    private void drawInitView(Canvas canvas) {
        canvas.drawText(mUpdateDate, mArcCenterX - mDatePaint.measureText(mUpdateDate) / 2,
                mArcCenterX - (mDatePaint.descent() - mDatePaint.ascent())
                        - dp2px(getResources(), 35), mDatePaint);
        float roundRectLeft = mArcCenterX - mBottomTextPaint.measureText(mScoreRank) / 2 - dp2px(getResources(), 10);
        float roundRectTop = mArcCenterX + dp2px(getResources(), 43);
        float roundRectRight = mArcCenterX + mBottomTextPaint.measureText(mScoreRank) / 2 + dp2px(getResources(), 10);
        float roundRectBottom = mArcCenterX + dp2px(getResources(), 70);
        Shader shader = new LinearGradient(roundRectLeft
                , roundRectTop
                , roundRectRight
                , roundRectBottom
                , new int[]{mRoundRectStartColor, mRoundRectEndColor},
                null, Shader.TileMode.CLAMP);
        mRoundRectPaint.setShader(shader);
        canvas.drawRoundRect(new RectF(roundRectLeft,
                        roundRectTop,
                        roundRectRight,
                        roundRectBottom),
                dp2px(getResources(), 12), dp2px(getResources(), 12), mRoundRectPaint);
        float bottomTextX = mArcCenterX - mBottomTextPaint.measureText(mScoreRank) / 2;
        canvas.drawText(mScoreRank, bottomTextX
                , mArcCenterX + (mBottomTextPaint.descent() - mBottomTextPaint.ascent()) + dp2px(getResources(), 43),
                mBottomTextPaint
        );
        canvas.rotate(155, mArcCenterX, mArcCenterX);
        //画背景圆弧
        canvas.drawArc(mArcRect, 0, 230, false, mArcBgPaint);
    }


    private void drawRunText(Canvas canvas) {
        String[] scores = spiltScore(mScore);
        String decString = "." + scores[1];
        String intString = scores[0];
        if (mScoreProgress * 20 < Integer.parseInt(intString)) {
            String string = mScoreProgress * 20 + "";
            canvas.drawText(".00", mArcCenterX + mIntScorePaint.measureText(string) / 2 - mDecScorePaint.measureText(".00") / 2,
                    mArcCenterX + (mDecScorePaint.descent() - mDecScorePaint.ascent()) / 2, mDecScorePaint);
            canvas.drawText(string, mArcCenterX - mIntScorePaint.measureText(string) / 2 - mDecScorePaint.measureText(".00") / 2,
                    mArcCenterX + (mDecScorePaint.descent() - mDecScorePaint.ascent()) / 2, mIntScorePaint);
        } else {
            canvas.drawText(decString, mArcCenterX + mIntScorePaint.measureText(intString) / 2 - mDecScorePaint.measureText(decString) / 2,
                    mArcCenterX + (mDecScorePaint.descent() - mDecScorePaint.ascent()) / 2, mDecScorePaint);
            canvas.drawText(intString, mArcCenterX - mIntScorePaint.measureText(intString) / 2 - mDecScorePaint.measureText(decString) / 2,
                    mArcCenterX + (mDecScorePaint.descent() - mDecScorePaint.ascent()) / 2, mIntScorePaint);
        }

    }

    /**
     * 实线变动
     *
     * @param canvas 画布
     */
    private void drawRunFullLineArc(Canvas canvas) {
        canvas.rotate(-3, mArcCenterX, mArcCenterX);
        for (int i = 0; i < mProgress; i++) {
            canvas.drawArc(mArcRect, 3, 230 * mProgress / 80, false, mArcForePaint);
        }
    }

    /**
     * 虚线
     */
    private void drawDottedLineArc(Canvas canvas) {
        mDottedLinePaint.setColor(mDottedDefaultColor);
        // 360 * Math.PI / 180
        float evenryDegrees = (float) (2.0f * Math.PI / mDottedLineCount);

        float startDegress = (float) (112 * Math.PI / 180);
        float endDegress = (float) (248 * Math.PI / 180);

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

    /**
     * 设置最大进度
     *
     * @param max
     */
    public void setMaxProgress(int max) {
        this.mProgressMax = max;
    }

    /**
     * 设置 更新日期描述
     *
     * @param updateDate 更新日期
     */
    public void setUpdateDate(String updateDate) {
        this.mUpdateDate = updateDate;
    }

    /**
     * 设置分数排行百分比
     *
     * @param scoreRank scoreRank
     */
    public void setScoreRank(String scoreRank) {
        this.mScoreRank = scoreRank;
    }

    public void setScore(String score) {
        this.mScore = score;
    }

    /**
     * 设置当前进度
     *
     * @param progress progress
     */
    public void setProgress(int progress) {
        // 进度100% = 控件的80%
        this.mRealProgress = progress;
        this.mProgress = (80 * progress) / mProgressMax;
        postInvalidate();
    }


    public void setScoreProgress(int progress) {
        this.mScoreProgress = progress;
        postInvalidate();
    }


    private float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }


    private String[] spiltScore(String mScore) {
        String[] spiltScores;
        if (mScore.contains(".")) {
            spiltScores = mScore.split("\\.");
        } else {
            spiltScores = new String[]{mScore, "00"};
        }
        return spiltScores;
    }
}
