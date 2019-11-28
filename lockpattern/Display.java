package com.lance.lockpattern;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Display extends AppCompatActivity {
    private Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        TextView tv = findViewById(R.id.ex_result);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        GlobalVariable glo = (GlobalVariable) getApplicationContext();

        Log.i("RESULT", glo.getFileString());
        tv.setText(glo.getFileString());

        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", glo.getFileString());
        cm.setPrimaryClip(mClipData);
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new Display.ButtonListener());
    }
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            GlobalVariable glo = (GlobalVariable) getApplicationContext();
            glo.setPage(0);
            Intent intent = new Intent();
            intent.setClass(Display.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
