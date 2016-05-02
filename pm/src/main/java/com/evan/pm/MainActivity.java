package com.evan.pm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.evan.pm.activity.AddAccountActivity;
import com.evan.pm.activity.BaseActivity;
import com.evan.pm.activity.DetailsActivity;
import com.evan.pm.adapter.InfoAdapter;
import com.evan.pm.db.DBHelper;
import com.evan.pm.entity.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    RequestQueue mQueue;

    ListView info_lv;
    List<Account> accounts;
    InfoAdapter adapter;

    ListView menu_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQueue = Volley.newRequestQueue(this);

        accounts = new ArrayList<>();
        info_lv = (ListView) findViewById(R.id.info_lv);
        adapter = new InfoAdapter(context, accounts);
        info_lv.setAdapter(adapter);

        menu_lv = (ListView) findViewById(R.id.menu_lv);

        List<String> menus = new ArrayList<>();
        menus.add("技术网站");
        menus.add("新闻网站");
        menus.add("学习网站");
        ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, menus);
        menu_lv.setAdapter(menuAdapter);

        info_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Account account = (Account) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddAccountActivity.class);
                    startActivity(intent);
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
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_add){
            Intent intent = new Intent(context, AddAccountActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            List<Account> list = DBHelper.getInstance(context).getAccountDao().queryForAll();
            accounts.clear();
            accounts.addAll(list);
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
