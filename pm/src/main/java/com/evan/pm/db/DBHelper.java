package com.evan.pm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.evan.pm.entity.Account;
import com.evan.pm.entity.Category;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * 数据库帮助类
 * Created by evan on 2016/4/21.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "account.db";
    private static final int VERSION = 1;

    private Dao<Account, Integer> accountDao;

    private Dao<Category, Integer> categoryDao;

    private Context context;

    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
        this.context = context;
    }

    //单例
    public static synchronized DBHelper getInstance(Context context){
        if(instance == null){
            synchronized (DBHelper.class){
                if(instance == null){
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Account.class);
            TableUtils.createTable(connectionSource, Category.class);
        } catch (SQLException e) {
            Toast.makeText(context, "创建数据库失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Account.class, true);
            TableUtils.dropTable(connectionSource, Category.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Toast.makeText(context, "升级数据库失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取AccountDao
     * @return
     * @throws SQLException
     */
    public Dao<Account, Integer> getAccountDao() throws SQLException {
        if(accountDao == null){
            accountDao = getDao(Account.class);
        }

        return accountDao;
    }

    /**
     * 获取CategoryDao
     * @return
     * @throws SQLException
     */
    public Dao<Category, Integer> getCategoryDao() throws SQLException {
        if(categoryDao == null){
            categoryDao = getDao(Category.class);
        }

        return categoryDao;
    }


    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        accountDao = null;
    }
}
