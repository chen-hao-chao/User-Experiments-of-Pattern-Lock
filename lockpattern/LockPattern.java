package com.lance.lockpattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class LockPattern extends Activity implements LockPatternView.OnPatternChangeListener {
    // DEFINE:
    private static final int NONE = 0;
    private static final int LESS = 1;
    private static final int ERROR = 2;
    private static final int CORRECT = 3;
    private static final int NOTHING = 6;
    // variables
    //private int Page;
    private TextView mLockPatternHint;
    private LockPatternView mLockPatternView;

    private void initPage(){
        /** GLOBAL VARIABLES **/
        GlobalVariable glo = (GlobalVariable) getApplicationContext();
        glo.setStartTime((int)System.currentTimeMillis());
        /** INIT **/
        mLockPatternHint = (TextView) findViewById(R.id.lock_pattern_hint);
        mLockPatternView = (LockPatternView) findViewById(R.id.lock_pattern_view);
        mLockPatternView.setOnPatternChangeListener(this);
    }
    private void leavePage(){
        /** GLOBAL VARIABLES **/
        GlobalVariable glo = (GlobalVariable) getApplicationContext();
        /** JUMP **/
        Intent intent = new Intent();
        intent.setClass(LockPattern.this, MainPage.class);
        startActivity(intent);
    }
    private void show_message(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pattern);
        initPage();
    }

    @Override
    public void onPatternStatusChange(int status) {
        if (status == NONE) {
            mLockPatternHint.setTextColor(0xFFC2C2C2);
            mLockPatternHint.setText("请绘制图案");
        }
        else if (status == LESS) {
            mLockPatternHint.setTextColor(0xFFC2C2C2);
            mLockPatternHint.setText("至少5个点");
        }
        else if (status == CORRECT){
            leavePage();
            Log.i("Hey", "LEAVE!");
        }
        else if (status == NOTHING){
            Log.i("Hey", "LEAVE!");
        }
        else {// ERROR
            mLockPatternHint.setTextColor(0xFFEE1289);
            mLockPatternHint.setText("图形密码错误");
            VibratorUtil.Vibrate(LockPattern.this, 300);
        }
    }
}