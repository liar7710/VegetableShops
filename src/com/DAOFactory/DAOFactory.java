package com.DAOFactory;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
    private static BasicDataSource dataSource;
    private static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("src/dbcp.properties"));
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ThreadLocal<Connection> TL=new ThreadLocal<>();
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    public static Connection getThreadConnection() throws SQLException {
        if(TL.get()==null)
            TL.set(getConnection());
        return TL.get();
    }
    public static boolean removeThreadConnection() throws SQLException {
        TL.remove();
        return true;
    }
    //public static DAO<Branch> getBranchDAO() { return new DAOlmpl<Branch>(Branch.class);}
}
