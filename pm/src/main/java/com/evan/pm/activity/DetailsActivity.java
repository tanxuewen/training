package com.evan.pm.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.evan.pm.R;
import com.evan.pm.entity.Account;

public class DetailsActivity extends BaseActivity {

    Account account;

    EditText desc_et;
    EditText username_et;
    EditText password_et;
    EditText url_et;
    EditText remark_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        account = (Account) getIntent().getSerializableExtra("account");

        desc_et = (EditText) findViewById(R.id.details_desc);
        username_et = (EditText) findViewById(R.id.details_username);
        password_et = (EditText) findViewById(R.id.details_password);
        url_et = (EditText) findViewById(R.id.details_url);
        remark_et = (EditText) findViewById(R.id.details_remark);

        desc_et.setText(account.getDescription());
        username_et.setText(account.getUsername());
        password_et.setText(account.getPassword());
        url_et.setText(account.getUrl());
        remark_et.setText(account.getRemark());

    }

}
