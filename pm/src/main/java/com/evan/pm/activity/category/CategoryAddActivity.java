package com.evan.pm.activity.category;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;

import com.evan.pm.R;
import com.evan.pm.activity.BaseActivity;
import com.evan.pm.db.CategoryDao;
import com.evan.pm.entity.Category;
import com.evan.xtool.utils.XToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by evan on 2016/9/11.
 * 分类添加界面
 *
 */
public class CategoryAddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.catetory_desc)
    EditText catetory_desc_et;

    CategoryDao categoryDao;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_category_add;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryDao = new CategoryDao(context);
    }

    @OnClick(R.id.category_save_btn)
    public void onClick() {

        String categoryDesc = catetory_desc_et.getText().toString();
        if(TextUtils.isEmpty(categoryDesc)){
            XToast.showShort(context, "类别不能为空！！");
            return;
        }

        Category category = new Category();
        category.setCategory(categoryDesc);
        categoryDao.save(category);
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
}
