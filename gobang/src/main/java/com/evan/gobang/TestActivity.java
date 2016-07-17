package com.evan.gobang;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.TextView;

/**
 * Created by evan on 2016/6/2.
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);
        Handler handler = new Handler();

        handler.sendEmptyMessage(0);
        TextView tv = new TextView(this);
        tv.setBackgroundDrawable(null);

//        getSystemService()
    }
}
