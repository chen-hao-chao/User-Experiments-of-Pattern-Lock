package com.lance.lockpattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainPage extends Activity {
    public int value = 0;// 0->default, 1->wrong, 2->right.

    private void initPage(){
        GlobalVariable glo = (GlobalVariable) getApplicationContext();
        int test_target = glo.getTestTargetFix(glo.getTestLayout(), glo.getCounter(false));
        if(glo.getTestLayout() == 0) {
            ImageButton Btn1 = (ImageButton) findViewById(R.id.imageButton1);
            ImageButton Btn2 = (ImageButton) findViewById(R.id.imageButton2);
            ImageButton Btn3 = (ImageButton) findViewById(R.id.imageButton3);
            ImageButton Btn4 = (ImageButton) findViewById(R.id.imageButton4);
            ImageButton ImBtnArray[] = {Btn1, Btn2, Btn3, Btn4};

            for (int i = 1; i <= 4; i++) {
                int t_idx = (test_target + i - 2) % 4;
                setImageBtn(ImBtnArray[t_idx], i, t_idx == (test_target - 1));
            }
        }
        else{
            ImageButton Btn1 = (ImageButton) findViewById(R.id.imageButtonTwo1);
            ImageButton Btn2 = (ImageButton) findViewById(R.id.imageButtonTwo2);
            ImageButton Btn3 = (ImageButton) findViewById(R.id.imageButtonTwo3);
            ImageButton Btn4 = (ImageButton) findViewById(R.id.imageButtonTwo4);
            ImageButton Btn5 = (ImageButton) findViewById(R.id.imageButtonTwo5);
            ImageButton Btn6 = (ImageButton) findViewById(R.id.imageButtonTwo6);
            ImageButton ImBtnArray[] = {Btn1, Btn2, Btn3, Btn4, Btn5, Btn6};

            for (int i = 1; i <= 6; i++) {
                int t_idx = (test_target + i - 2) % 6;
                setImageBtn(ImBtnArray[t_idx], i, t_idx == (test_target - 1));
            }
        }
    }
    private void leavePage(){
        GlobalVariable glo = (GlobalVariable) getApplicationContext();
        int target = glo.getTestTargetFix(glo.getTestLayout(), glo.getCounter(false));
        int time = (value == 1)? -444 : (int)System.currentTimeMillis() - glo.getStartTime();
        glo.storeTimeLayout(glo.getTestLayout(), target-1, time);

        glo.addCounter(false);
        int count = glo.getCounter(false);
        if ((glo.getTestLayout() == 0 && count == 20) || (glo.getTestLayout() == 1 && count == 30)){
            if(glo.getTestLayout() == 0){
                glo.setTestLayout(1);
                glo.initCounter();
                Intent intent = new Intent();
                intent.setClass(MainPage.this, LockPattern.class);
                startActivity(intent);
            }
            else{
                glo.setPage(2);
                glo.setTestLayout(0);
                glo.setTestTime(0); // $$
                glo.initCounter();
                Intent intent = new Intent();
                intent.setClass(MainPage.this, WaitActivity.class);
                startActivity(intent);
            }
        }
        else{
            Intent intent = new Intent();
            intent.setClass(MainPage.this, LockPattern.class);
            startActivity(intent);
        }
    }
    private void show_message(String string){
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalVariable glo = (GlobalVariable) getApplicationContext();
        int layout = glo.getTestLayout();
        if(layout == 0) setContentView(R.layout.activity_main_page);
        else setContentView(R.layout.activity_main_page_2);

        initPage();
    }

    // TOOL FUNCTIONS...
    private void setImageBtn(ImageButton btn, int index, boolean check){
        String uri = "@drawable/icon"+String.valueOf(index);
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        btn.setImageResource(imageResource);
        if(check) btn.setOnClickListener(new MainPage.RightButtonListener());
        else btn.setOnClickListener(new MainPage.WrongButtonListener());
    }
    private class WrongButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            value = 1;
            leavePage();
            Log.i("Wrong", "Wrong");
        }
    }
    private class RightButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            value = 2;
            leavePage();
            Log.i("Right", "Right");
        }
    }
}
