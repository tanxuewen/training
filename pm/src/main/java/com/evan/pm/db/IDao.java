package com.evan.pm.db;

import java.util.List;

/**
 * 实体类操作接口
 * Created by evan on 2016/7/17.
 */
public interface IDao<T> {

    public void add(T t);

    public void delete(T t);

    public void deleteById(int id);

    public void update(T t);

    public List<T> queryAll();

}
