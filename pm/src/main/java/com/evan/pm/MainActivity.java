package com.evan.pm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.evan.pm.activity.AddAccountActivity;
import com.evan.pm.activity.BaseActivity;
import com.evan.pm.activity.DetailsActivity;
import com.evan.pm.adapter.DividerItemDecoration;
import com.evan.pm.adapter.InfoAdapter;
import com.evan.pm.db.AccountDao;
import com.evan.pm.db.DBHelper;
import com.evan.pm.entity.Account;
import com.evan.xtool.utils.CommonUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    DrawerLayout drawerLayout;

    RecyclerView info_ryv;
    List<Account> accounts;
    InfoAdapter adapter;

    AccountDao accountDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        resetToolBarHeight(toolbar);
        setSupportActionBar(toolbar);

        accountDao = new AccountDao(context);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);

        accounts = new ArrayList<>();
        info_ryv = (RecyclerView) findViewById(R.id.info_lv);
        adapter = new InfoAdapter(context, accounts);
        info_ryv.setAdapter(adapter);
        info_ryv.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL_LIST));

//        info_ryv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Account account = (Account) parent.getAdapter().getItem(position);
//                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
//                intent.putExtra("account", account);
//                startActivity(intent);
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = CommonUtils.createLoadingDialog(context, null);
                    dialog.show();
//                    Intent intent = new Intent(context, AddAccountActivity.class);
//                    startActivity(intent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                }
            });
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
            Intent intent = new Intent(context, AddAccountActivity.class);
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
    }
}
