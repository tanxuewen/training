package com.evan.pm.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by evan on 2016/4/21.
 */
@DatabaseTable(tableName = "account")
public class Account implements Serializable {

    public static final String ID = "id";
    public static final String URL = "url";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String REMARK = "remark";

    @DatabaseField(generatedId = true, useGetSet = true, columnName = ID)
    private int id;
    @DatabaseField(useGetSet = true, columnName = URL)
    private String url;
    @DatabaseField(useGetSet = true, columnName = DESCRIPTION)
    private String description;
    @DatabaseField(useGetSet = true, columnName = CATEGORY)
    private String category;
    @DatabaseField(useGetSet = true, columnName = USERNAME)
    private String username;
    @DatabaseField(useGetSet = true, columnName = PASSWORD)
    private String password;
    @DatabaseField(useGetSet = true, columnName = REMARK)
    private String remark;

    public Account() {
        category = "default";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        String str = "ACCOUNT --> id:"+id+" url:" + url + " description:" + description + " category:"+category
                + " username:" + username + " password:" + password+" remark:"+remark;
        return str;
    }
}
