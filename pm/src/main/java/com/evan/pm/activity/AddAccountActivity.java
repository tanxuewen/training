package com.evan.pm.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.evan.pm.R;
import com.evan.pm.db.DBHelper;
import com.evan.pm.entity.Account;

import java.sql.SQLException;

/**
 * Created by evan on 2016/4/22.
 */
public class AddAccountActivity extends BaseActivity implements View.OnClickListener{

    EditText desc_et;
    Spinner category_spinner;
    EditText username_et;
    EditText password_et;
    EditText url_et;
    EditText remark_et;
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        desc_et = (EditText) findViewById(R.id.details_desc);
        category_spinner = (Spinner) findViewById(R.id.details_category);
        username_et = (EditText) findViewById(R.id.details_username);
        password_et = (EditText) findViewById(R.id.details_password);
        url_et = (EditText) findViewById(R.id.details_url);
        remark_et = (EditText) findViewById(R.id.details_remark);
        save_btn = (Button) findViewById(R.id.save);

        save_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == save_btn){
            save();
        }
    }

    private void save() {
        if(username_et.length() == 0 || password_et.length() == 0
                || url_et.length() == 0 || desc_et.length() == 0){
            Toast.makeText(AddAccountActivity.this, "数据不完整", Toast.LENGTH_SHORT).show();
            return;
        }

        Account account = new Account();
        account.setDescription(desc_et.getText().toString());
        account.setUrl(url_et.getText().toString());
        account.setUsername(username_et.getText().toString());
        account.setPassword(password_et.getText().toString());
        account.setCategory(category_spinner.getSelectedItem().toString());
        account.setRemark(remark_et.getText().toString());

        try {
            DBHelper.getInstance(context).getAccountDao().createOrUpdate(account);
        } catch (SQLException e) {
            Toast.makeText(AddAccountActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
