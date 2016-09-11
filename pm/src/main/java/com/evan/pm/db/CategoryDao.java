package com.evan.pm.db;

import android.content.Context;

import com.evan.pm.entity.Category;
import com.j256.ormlite.dao.Dao;

/**
 * Created by evan on 2016/7/17.
 */
public class CategoryDao extends BaseDao<Category, Integer> {

    private Dao<Category, Integer> categoryDao;

    public CategoryDao(Context context) {
        super(context);
        categoryDao = mDBHelper.getDao(Category.class);
    }

    @Override
    public Dao<Category, Integer> getDao() {
        if (categoryDao == null) {
            categoryDao = mDBHelper.getDao(Category.class);
        }
        return categoryDao;
    }
}
