package com.liangxiaolin.rdcchat.RDCChat1.dao.base;


import com.liangxiaolin.rdcchat.RDCChat1.util.JDBCUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectDimQuery {
    /**
     *(查)select * from T where value LIKE "%"?"%" or ;
     * @param entity
     * @param values 条件的内容
     * @param <T>
     * @return 返回一个对象
     */
    public static <T> T dimQueryObject(T entity, List<String> values){
        //获取运行时类
        Class clazz = entity.getClass();

        //获取简单类名
        String className = clazz.getSimpleName();

        String sql = "";

        //左边
        String left = "select * from " + className;

        //中间
        String mid = " where ";

        //右边
        StringBuilder right = new StringBuilder();
        for (String value : values) {
            right.append(value+" LIKE \"%\"?\"%\"  or ");
        }

        //字符串处理
        //把最后的" or "去掉
        String right0 = right.substring(0, right.length() - 4);

        sql = left + mid + right0 +";";


        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //数据的传输
            int i = 1;
            for (String value : values) {//遍历list集合
                //获取实体对象的方法和私有属性
                Field field = clazz.getDeclaredField(value);
                field.setAccessible(true);
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(entity));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setDate(i, ((Date) field.get(entity)));
                }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(entity));
                } else {
                    ps.setString(i, String.valueOf(field.get(entity)));
                }
                i++;
            }
            rs = ps.executeQuery();
            //DatabaseMetaData 有关整个数据库的信息：表名、表的索引、数据库产品的名称和版本、数据库支持的操作。
            //ResultSet 关于某个表的信息或一个查询的结果。您必须逐行访问数据行，但是您可以任何顺序访问列。
            ResultSetMetaData rsmd = rs.getMetaData();
            //记录一共有多少列
            int ccount = rsmd.getColumnCount();
            list = new ArrayList<T>();
            if (rs.next()) {
                T t = entity;
                for (int j = 0; j < ccount; j++) {
                    //同一行中（一个i代表一列）
                    //每一列的标题
                    String columnLabel = rsmd.getColumnLabel(j + 1);
                    //每一列的值
                    Object columnValue = rs.getObject(j + 1);
                    //使用反射获取每一列的值
                    //获取类的属性，包括private和public
                    Field field = clazz.getDeclaredField(columnLabel);
                    //设置true可对private进行操作
                    field.setAccessible(true);
                    //给t对象的field属性赋值
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return null;
    }

    /**
     *(查)select * from T where value LIKE "%"?"%" or ;
     * @param entity
     * @param values
     * @param <T>
     * @return 返回对象集合
     */
    public static <T> List<T> dimQueryList(T entity, List<String> values){
        //获取运行时类
        Class clazz = entity.getClass();

        //获取简单类名
        String className = clazz.getSimpleName();

        String sql = "";

        //左边
        String left = "select * from " + className;

        //中间
        String mid = " where ";

        //右边
        StringBuilder right = new StringBuilder();
        for (String value : values) {
            right.append(value+" LIKE \"%\"?\"%\" or ");
        }

        //字符串处理
        //把最后的" or "去掉
        String right0 = right.substring(0, right.length() - 4);

        sql = left + mid + right0 +";";


        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //数据的传输
            int i = 1;
            for (String value : values) {//遍历list集合
                //获取实体对象的方法和私有属性
                Field field = clazz.getDeclaredField(value);
                field.setAccessible(true);
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(entity));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setDate(i, ((Date) field.get(entity)));
                }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(entity));
                } else {
                    ps.setString(i, String.valueOf(field.get(entity)));
                }
                i++;
            }
            rs = ps.executeQuery();
            //DatabaseMetaData 有关整个数据库的信息：表名、表的索引、数据库产品的名称和版本、数据库支持的操作。
            //ResultSet 关于某个表的信息或一个查询的结果。您必须逐行访问数据行，但是您可以任何顺序访问列。
            ResultSetMetaData rsmd = rs.getMetaData();
            //记录一共有多少列
            int ccount = rsmd.getColumnCount();
            //list = new ArrayList<T>();
            while (rs.next()) {
                T t = entity;
                for (int j = 0; j < ccount; j++) {
                    //同一行中（一个i代表一列）
                    //每一列的标题
                    String columnLabel = rsmd.getColumnLabel(j + 1);
                    //每一列的值
                    Object columnValue = rs.getObject(j + 1);
                    //使用反射获取每一列的值
                    //获取类的属性，包括private和public
                    Field field = clazz.getDeclaredField(columnLabel);
                    //设置true可对private进行操作
                    field.setAccessible(true);
                    //给t对象的field属性赋值
                    field.set(t, columnValue);
                }
                System.out.println("dimqury(t):"+t);
                list.add(t);
            }
            System.out.println("dimreflect()list:"+list);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return list;
    }

    /**
     *(查)select * from T where value LIKE "%"?"%" or ;
     * @param entity
     * @param values
     * @param <T>
     * @return 返回对象集合
     *//*
    public static <T> List<T> dimQueryListAndOr(T entity, List<String> values){
        //获取运行时类
        Class clazz = entity.getClass();

        //获取简单类名
        String className = clazz.getSimpleName();

        String sql = "";

        //左边
        String left = "select * from " + className;

        //中间
        String mid = " where ";

        //右边
        StringBuilder right = new StringBuilder();
        for (String value : values) {
            right.append(value+" LIKE \"%\"?\"%\" or ");
        }

        //字符串处理
        //把最后的" or "去掉
        String right0 = right.substring(0, right.length() - 4);

        sql = left + mid + right0 +";";


        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //数据的传输
            int i = 1;
            for (String value : values) {//遍历list集合
                //获取实体对象的方法和私有属性
                Field field = clazz.getDeclaredField(value);
                field.setAccessible(true);
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(entity));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setDate(i, ((Date) field.get(entity)));
                }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(entity));
                } else {
                    ps.setString(i, String.valueOf(field.get(entity)));
                }
                i++;
            }
            rs = ps.executeQuery();
            //DatabaseMetaData 有关整个数据库的信息：表名、表的索引、数据库产品的名称和版本、数据库支持的操作。
            //ResultSet 关于某个表的信息或一个查询的结果。您必须逐行访问数据行，但是您可以任何顺序访问列。
            ResultSetMetaData rsmd = rs.getMetaData();
            //记录一共有多少列
            int ccount = rsmd.getColumnCount();
            //list = new ArrayList<T>();
            while (rs.next()) {
                T t = entity;
                for (int j = 0; j < ccount; j++) {
                    //同一行中（一个i代表一列）
                    //每一列的标题
                    String columnLabel = rsmd.getColumnLabel(j + 1);
                    //每一列的值
                    Object columnValue = rs.getObject(j + 1);
                    //使用反射获取每一列的值
                    //获取类的属性，包括private和public
                    Field field = clazz.getDeclaredField(columnLabel);
                    //设置true可对private进行操作
                    field.setAccessible(true);
                    //给t对象的field属性赋值
                    field.set(t, columnValue);
                }
                System.out.println("dimqury(t):"+t);
                list.add(t);
            }
            System.out.println("dimreflect()list:"+list);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return list;
    }*/
}
