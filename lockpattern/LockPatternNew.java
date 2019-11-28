package com.lance.lockpattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LockPatternNew extends Activity implements LockPatternNewView.OnPatternChangeListener {
    // DEFINE:
    private static final int NONE = 0;
    private static final int LESS = 1;
    private static final int ERROR = 2;
    private static final int CORRECT = 3;
    private static final int GO_SECOND = 4;
    private static final int CHOOSE = 5;
    private static final int NOTHING = 6;
    private static final int LEAVE_RIGHT = 7;
    private static final int LEAVE_WRONG = 8;

    // variables
    private TextView mLockPatternHint;
    private LockPatternNewView mLockPatternView;
    private int value = 0;
    private int hover_x = 0;
    private int hover_y = 0;
    private int mode;

    private void initPage(){
        /** GLOBAL VARIABLES **/
        GlobalVariable glo = (GlobalVariable) getApplicationContext();
        mode = glo.getMode();
        int test_layout = glo.getTestLayout(), test_time = glo.getTestTime(), test_target;
        boolean test_enlarge, test_fast_leave;
        if (mode == 1) {
            /** FOR MODE 1 **/
            test_target = glo.getTestTarget();
            test_enlarge = false;
            test_fast_leave = false;
        } else if (mode == 2) {
            /** FOR MODE 2 **/
            test_target = 0; // x
            test_enlarge = false; // x
            test_fast_leave = true;
        } else {
            /** FOR MODE 3 **/
            test_target = glo.getTestTargetFix(glo.getTestLayout(), glo.getCounter(false));
                    //(test_layout == 0 || test_layout == 1) ? (int) (Math.random() * 4 + 1) : (int) (Math.random() * 6 + 1);
            test_enlarge = true;
            test_fast_leave = false;
            glo.setStartTime((int)System.currentTimeMillis());
        }

        /** INIT **/
        mLockPatternHint = (TextView) findViewById(R.id.lock_pattern_hint);
        mLockPatternView = (LockPatternNewView) findViewById(R.id.lock_pattern_view_new);
        mLockPatternView.test_layout = test_layout;
        mLockPatternView.test_time = (test_time == 0) ? 250 : (test_time == 1) ? 400 : 550;
        mLockPatternView.test_target = test_target;
        mLockPatternView.test_enlarge = test_enlarge;
        mLockPatternView.test_fast_leave = test_fast_leave;
        mLockPatternView.setOnPatternChangeListener(this);
    }
    private void leavePage(){
        GlobalVariable glo = (GlobalVariable) getApplicationContext();
        if(mode == 1){
            /** FOR MODE 1 **/
            // index: 0,1,2,...n-1
            glo.storePoint(glo.getTestLayout(), glo.getTestTarget()-1, glo.getCounter(false), hover_x, hover_y);
            glo.addCounter(false); // use the right counter
            if (glo.getCounter(false) == 5) {
                int end = (glo.getTestLayout() == 0 || glo.getTestLayout() == 1) ? 4 : 6;
                if (glo.getTestTarget() == end) {
                    if (glo.getTestLayout() == 3) {
                        glo.setPage(0);
                        Intent intent = new Intent();
                        intent.setClass(LockPatternNew.this, Display.class);
                        startActivity(intent);
                    } else {
                        glo.setTestTarget(1);
                        glo.setTestLayout(glo.getTestLayout() + 1);
                        glo.initCounter();
                        Intent intent = new Intent();
                        intent.setClass(LockPatternNew.this, LockPatternNew.class);
                        startActivity(intent);
                    }
                } else {
                    // go next target
                    glo.setTestTarget(glo.getTestTarget() + 1);
                    glo.initCounter();
                    Intent intent = new Intent();
                    intent.setClass(LockPatternNew.this, LockPatternNew.class);
                    startActivity(intent);
                }
            }
            else{
                Intent intent = new Intent();
                intent.setClass(LockPatternNew.this, LockPatternNew.class);
                startActivity(intent);
            }
        }
        else if (mode == 2){
            /** FOR MODE 2 **/
            glo.addCounter(value == 1);
            int count = glo.getCounter(true)+glo.getCounter(false);
            if (count == 20){// 20
                glo.storeTime(glo.getTestTime(), glo.getCounter(true), glo.getCounter(false));
                if(glo.getTestTime() == 2){
                    glo.setPage(0);
                    Intent intent = new Intent();
                    intent.setClass(LockPatternNew.this, Display.class);
                    startActivity(intent);
                }
                else{
                    glo.setTestTime(glo.getTestTime()+1);
                    glo.initCounter();
                    Intent intent = new Intent();
                    intent.setClass(LockPatternNew.this, LockPatternNew.class);
                    startActivity(intent);
                }
            }
            else{
                // test again
                Intent intent = new Intent();
                intent.setClass(LockPatternNew.this, LockPatternNew.class);
                startActivity(intent);
            }
        }
        else{
            /** FOR MODE 3 **/
            int target = glo.getTestTargetFix(glo.getTestLayout()+2, glo.getCounter(false));;
            int time = (value == 1)? -444 : (int)System.currentTimeMillis() - glo.getStartTime();
            glo.storeTimeLayout(glo.getTestLayout()+2, target-1, time);

            glo.addCounter(false);
            int count = glo.getCounter(false);
            if (((glo.getTestLayout() == 0 || glo.getTestLayout() == 1) && count == 20) ||
                    ((glo.getTestLayout() == 2 || glo.getTestLayout() == 3) && count == 30)){
                if(glo.getTestLayout() == 3){
                    glo.setPage(0);
                    Intent intent = new Intent();
                    intent.setClass(LockPatternNew.this, Display.class);
                    startActivity(intent);
                }
                else {
                    // go next layout
                    glo.setTestLayout(glo.getTestLayout()+1);
                    glo.initCounter();
                    Intent intent = new Intent();
                    intent.setClass(LockPatternNew.this, LockPatternNew.class);
                    startActivity(intent);
                }
            }
            else{
                // test again
                Intent intent = new Intent();
                intent.setClass(LockPatternNew.this, LockPatternNew.class);
                startActivity(intent);
            }
        }
    }
    private void show_message(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pattern_new);
        initPage();
    }

    @Override
    public void onPatternStatusChange(int status, int x, int y) {
        if (status == NONE) {
            mLockPatternHint.setTextColor(0xFFC2C2C2);
            mLockPatternHint.setText("请绘制图案");
        }
        else if (status == LESS) {
            mLockPatternHint.setTextColor(0xFFC2C2C2);
            mLockPatternHint.setText("至少5个点");
        }
        else if (status == CORRECT){
            //leavePage();
        }
        else if (status == GO_SECOND){
            //Log.i("Hey", "GO_SECOND!");
            mLockPatternHint.setTextColor(0xFFC2C2C2);
            mLockPatternHint.setText("請選擇想激活的app!");
            VibratorUtil.Vibrate(LockPatternNew.this, 80);
        }
        else if (status == CHOOSE){
            if(mode == 3) {
                VibratorUtil.Vibrate(LockPatternNew.this, 10);
            }
        }
        else if (status == ERROR){// ERROR
            mLockPatternHint.setTextColor(0xFFEE1289);
            mLockPatternHint.setText("图形密码错误");
            VibratorUtil.Vibrate(LockPatternNew.this, 300);
        }
        else if (status == LEAVE_RIGHT){
            value = 2;
            hover_x = x;
            hover_y = y;
            Log.i("RIGHT", "RIGHT");
            leavePage();
        }
        else if (status == LEAVE_WRONG){
            value = 1;
            hover_x = x;
            hover_y = y;
            Log.i("WRONG", "WRONG");
            leavePage();
        }
        else {
            mLockPatternHint.setText("");
        }
    }
}
