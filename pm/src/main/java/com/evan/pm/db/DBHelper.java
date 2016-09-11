package com.evan.pm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.evan.pm.XApp;
import com.evan.pm.entity.Account;
import com.evan.pm.entity.Category;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库帮助类
 * Created by evan on 2016/4/21.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String TABLE_NAME = "account.db";
    private static final int VERSION = 1;

    private Map<String, Dao> daoMap = new HashMap<>();

    private Context context;

    private static DBHelper instance;

    String[] defautlCategorys = new String[]{"技术", "科技", "新闻", "休闲"};

    private DBHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
        this.context = context;
    }

    //单例
    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
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
            addDefaultCategory(database);
        } catch (SQLException e) {
            Toast.makeText(context, "创建数据库失败", Toast.LENGTH_SHORT).show();
        }
    }

    //添加默认的类别
    private void addDefaultCategory(SQLiteDatabase database) {

        String sql = "";
        for (String category : defautlCategorys) {
            sql = "insert into category (category) values ('" + category + "')";
            database.execSQL(sql);
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

    public Dao getDao(Class clazz) {

        Dao dao = null;
        String className = clazz.getName();

        if (daoMap.containsKey(className)) {
            dao = daoMap.get(className);
        }

        if (dao == null) {
            try {
                dao = super.getDao(clazz);
                daoMap.put(className, dao);
            } catch (SQLException e) {
                dao = null;
            }
        }

        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();

        for (String key : daoMap.keySet()) {
            Dao dao = daoMap.get(key);
            dao = null;
        }
    }
}
