package com.DAO;

import java.util.List;

public interface DAO<T> {
    public boolean add(T u);
    public boolean add(List<T> u);
    public boolean delete(int col, Object id);
    default public boolean delete(Object id){return delete(1,id);};
    public T Query(int col, Object id);
    default public T Query(Object id){return Query(1,id);};
    public List<T> QueryAll();
    public boolean alter(int num,T u);
}