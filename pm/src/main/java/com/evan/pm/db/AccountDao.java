package com.evan.pm.db;

import android.content.Context;

import com.evan.pm.entity.Account;
import com.j256.ormlite.dao.Dao;

/**
 * 账户数据库操作类
 * Created by evan on 2016/7/17.
 */
public class AccountDao extends BaseDao<Account, Integer> {

    private Dao<Account, Integer> accountDao;

    public AccountDao(Context context) {
        super(context);
        accountDao = mDBHelper.getDao(Account.class);
    }

    @Override
    public Dao<Account, Integer> getDao() {
        if (accountDao == null) {
            accountDao = mDBHelper.getDao(Account.class);
        }
        return accountDao;
    }
}
