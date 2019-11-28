package com.lance.lockpattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LockPatternNewView extends View {
    // interface
    public int test_time;
    public int test_layout;
    public int test_target;
    public boolean test_enlarge;
    public boolean test_fast_leave;

    // DEFINE
    private static int ANSWER_STRING = 36987452;
    private static final int NONE = 0;
    private static final int LESS = 1;
    private static final int ERROR = 2;
    private static final int CORRECT = 3;
    private static final int GO_SECOND = 4;
    private static final int CHOOSE = 5;
    private static final int NOTHING = 6;
    private static final int LEAVE_RIGHT = 7;
    private static final int LEAVE_WRONG = 8;
    private static final int NORMAL_COLOR = 0x3F979797;//正常状态的颜色
    private static final int SELECTED_COLOR = 0xFF70DBDB;//选中状态的颜色

    // states
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int WAIT = 2;

    // condition
    private boolean mIsSelected;//第一个点是否选中
    private boolean mIsFinished;//是否绘制结束
    private boolean mIsMovingWithoutCircle = false;//正在滑动并且没有任何点选中

    // lock pattern of the first layer...
    private Paint mCirclePaint;// 圆的画笔
    private Paint mLinePaint;//线的画笔
    private PointView[][] mPointViewArray = new PointView[3][3];//圆心数组
    private List<PointView> mSelectedPointViewList;//保存选中点的集合

    // settings
    private int mPatternWidth;//解锁图案的边长
    private float mRadius;//半径
    private float mCurrentX, mCurrentY;
    private int mIndex = 1;//每个圆圈的下标

    //图案监听器
    private OnPatternChangeListener mOnPatternChangeListener;
    private int messageStatus = 0;// 判斷顯示訊息

    // hover detection
    private boolean check = false;
    protected int state = FIRST;
    private int hover_num = -1;
    private int hover_num_prev = -1;

    // icon settings...
    private static int magnify = 4;
    private static int stroke = 20*magnify;
    private static int size = 40*magnify;
    private static int distance = 50*magnify;
    private static int ct_top = 83*magnify;
    private static int ct_left = 135*magnify;
    private static final int true_degree_upper[][] = {{135, 45, 315, 225}, {90, 360, 270, 180}, {120, 60, 360, 300, 240, 180}, {90, 30, 330, 270, 210, 150}};
    private static final int true_degree_lower[][] = {{45, -45, 225, 135}, {0, 270, 180, 90}, {60, 0, 300, 240, 180, 120}, {30, -30, 270, 210, 150, 90}};
    private Bitmap rawBitmap[] = {BitmapFactory.decodeResource(getResources(),R.drawable.icon1),
                                    BitmapFactory.decodeResource(getResources(),R.drawable.icon2),
                                    BitmapFactory.decodeResource(getResources(),R.drawable.icon3),
                                    BitmapFactory.decodeResource(getResources(),R.drawable.icon4),
                                    BitmapFactory.decodeResource(getResources(),R.drawable.icon5),
                                    BitmapFactory.decodeResource(getResources(),R.drawable.icon6)};

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
        if(state == FIRST){
            state = WAIT;
            second_activator();
        }
        }
    };
    private void second_activator(){
        messageStatus = NONE;
        String patternPassword = "";
        for (PointView pointView : mSelectedPointViewList) {
            patternPassword += pointView.getIndex();
        }
        if(test_fast_leave){
            if (Integer.valueOf(patternPassword) != ANSWER_STRING){
                // error
                messageStatus = LEAVE_WRONG;
                mOnPatternChangeListener.onPatternStatusChange(messageStatus, 0, 0);
            }
            else {
                // correct
                messageStatus = LEAVE_RIGHT;
                mOnPatternChangeListener.onPatternStatusChange(messageStatus, 0, 0);
            }
        }
        else {
            if (Integer.valueOf(patternPassword) != ANSWER_STRING){
                //clear
                mSelectedPointViewList.clear();
                //message
                messageStatus = ERROR;
                mOnPatternChangeListener.onPatternStatusChange(messageStatus, 0, 0);
                //draw
                invalidate();
                //init
                mIsFinished = true;
                mIsSelected = false;
                check = false;
                state = FIRST;
            }
            else {
                //message
                messageStatus = GO_SECOND;
                mOnPatternChangeListener.onPatternStatusChange(messageStatus, 0, 0);
                //draw
                mLinePaint.setAlpha(10);
                mCirclePaint.setAlpha(10);
                invalidate();
                // init
                state = SECOND;
            }
        }
    }

    public LockPatternNewView(Context context) {
        this(context, null);
    }
    public LockPatternNewView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setColor(NORMAL_COLOR);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);
        mLinePaint.setStrokeWidth(stroke);
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
        if(state == SECOND || (state == WAIT && messageStatus == GO_SECOND)) {
            int hover_idx = 0;
            // count the tangent...
            double dX = (double)( mCurrentX-ct_left );
            double dY = (double)( mCurrentY-ct_top );
            //if (dX == 0){return;}
            int tan = (int)(180*Math.atan2(dY, dX)/Math.PI);// HOVER: count the degree...
            int hover_deg;
            if (dX>0 && dY>0) hover_deg = -tan+359;
            else if(dX<0 && dY>0) hover_deg = -tan+359;
            else if(dX>0 && dY<0) hover_deg = -tan;
            else hover_deg = -tan;

            int total_num = (test_layout == 0 || test_layout == 1)? 4 : 6;
            for(int i=0;i<total_num;i++){
                // count deg...
                boolean lower = true_degree_lower[test_layout][i]<0;
                if((!lower && ( hover_deg>=true_degree_lower[test_layout][i] && hover_deg<true_degree_upper[test_layout][i]) )||
                    (lower && (hover_deg>=0 && hover_deg<true_degree_upper[test_layout][i]) ||
                        (hover_deg>=(true_degree_lower[test_layout][i]+360) && hover_deg<360))){
                    hover_idx = i;
                }
            }
            drawicons(canvas, test_target, hover_idx);
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
    private void drawicons(Canvas canvas, int Target, int Hover) {
        //hover_num = -1;
        int total_num = (test_layout == 0 || test_layout == 1)? 4 : 6;
        for(int i=1;i<=total_num;i++){// Target = 1,2,3,4
            Bitmap icon = rawBitmap[i-1];
            // get target id...
            int t_idx = (Target+i-2)%total_num;
            int true_deg = (true_degree_upper[test_layout][t_idx]+true_degree_lower[test_layout][t_idx])/2;
            // count distance
            int dis_X = (int)(Math.cos(Math.toRadians(true_deg))*distance);
            int dis_Y = (int)(-Math.sin(Math.toRadians(true_deg))*distance);
            if(t_idx == Hover && test_enlarge){
                icon = zoomImg(icon, size,size);
                canvas.drawBitmap(icon,ct_left+dis_X-size/2,ct_top+dis_Y-size/2, new Paint());
                hover_num_prev = hover_num;
                hover_num = t_idx;
            }
            else{
                int new_size = size*4/5;
                icon = zoomImg(icon, new_size,new_size);
                canvas.drawBitmap(icon,ct_left+dis_X-new_size/2,ct_top+dis_Y-new_size/2, new Paint());
            }
        }
    }
    public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
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
        if(state == FIRST){
            mCurrentX = event.getX();
            mCurrentY = event.getY();
            messageStatus = NOTHING;
            PointView selectedPointView = null;

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
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
            if(selectedPointView == null){
                check = false;
                timerHandler.removeCallbacks(timerRunnable);
            }
            else{
                if (check == false) {
                    check = true;
                    timerHandler.postDelayed(timerRunnable, test_time);
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
            mOnPatternChangeListener.onPatternStatusChange(messageStatus, 0, 0);
        }
        else if (state == SECOND){
            // init...
            messageStatus = NOTHING;
            mCurrentX = event.getX();
            mCurrentY = event.getY();
            boolean finish = false;
            // interface...
            int x = (int)(mCurrentX-ct_left);
            int y = -(int)(mCurrentY-ct_top);

            switch(event.getAction()) {
                case MotionEvent.ACTION_UP:
                    finish = true;
                    break;
            }

            if(finish){
                Log.i("HOVER", String.valueOf(hover_num));
                if(hover_num == -1){
                    Log.i("BUG", "BUG");
                    //messageStatus = LEAVE_WRONG;
                    //hover_num = 0;
                }
                else{
                    // go to next trial
                    if(hover_num == test_target-1){
                        messageStatus = LEAVE_RIGHT;
                    }
                    else{
                        messageStatus = LEAVE_WRONG;
                    }
                }
            }
            else{
                //Log.i("HOVER_NUM", String.valueOf(hover_num));
                //Log.i("HOVER_NUM", String.valueOf(hover_num_prev));
                if(hover_num != hover_num_prev){
                    messageStatus = CHOOSE;
                }
            }
            mOnPatternChangeListener.onPatternStatusChange(messageStatus, x, y);
            invalidate();
        }
        else{
            // WAIT: none
        }
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
        void onPatternStatusChange(int messageStatus, int x, int y);
    }
}
