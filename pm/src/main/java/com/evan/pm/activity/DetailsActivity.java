package com.evan.pm.activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.evan.pm.R;
import com.evan.pm.XApp;
import com.evan.pm.entity.Account;
import com.evan.xtool.utils.ThreeDes;

import butterknife.BindView;

/**
 * 帐号详情界面
 */
public class DetailsActivity extends BaseActivity {

    Account account;

    @BindView(R.id.details_desc)
    EditText desc_et;
    @BindView(R.id.details_username)
    EditText username_et;
    @BindView(R.id.details_password)
    EditText password_et;
    @BindView(R.id.details_url)
    EditText url_et;
    @BindView(R.id.details_remark)
    EditText remark_et;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_details;
    }

    @Override
    protected void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        account = (Account) getIntent().getSerializableExtra("account");

        String password = new String(ThreeDes.decryptMode(XApp.iv, XApp.key, ThreeDes.decodeHex(account.getPassword())));

        desc_et.setText(account.getDescription());
        username_et.setText(account.getUsername());
        password_et.setText(password);
        url_et.setText(account.getUrl());
        remark_et.setText(account.getRemark());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
