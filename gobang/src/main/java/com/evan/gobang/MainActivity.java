package com.evan.gobang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.evan.gobang.widget.GoBangView;

public class MainActivity extends AppCompatActivity {

    GoBangView panel;
    Button reset_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panel = (GoBangView) findViewById(R.id.wuzi);
        reset_btn = (Button) findViewById(R.id.reset_btn);
        reset_btn.setEnabled(false);

        panel.setListener(new GoBangView.WinListener() {
            @Override
            public void onWinListener(boolean whiteWin) {
                reset_btn.setEnabled(true);
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panel.start();
                reset_btn.setEnabled(false);
            }
        });
    }
}
