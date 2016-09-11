package com.evan.pm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.evan.pm.activity.AccountAddActivity;
import com.evan.pm.activity.BaseActivity;
import com.evan.pm.activity.DetailsActivity;
import com.evan.pm.activity.category.CategoryListActivity;
import com.evan.pm.adapter.DividerItemDecoration;
import com.evan.pm.adapter.InfoAdapter;
import com.evan.pm.adapter.OnRecyclerViewItemClickListener;
import com.evan.pm.common.Constant;
import com.evan.pm.db.AccountDao;
import com.evan.pm.entity.Account;
import com.evan.xtool.ui.EmptyView;
import com.evan.xtool.ui.XDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;

    @BindView(R.id.info_lv)
    RecyclerView info_ryv;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    List<Account> accounts;
    InfoAdapter adapter;

    AccountDao accountDao;
    XDialog dialog;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        setSupportActionBar(toolbar);

        accountDao = new AccountDao(context);
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);

        emptyView.bindView(info_ryv);

        accounts = new ArrayList<>();
        adapter = new InfoAdapter(context, accounts);
        info_ryv.setLayoutManager(new LinearLayoutManager(this));
        info_ryv.setAdapter(adapter);
        info_ryv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Account account = accounts.get(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.navigation_category) {
                    Intent intent = new Intent(context, CategoryListActivity.class);
                    startActivity(intent);
                    return true;
                }else if(item.getItemId() == R.id.navigation_setting){

                }
                return false;
            }
        });

        dialog = new XDialog(context);
    }

    private void updateEmpty() {
        if (accounts.isEmpty()) {
            emptyView.empty();
        } else {
            emptyView.success();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(context, AccountAddActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Account> list = accountDao.queryAll();
        accounts.clear();
        accounts.addAll(list);
        adapter.notifyDataSetChanged();
        updateEmpty();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
