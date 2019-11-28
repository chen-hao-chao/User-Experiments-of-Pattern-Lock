package com.lance.lockpattern;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LockPatternView extends View {
    // DEFINE:
    private static int ANSWER_STRING = 36987452;
    private static final int NONE = 0;
    private static final int LESS = 1;
    private static final int ERROR = 2;
    private static final int CORRECT = 3;
    private static final int NOTHING = 6;
    private static final int NORMAL_COLOR = 0x3F979797;//正常状态的颜色
    private static final int SELECTED_COLOR = 0xFF70DBDB;//选中状态的颜色

    // condition
    private boolean mIsSelected;//第一个点是否选中
    private boolean mIsFinished;//是否绘制结束
    private boolean mIsMovingWithoutCircle = false;//正在滑动并且没有任何点选中

    // lock pattern of the first layer...
    private Paint mCirclePaint;// 圆的画笔
    private Paint mLinePaint;//线的画笔
    private PointView[][] mPointViewArray = new PointView[3][3];//圆心数组
    private List<PointView> mSelectedPointViewList;//保存选中点的集合

    // lock pattern of the second layer...
    //private Paint mCirclePaintSec;
    //private Paint mLinePaintSec;//线的画笔
    //private PointView[] mPointViewArraySec = new PointView[3];//圆心数组

    // settings
    private int mPatternWidth;//解锁图案的边长
    private float mRadius;//半径
    private float mCurrentX, mCurrentY;
    private int mIndex = 1;//每个圆圈的下标

    private static int magnify = 4;
    private static int size = 40*magnify;
 
    //图案监听器
    private OnPatternChangeListener mOnPatternChangeListener;
    private int messageStatus = 0;// 判斷顯示訊息

    public LockPatternView(Context context) {
        this(context, null);
    }
 
    public LockPatternView(Context context, AttributeSet attrs) {
        super(context, attrs);
 
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(NORMAL_COLOR);
        mCirclePaint.setStyle(Paint.Style.FILL);
 
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setStrokeWidth(20*magnify);
        mLinePaint.setColor(SELECTED_COLOR);
        mLinePaint.setAlpha(100);
        mLinePaint.setStyle(Paint.Style.STROKE);
 
        mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
 
        mSelectedPointViewList = new ArrayList<>();
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
 
        //取屏幕长和宽中的较小值作为图案的边长
        mPatternWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(mPatternWidth, mPatternWidth);
    }
 
    @Override
    protected void onDraw(Canvas canvas) {
        //画圆
        drawCircle(canvas);
 
        //将选中的圆重新绘制一遍，将选中的点和未选中的点区别开来
        for (PointView pointView : mSelectedPointViewList) {
            mCirclePaint.setColor(SELECTED_COLOR);
            canvas.drawCircle(pointView.x, pointView.y, mRadius, mCirclePaint);
            mCirclePaint.setColor(NORMAL_COLOR);  //每重新绘制一个,将画笔的颜色重置,保证不会影响其他圆的绘制
        }
 
        //点与点画线
        if (mSelectedPointViewList.size() > 0) {
            Point pointViewA = mSelectedPointViewList.get(0);  //第一个选中的点为A点
            for (int i = 0; i < mSelectedPointViewList.size(); i++) {
                Point pointViewB = mSelectedPointViewList.get(i);  //其他依次遍历出来的点为B点
                drawLine(canvas, pointViewA, pointViewB);
                pointViewA = pointViewB;
            }
 
            //点与鼠标当前位置绘制轨迹
            if (mIsMovingWithoutCircle & !mIsFinished) {
                drawLine(canvas, pointViewA, new PointView((int)mCurrentX, (int)mCurrentY));
            }
        }
 
        super.onDraw(canvas);
    }
 
    /**
     * 画圆
     * @param canvas  画布
     */
    private void drawCircle(Canvas canvas) {
        //初始化点的位置
        for (int i = 0; i < mPointViewArray.length; i++) {
            for (int j = 0; j < mPointViewArray.length; j++) {
                //圆心的坐标
                int cx = mPatternWidth / 4 * (j + 1);
                int cy = mPatternWidth / 4 * (i + 1)+size/3;
 
                //将圆心放在一个点数组中
                PointView pointView = new PointView(cx, cy);
                pointView.setIndex(mIndex);
                mPointViewArray[i][j] = pointView;
                canvas.drawCircle(cx, cy, mRadius, mCirclePaint);
                mIndex++;
            }
        }
 
        mIndex = 1;
    }
 
    /**
     * 画线
     * @param canvas  画布
     * @param pointA  第一个点
     * @param pointB  第二个点
     */
    private void drawLine(Canvas canvas, Point pointA, Point pointB) {
        canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, mLinePaint);
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentX = event.getX();
        mCurrentY = event.getY();
        messageStatus = NOTHING;
        PointView selectedPointView = null;
 
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重新绘制
                //messageStatus = 0;
                if (mOnPatternChangeListener != null) {

                }
                mSelectedPointViewList.clear();
                mIsFinished = false;
 
                selectedPointView = checkSelectPoint();
 
                if (selectedPointView != null) {
                    //第一次按下的位置在圆内，被选中
                    mIsSelected = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsSelected) {
                    selectedPointView = checkSelectPoint();
                }
                if (selectedPointView == null) {
                    mIsMovingWithoutCircle = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsFinished = true;
                mIsSelected = false;
                break;
        }
 
        //将选中的点收集起来
        if (!mIsFinished && mIsSelected && selectedPointView != null) {
            if (!mSelectedPointViewList.contains(selectedPointView)) {
                mSelectedPointViewList.add(selectedPointView);
            }
        }
 
        if (mIsFinished) {
            if (mSelectedPointViewList.size() <= 1) {
                // none
                messageStatus = NONE;
                mSelectedPointViewList.clear();
                Log.i("None", "None!");
            }
            else if (mSelectedPointViewList.size() < 5 && mSelectedPointViewList.size() > 1) {
                // 绘制<5, none
                messageStatus = LESS;
                mSelectedPointViewList.clear();
                Log.i("Less", "Less!");
            }
            else {
                // 绘制成功, 1
                String patternPassword = "";
                if (mOnPatternChangeListener != null) {
                    for (PointView pointView : mSelectedPointViewList) {
                        patternPassword += pointView.getIndex();
                    }
                    if (Integer.valueOf(patternPassword) != ANSWER_STRING){
                        //error!
                        messageStatus = ERROR;
                        mSelectedPointViewList.clear();
                        Log.i("Error", "Error!");
                        Log.i("Error", String.valueOf(Integer.valueOf(patternPassword)));
                        Log.i("Error", String.valueOf(Integer.valueOf(ANSWER_STRING)));
                    }
                    else {
                        //correct!
                        messageStatus = CORRECT;
                        mSelectedPointViewList.clear();
                    }
                }
            }
        }
 
        invalidate();
        mOnPatternChangeListener.onPatternStatusChange(messageStatus);
        return true;
    }
 
    /**
     * 判断当前按下的位置是否在圆心数组中
     * @return 返回选中的点
     */
    private PointView checkSelectPoint() {
        for(int i = 0; i < mPointViewArray.length; i++) {
            for (int j = 0; j < mPointViewArray.length; j++) {
                PointView pointView = mPointViewArray[i][j];
                if (isWithinCircle(mCurrentX, mCurrentY, pointView.x, pointView.y, mRadius)) {
                    return pointView;
                }
            }
        }
 
        return null;
    }
 
    /**
     * 判断点是否在圆内
     * @param x      点X轴坐标
     * @param y      点Y轴坐标
     * @param cx     圆心X坐标
     * @param cy     圆心Y坐标
     * @param radius 半径
     * @return       true表示在圆内，false表示在圆外
     */
    private boolean isWithinCircle(float x, float y, float cx, float cy, float radius) {
        //如果点和圆心的距离小于半径，则证明点在圆内
        if (Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y- cy, 2)) <= radius) {
           return true;
        }
 
        return false;
    }
 
    /** 设置图案监听器 **/
    public void setOnPatternChangeListener(OnPatternChangeListener onPatternChangeListener) {
        if (onPatternChangeListener != null) {
            this.mOnPatternChangeListener = onPatternChangeListener;
        }
    }

    /**
     * 图案监听器
     */
    public interface OnPatternChangeListener {
        void onPatternStatusChange(int messageStatus);
    }
}
