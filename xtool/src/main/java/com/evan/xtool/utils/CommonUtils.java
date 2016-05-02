package com.evan.xtool.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.evan.xtool.R;

/**
 * Created by evan on 2016/5/2.
 */
public class CommonUtils {

    public static Dialog createLoadingDialog(Context context, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.xdialog, null);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        ImageView xdialog_iv = (ImageView) v.findViewById(R.id.xdialog_iv);
        xdialog_iv.startAnimation(animation);

        TextView xdialog_tv = (TextView) v.findViewById(R.id.xdialog_tv);
        if(!TextUtils.isEmpty(message))
            xdialog_tv.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.XDialog);
        builder.setCancelable(true);
        builder.setView(v);

        return builder.create();
    }
}
