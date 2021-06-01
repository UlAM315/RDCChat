package com.liangxiaolin.rdcchat.RDCChat1.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    public static String url;
    public static String username;
    public static String password;
    private static String driver;

    /**
     * 文件的读取，只需要读取一次即可拿到这些值，使用静态代码块：在类加载的时候就会执行
     */

    //1.定义成员变量 DataSource
    private static DataSource ds ;

    static{
        try {
            //1.加载配置文件
            Properties pro = new Properties();
            pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            //2.获取DataSource
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 释放资源①
     *
     * 一定要分开每个close（），因为如果一个执行不成功，会影响后续资源的释放
     */
    public static void close(Connection conn, PreparedStatement ps){

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(ps != null){
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    /**
     * 释放资源②
     *
     * 方法重载
     */
    public static void close(Connection conn, PreparedStatement ps,  ResultSet rs){

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(ps != null){
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取连接池方法
     */

    public static DataSource getDataSource(){
        return  ds;
    }
}
