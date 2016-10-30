package com.evan.pm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.evan.pm.R;

import butterknife.ButterKnife;

/**
 * Created by evan on 2016/4/21.
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        beforeView(savedInstanceState);
        int layoutResID = getLayoutResID();
        if (layoutResID > 0) {
            setContentView(getLayoutResID());
            ButterKnife.bind(this);
        }

        init();
    }

    protected void beforeView(Bundle savedInstanceState) {

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


    @Override
    public void onClick(View v) {

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }
}
