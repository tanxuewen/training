package com.evan.pm.activity;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.evan.pm.R;
import com.evan.pm.XApp;
import com.evan.pm.db.AccountDao;
import com.evan.pm.db.CategoryDao;
import com.evan.pm.entity.Account;
import com.evan.pm.entity.Category;
import com.evan.xtool.adapter.CommonAdapter;
import com.evan.xtool.adapter.ViewHolder;
import com.evan.xtool.utils.ThreeDes;
import com.evan.xtool.utils.XToast;

import java.util.List;

import butterknife.BindView;

/**
 * Created by evan on 2016/4/22.
 * 帐号添加界面
 */
public class AccountAddActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.details_desc)
    EditText desc_et;
    @BindView(R.id.details_category)
    Spinner category_spinner;
    @BindView(R.id.details_username)
    EditText username_et;
    @BindView(R.id.details_password)
    EditText password_et;
    @BindView(R.id.details_url)
    EditText url_et;
    @BindView(R.id.details_remark)
    EditText remark_et;
    @BindView(R.id.save)
    Button save_btn;

    Category selectCategory;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_add;
    }

    @Override
    protected void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save_btn.setOnClickListener(this);

        initSpinner();
    }

    private void initSpinner() {
        CategoryDao categoryDao = new CategoryDao(context);
        final List<Category> categorys = categoryDao.queryAll();
        CategoryAdapter adapter = new CategoryAdapter(context, categorys);
        category_spinner.setAdapter(adapter);
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectCategory = categorys.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectCategory = null;
            }
        });
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
            Toast.makeText(AccountAddActivity.this, "数据不完整", Toast.LENGTH_SHORT).show();
            return;
        }

        if(selectCategory == null){
            XToast.showShort(context, "请选择类别！！");
            return;
        }

        Account account = new Account();
        account.setDescription(desc_et.getText().toString());
        account.setUrl(url_et.getText().toString());
        account.setUsername(username_et.getText().toString());
        String srcPsd = password_et.getText().toString();
        account.setPassword(ThreeDes.encodeHex(ThreeDes.encryptMode(XApp.iv, XApp.key, srcPsd)));
        account.setCategory(selectCategory.getId());
        account.setRemark(remark_et.getText().toString());

        AccountDao accountDao = new AccountDao(context);
        accountDao.save(account);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class CategoryAdapter extends CommonAdapter<Category>{

        public CategoryAdapter(Context context, List<Category> datas) {
            super(context, datas, android.R.layout.simple_list_item_1);
        }

        @Override
        protected void convert(ViewHolder holder, Category category) {
            TextView textView = holder.getView(android.R.id.text1);
            textView.setText(category.getCategory());
        }
    }
}
