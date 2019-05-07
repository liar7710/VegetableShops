package com.DAOlmpl;

import com.DAO.DAO;
import com.DAOFactory.DAOFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOlmpl<T> implements DAO<T> {
    private final Field[] F;
    private final Method[] M;
    private final Class<T> C;
    private String table;
    public DAOlmpl(Class s) {
        this.C = s;
        this.F = s.getDeclaredFields();
        this.M=s.getDeclaredMethods();
        for (Field i : F) {
            i.setAccessible(true);
        }
        try {
            table = (String) F[0].get(C.getDeclaredConstructor().newInstance());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(T u) {
        StringBuilder sql = new StringBuilder(String.format("insert into %s values (null", table));
        for (int i=2;i<F.length;i++)
            sql.append(",?");
        sql.append(")");
        boolean flag=false;
        try (Connection conn = DAOFactory.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString())
        ) {
            for (int i = 2; i < F.length; i++) {
                pst.setObject(i-1, F[i].get(u));
           }
            flag=pst.execute();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean add(List<T> u) {
        boolean flag=false;
        StringBuilder sql= new StringBuilder(String.format("insert into %s values (null", table));
        for (int i=2;i<F.length;i++)
            sql.append(",?");
        sql.append(")");
        try (Connection conn = DAOFactory.getConnection();
        ) {
            conn.setAutoCommit(false);
            PreparedStatement pst = conn.prepareStatement(sql.toString());
           for (T p : u) {
                for (int i = 2; i < F.length; i++) {
                    pst.setObject(i-1, F[i].get(p));
                }
                pst.addBatch();
            }
            pst.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            flag=true;
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean delete(int col, Object id) {
        String sql = String.format("delete from %s where %s=?", table,F[col].getName());
        boolean flag=false;
        try (Connection conn = DAOFactory.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);) {
            pst.setObject(col, id);
            flag=pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
    @Override
    public T Query(int col, Object id) {
        String sql = String.format("select * from %s", table);
        T g=null;
        try (Connection c = DAOFactory.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql);) {
            while (rs.next()&&g==null) {
                if (rs.getObject(col).equals(id)) {
                    g = C.getDeclaredConstructor().newInstance();
                    for (int i = 1; i < F.length; i++) {
                        F[i].set(g, rs.getObject(i));
                    }
                }
            }
        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return g;
    }
    @Override
    public List<T> QueryAll() {
        List<T> glist = new ArrayList<>();
        String sql = String.format("select * from %s", table);
        try (Connection c = DAOFactory.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql);) {
            while (rs.next()) {
                T g = C.getDeclaredConstructor().newInstance();
                for (int i = 1; i < F.length; i++) {
                    F[i].set(g, rs.getObject(i));
                }
                glist.add(g);
            }
            return glist;
        } catch (SQLException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean alter(int num,T u) {
        StringBuilder sql = new StringBuilder(String.format("update %s set ",table));
        for (int i = 1+num; i < F.length - 1; i++) {
            sql.append(F[i].getName()).append("=?,");
        }
        sql.append(F[F.length - 1].getName()).append("=? where ");
        for(int i=1;i<num;i++){
            sql.append(F[i].getName()).append("=?").append(" and ");
        }
        sql.append(F[num].getName()).append("=?");
        System.out.println(sql.toString());
        boolean flag=false;
        try (Connection conn = DAOFactory.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql.toString());) {
            for (int i = 1+num; i < F.length; i++) {
                pst.setObject(i-num, F[i].get(u));
            }
            for(int i=1;i<=num;i++){
                pst.setObject(F.length - (num-i+1), F[i].get(u));
            }

            flag=pst.execute();
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
