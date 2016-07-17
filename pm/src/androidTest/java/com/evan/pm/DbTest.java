package com.evan.pm;


import android.test.AndroidTestCase;
import android.util.Log;

import com.evan.pm.db.AccountDao;
import com.evan.pm.db.CategoryDao;
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

    public void testAddAccount() {

        Account account = new Account();
        account.setUrl("http://www.csdn.com");
        account.setDescription("CSDN");
        account.setUsername("tanxuwen");
        account.setPassword("ysjipyygy");
        account.setRemark("技术学习网站");

        AccountDao accountDao = new AccountDao(getContext());
        accountDao.add(account);

        Category category = new Category();
        category.setCategory("tech");
        category.setDescription("技术网站");

        CategoryDao categoryDao = new CategoryDao(getContext());
        categoryDao.add(category);
    }

    public void testDeleteAccount() {

        AccountDao accountDao = new AccountDao(getContext());
        accountDao.deleteById(1);
    }

    public void testUpdateAccount() {
        Account account = new Account();
        account.setUrl("http://www.baidu.com");
        account.setDescription("百度一下");
        account.setUsername("tanxuwen");
        account.setPassword("ysjipyygy");
        account.setId(3);

        AccountDao accountDao = new AccountDao(getContext());
        accountDao.update(account);
    }

    public void testList() {

        AccountDao accountDao = new AccountDao(getContext());
        List<Account> accounts = accountDao.queryAll();
        for (Account account : accounts) {
            Log.i(TAG, account.toString());
            //fjfa
        }

        CategoryDao categoryDao = new CategoryDao(getContext());
        List<Category> categories = categoryDao.queryAll();
        for (Category category : categories) {
            Log.i(TAG, category.toString());
            //fjfa
        }
    }
}
