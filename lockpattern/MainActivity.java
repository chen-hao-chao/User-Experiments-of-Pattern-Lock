package com.lance.lockpattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Button start_btn;
    private EditText input_text;

    private void init(){
        input_text = (EditText) findViewById(R.id.input_text);
        start_btn = (Button) findViewById(R.id.start_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        start_btn.setOnClickListener(new ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String x = input_text.getText().toString();
            boolean mode_1 = x.startsWith("test_1_");
            boolean mode_2 = x.startsWith("test_2_");
            boolean mode_3 = x.startsWith("test_3_");

            if (android.text.TextUtils.isEmpty(x)){
                Toast.makeText(getApplicationContext(), "请输入内容!", Toast.LENGTH_LONG).show();
            }
            else{
                if(mode_1){
                    // mode_1 : The first part of the experiments.
                    GlobalVariable glo = (GlobalVariable) getApplicationContext();
                    glo.setName(x);
                    glo.setPage(1);
                    glo.setMode(1);
                    glo.setTestTime(0);
                    glo.setTestLayout(0);
                    glo.setTestTarget(1);
                    glo.initCounter();
                    glo.initFileString();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LockPatternNew.class);
                    startActivity(intent);
                }
                else if(mode_2){
                    // mode_2 : The second part of the experiments.
                    GlobalVariable glo = (GlobalVariable) getApplicationContext();
                    glo.setName(x);
                    glo.setPage(1);
                    glo.setMode(2);
                    glo.setTestTime(0);
                    glo.initCounter();
                    glo.setTestLayout(0); // x
                    glo.initFileString();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LockPatternNew.class);
                    startActivity(intent);
                }
                else if(mode_3){
                    // mode_3 : The third part of the experiments.
                    GlobalVariable glo = (GlobalVariable) getApplicationContext();
                    glo.setName(x);
                    glo.setPage(1);
                    glo.setMode(3);
                    glo.setTestTime(0);// constant : 0, 1, 2 (?)
                    glo.setTestLayout(0);
                    glo.initCounter();
                    glo.initFileString();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, WaitActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "请确认输入号正确!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}