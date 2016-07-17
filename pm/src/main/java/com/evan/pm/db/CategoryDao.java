package com.evan.pm.db;

import android.content.Context;

import com.evan.pm.entity.Category;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by evan on 2016/7/17.
 */
public class CategoryDao implements IDao<Category> {

    private Context context;
    private DBHelper helper;
    private Dao<Category, Integer> categoryDao;

    public CategoryDao(Context context) {
        this.context = context;
        helper = DBHelper.getInstance(context);
        categoryDao = helper.getDao(Category.class);
    }

    @Override
    public void add(Category category) {
        try {
            categoryDao.create(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Category category) {
        try {
            categoryDao.delete(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            categoryDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category category) {
        try {
            categoryDao.update(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> queryAll() {
        try {
            return categoryDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
