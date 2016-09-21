package com.zero.movedelete;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;


/**
 * 可滑动删除RelativeLayout
 * 作用：可实现子View在MoveRelativeLayout布局范围内可以左滑或者右滑删除
 * 备注：如果没有设置目标子View,则需要被滑动删除的子View为第一个子View
 * @author linzewu
 * @date 16-9-21
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MoveRelativeLayout extends RelativeLayout {
    
    public MoveRelativeLayout(Context context) {
        super(context);
    }

    public MoveRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    
    private static final float DEFAULT_DISTANCE_TO_MOVE = 300f;
    private static final float DEFAULT_DISTANCE_OF_ANIM_END = 500f;
    
    private float mDistanceToMove = DEFAULT_DISTANCE_TO_MOVE;
    private float mDistanceOfAnimEnd = DEFAULT_DISTANCE_OF_ANIM_END;

    private float mDownX;
    private float mMoveX;

    /**
     * 要滑动删除的目标视图
     */
    private View mTargetView;
    /**
     * 设置滑动删除的目标视图
     * @param targetView
     */
    public void setTargetView(View targetView) {
        this.mTargetView = targetView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mIsAnimRunning) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = ev.getRawX();
                if (mTargetView == null) {
                    mTargetView = getChildAt(0);
                }
                if (mTargetView == null) {
                    return false;
                }
                if (mMoveX - mDownX > 0) {
                    /* view向右移动 */
                    mTargetView.setTranslationX((mMoveX - mDownX) * 0.65f);
                } else if (mMoveX - mDownX < 0) {
                    /* view向左移动 */
                    mTargetView.setTranslationX((mMoveX - mDownX) * 0.65f);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mMoveX - mDownX > mDistanceToMove) {
                    animOut();
                } else if (mMoveX - mDownX > 0 && mMoveX - mDownX < mDistanceToMove) {
                    animRecover();
                } else if (mDownX - mMoveX > mDistanceToMove) {
                    animOut();
                } else if (mDownX - mMoveX > 0 && mDownX - mMoveX < mDistanceToMove) {
                    animRecover();
                }
                break;
        }
        return true;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mIsParamsInit) {
            mDistanceToMove = w / 4f;
            mDistanceOfAnimEnd = w / 2f;
            mIsParamsInit = true;
        }
    }

    private boolean mIsParamsInit = false;
    
    /**
     * 动画是否正在运行
     */
    private boolean mIsAnimRunning = false;
    /**
     * 动画执行时间
     */
    private static final long ANIM_DURATION = 600;
    
    private static final long ANIM_RECOVER_DURATION = 250;
    
    /**
     * 动画删除
     * 效果：淡出效果加位移效果
     */
    private void animOut() {
        mIsAnimRunning = true;
        if (mTargetView == null) {
            mTargetView = getChildAt(0);
        }
        float targetDistance = mTargetView.getTranslationX() > 0 ? mDistanceOfAnimEnd :
                -mDistanceOfAnimEnd;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mTargetView.getTranslationX(), targetDistance);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(ANIM_DURATION);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();
                mTargetView.setTranslationX(value);
                mTargetView.setAlpha(fraction < 0.8f ? 1 - fraction - 0.2f : 0);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimRunning = false;
                if (mIMoveListener != null) {
                    mIMoveListener.onDelete();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 动画恢复
     * 效果：弹回原来的位置
     */
    private void animRecover() {
        mIsAnimRunning = true;
        if (mTargetView == null) {
            mTargetView = getChildAt(0);
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(mTargetView.getTranslationX(), 0);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(ANIM_RECOVER_DURATION);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mTargetView.setTranslationX(value);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimRunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * @author linzewu
     */
    public interface IMoveListener {
        void onDelete();
    }
    
    private IMoveListener mIMoveListener;
    public void setIMoveListener(IMoveListener iMoveListener) {
        this.mIMoveListener = iMoveListener;
    }
}
