package com.evan.pm;


import android.test.AndroidTestCase;
import android.util.Log;

import com.evan.pm.db.DBHelper;
import com.evan.pm.entity.Account;
import com.evan.pm.entity.Category;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by evan on 2016/4/21.
 */
public class DbTest extends AndroidTestCase {

    public static final String TAG = DbTest.class.getSimpleName();

    public void testAddAccount(){
        DBHelper dbHelper = DBHelper.getInstance(getContext());

        Account account = new Account();
        account.setUrl("http://www.csdn.com");
        account.setDescription("CSDN");
        account.setUsername("tanxuwen");
        account.setPassword("ysjipyygy");
        account.setRemark("技术学习网站");

        Category category = new Category();
        category.setCategory("tech");
        category.setDescription("技术网站");

        try {
            dbHelper.getAccountDao().create(account);
            dbHelper.getCategoryDao().create(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testDeleteAccount(){
        DBHelper dbHelper = DBHelper.getInstance(getContext());
        try {
            dbHelper.getAccountDao().deleteById(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testUpdateAccount(){
        DBHelper dbHelper = DBHelper.getInstance(getContext());

        Account account = new Account();
        account.setUrl("http://www.baidu.com");
        account.setDescription("百度一下");
        account.setUsername("tanxuwen");
        account.setPassword("ysjipyygy");
        account.setId(3);

        try {
            dbHelper.getAccountDao().update(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void testList(){
        DBHelper dbHelper = DBHelper.getInstance(getContext());
        try {
            List<Account> accounts = dbHelper.getAccountDao().queryForAll();
            for (Account account: accounts) {
                Log.i(TAG, account.toString());
                //fjfa
            }

            List<Category> categories = dbHelper.getCategoryDao().queryForAll();
            for (Category category: categories) {
                Log.i(TAG, category.toString());
                //fjfa
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
