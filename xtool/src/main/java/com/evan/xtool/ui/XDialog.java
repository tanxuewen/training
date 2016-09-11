package com.evan.xtool.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.evan.xtool.R;


/**
 * 弹出框
 * Created by evan on 2016/7/17.
 */
public class XDialog extends Dialog {

    ImageView xdialog_iv;
    TextView xdialog_tv;
    Animation animation;

    public XDialog(Context context) {
        this(context, R.style.XDialog);
    }

    public XDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        setContentView(R.layout.xdialog);
        xdialog_iv = (ImageView) findViewById(R.id.xdialog_iv);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_animation);

        xdialog_tv = (TextView) findViewById(R.id.xdialog_tv);
    }

    public void setLoadingText(int resId) {
        xdialog_tv.setText(resId);
    }

    public void setLoadingText(String text) {
        xdialog_tv.setText(text);
    }

    @Override
    public void show() {
        xdialog_iv.startAnimation(animation);
        super.show();
    }

    @Override
    public void dismiss() {
        xdialog_iv.clearAnimation();
        super.dismiss();
    }

    @Override
    public void cancel() {
        xdialog_iv.clearAnimation();
        super.cancel();
    }
}
