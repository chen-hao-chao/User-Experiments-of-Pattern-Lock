package com.lance.lockpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WaitActivity extends AppCompatActivity {
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            GlobalVariable glo = (GlobalVariable) getApplicationContext();
            int page = glo.getPage();
            Intent intent = new Intent();
            if(page == 0){
                intent.setClass(WaitActivity.this, MainActivity.class);
            }
            else if(page == 1){
                intent.setClass(WaitActivity.this, LockPattern.class);
            }
            else if(page == 2){
                intent.setClass(WaitActivity.this, LockPatternNew.class);
            }
            else{
                intent.setClass(WaitActivity.this, MainActivity.class);
            }
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        timerHandler.postDelayed(timerRunnable, 3000);
    }
}
