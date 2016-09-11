package com.evan.pm.activity.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.evan.pm.R;
import com.evan.pm.XApp;
import com.evan.pm.activity.BaseActivity;
import com.evan.pm.adapter.CategoryAdapter;
import com.evan.pm.adapter.DividerItemDecoration;
import com.evan.pm.db.CategoryDao;
import com.evan.pm.entity.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by evan on 2016/9/11.
 */
public class CategoryListActivity extends BaseActivity {

    @BindView(R.id.category_rcv)
    RecyclerView category_rcv;

    List<Category> categoryList;
    CategoryAdapter categoryAdapter;

    CategoryDao categoryDao;
    @BindView(R.id.category_add_btn)
    Button categoryAddBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_category_list;
    }

    @Override
    protected void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryDao = new CategoryDao(context);
        categoryList = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(context, categoryList);
        category_rcv.setLayoutManager(new LinearLayoutManager(this));
        category_rcv.setAdapter(categoryAdapter);
        category_rcv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
    }

    private void updateCategoryList() {
        XApp.getExecutor().execute(new Runnable() {
            @Override
            public void run() {
                categoryList.clear();
                categoryList.addAll(categoryDao.queryAll());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        categoryAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @OnClick(R.id.category_add_btn)
    public void onClick() {
        Intent intent = new Intent(context, CategoryAddActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCategoryList();
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
