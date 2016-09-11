package com.evan.pm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by evan on 2016/4/21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        int layoutResID = getLayoutResID();
        if (layoutResID > 0) {
            setContentView(getLayoutResID());
            ButterKnife.bind(this);
        }

        init();
    }


    protected abstract int getLayoutResID();

    protected abstract void init();

//    private void setTranslucentStatus() {
//        //版本大于4.4,设置状态栏透明化
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////            WindowManager.LayoutParams params = getWindow().getAttributes();
////            params.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | params.flags);
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // Translucent navigation bar
////            window.setFlags(
////                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
////                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//    }
}
