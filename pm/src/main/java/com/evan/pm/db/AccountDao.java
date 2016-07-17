package com.evan.pm.db;

import android.content.Context;

import com.evan.pm.entity.Account;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * 账户数据库操作类
 * Created by evan on 2016/7/17.
 */
public class AccountDao implements IDao<Account> {

    private Context context;
    private DBHelper helper;
    private Dao<Account, Integer> accountDao;

    public AccountDao(Context context) {
        this.context = context;
        helper = DBHelper.getInstance(context);
        accountDao = helper.getDao(Account.class);
    }

    public void add(Account account) {
        try {
            accountDao.create(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Account account) {
        try {
            accountDao.delete(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            accountDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Account account) {
        try {
            accountDao.update(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> queryAll() {
        try {
            return accountDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
