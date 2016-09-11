package com.evan.pm.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by evan on 2016/4/22.
 */
@DatabaseTable(tableName = "category")
public class Category {

    public static final String ID = "id";
    public static final String CATEGORY = "category";

    @DatabaseField(generatedId = true, useGetSet = true, columnName = ID)
    private int id;
    @DatabaseField(useGetSet = true, unique = true, columnName = CATEGORY)
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        String str = "CATEGORY --> id:" + id + " category:" + category;
        return str;
    }
}
