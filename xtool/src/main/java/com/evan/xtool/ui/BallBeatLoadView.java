package com.evan.xtool.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.evan.xtool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evan on 2016/6/2.
 */
public class BallBeatLoadView extends View {

    public static final float SCALE=1.0f;

    public static final int ALPHA=255;

    List<Float> scaleList = new ArrayList<>();
    List<Integer> alphasList = new ArrayList<>();

    //Sizes (with defaults in DP)
    public static final int DEFAULT_SIZE=120;

    public static final int DEFAULT_RADIUS=8;

    //attrs
    int circleCount;
    int circleColor;
    float radius;

    Paint mPaint;

    private boolean mHasAnimation;

    List<Animator> mAnimators = new ArrayList<>();

    public BallBeatLoadView(Context context) {
        this(context, null);
    }

    public BallBeatLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallBeatLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView);
        circleCount=a.getInt(R.styleable.LoadingView_count, 3);
        circleColor =a.getColor(R.styleable.LoadingView_circleColor, Color.WHITE);
        radius = a.getDimension(R.styleable.LoadingView_radius, dp2px(DEFAULT_RADIUS));
        a.recycle();
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(circleColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        for(int i =0; i<circleCount;i++){
            scaleList.add(SCALE);
            alphasList.add(ALPHA);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width  = measureDimension(dp2px(DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(dp2px((int) (radius*2)), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize,int measureSpec){
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float circleSpacing=(getWidth()-(circleCount*radius*2))/(circleCount-1);

        float y = getHeight() / 2;
        for (int i = 0; i < circleCount; i++) {
            canvas.save();
            float translateX=radius+(radius*2)*i+circleSpacing*i;
            canvas.translate(translateX, y);
            canvas.scale(scaleList.get(i), scaleList.get(i));
            mPaint.setAlpha(alphasList.get(i));
            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!mHasAnimation){
            mHasAnimation=true;
            mAnimators=createAnimation();
        }
    }

    @Override
    public void setVisibility(int v) {
        if (getVisibility() != v) {
            super.setVisibility(v);
            if (v == GONE || v == INVISIBLE) {
                setAnimationStatus(AnimStatus.END);
            } else {
                setAnimationStatus(AnimStatus.START);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mHasAnimation) {
            setAnimationStatus(AnimStatus.START);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setAnimationStatus(AnimStatus.CANCEL);
    }

    public List<Animator> createAnimation() {
        List<Animator> animators=new ArrayList<>();

        for (int i = 0; i < circleCount; i++) {
            final int index=i;
            ValueAnimator scaleAnim= ValueAnimator.ofFloat(1,0.5f,1);
            scaleAnim.setDuration(350*circleCount);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(350*i);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    scaleList.add(index, value);
                    postInvalidate();
                }
            });
            scaleAnim.start();

            ValueAnimator alphaAnim=ValueAnimator.ofInt(255,51,255);
            alphaAnim.setDuration(350*circleCount);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(350*i);
            alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    alphasList.add(index, value);
                    postInvalidate();
                }
            });
            alphaAnim.start();
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }

    /**
     * make animation to start or end when target
     * view was be Visible or Gone or Invisible.
     * make animation to cancel when target view
     * be onDetachedFromWindow.
     * @param animStatus
     */
    public void setAnimationStatus(AnimStatus animStatus){

        if (mAnimators==null){
            return;
        }
        int count=mAnimators.size();
        for (int i = 0; i < count; i++) {
            Animator animator=mAnimators.get(i);
            boolean isRunning=animator.isRunning();
            switch (animStatus){
                case START:
                    if (!isRunning){
                        animator.start();
                    }
                    break;
                case END:
                    if (isRunning){
                        animator.end();
                    }
                    break;
                case CANCEL:
                    if (isRunning){
                        animator.cancel();
                    }
                    break;
            }
        }
    }

    public enum AnimStatus{
        START,END,CANCEL
    }

    private int dp2px(int dpValue) {
        return (int) getContext().getResources().getDisplayMetrics().density * dpValue;
    }
}
