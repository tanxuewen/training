package com.evan.pm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.evan.pm.MainActivity;
import com.evan.pm.R;
import com.evan.xtool.utils.XToast;

import butterknife.BindView;

/**
 * Created by evan on 2016/10/29.
 * 闪屏密码页
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.login_password_et)
    EditText et_login_password;
    @BindView(R.id.excess_count_hint)
    TextView tv_error_hint;
    @BindView(R.id.login_sure)
    Button btn_sure;

    //记录密码输入错误次数
    int errorCount;
    SharedPreferences prefs;

    @Override
    protected void beforeView(Bundle savedInstanceState) {
        super.beforeView(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.splash;
    }

    @Override
    protected void init() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        btn_sure.setOnClickListener(this);
        et_login_password.requestFocus();
        et_login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btn_sure.setEnabled(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_sure:
                if (check()) {
                    login();
                }
                break;
        }
    }

    /**
     * 验证数据的完整性
     *
     * @return
     */
    private boolean check() {
        btn_sure.setEnabled(false);
        long errorTime = prefs.getLong("errorTime", 0);
        long currTime = System.currentTimeMillis();
        int intervalMin = (int) ((currTime - errorTime) / (60 * 1000));
        if (intervalMin < 5) {
            tv_error_hint.setText(getString(R.string.excess_count_hint, intervalMin));
            tv_error_hint.setVisibility(View.VISIBLE);
            return false;
        }

        String password = et_login_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            XToast.showShort(this, "密码不能为空！");
            return false;
        }

        errorCount++;
        if ("2749".equals(password)) {
            return true;
        } else {
            XToast.showShort(this, "密码不正确！");
            //密码输入错误超过5次，锁定5分钟
            if (errorCount >= 5) {
                tv_error_hint.setText(getString(R.string.excess_count_hint, 5));
                tv_error_hint.setVisibility(View.VISIBLE);
                prefs.edit().putLong("errorTime", System.currentTimeMillis()).apply();
            }
            return false;
        }
    }

    /**
     * 登录
     */
    private void login() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
