package com.liangxiaolin.rdcchat.RDCChat1.dao.base;


import com.liangxiaolin.rdcchat.RDCChat1.util.DateUtils;
import com.liangxiaolin.rdcchat.RDCChat1.util.JDBCUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectCRUD {
    /**
     * 在表中插入一行(增)
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> boolean add(T entity){
        //获取运行时类
        Class clazz = entity.getClass();

        //获取实体对象的方法和私有属性
        Field[] fields = clazz.getDeclaredFields();
        Field.setAccessible(fields, true);

        //获取简单类名
        String className = clazz.getSimpleName();

        //设置符号集
        String sql = "";

        String left = "insert into " + className + " values (";
        String right = ")";

        //反射拼接语句
        StringBuilder mid = new StringBuilder();
        for (Field f : fields) {
            mid.append("?,");
        }

        //字符串处理
        //把最后的逗号去掉
        String mid0 = mid.substring(0, mid.length() - 1);
        sql = left + mid0 + right;

        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------

        //获取数据库连接
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //数据的传输
            int i = 1;
            for (Field field : fields) {
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(entity));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setString(i, (DateUtils.utilDateToString((java.util.Date) field.get(entity))));
                } else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(entity));
                } else {
                    ps.setString(i, String.valueOf(field.get(entity)));
                }
                i++;
            }
            //执行语句
            int line = ps.executeUpdate();
            System.out.println(line);
            if(line==1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps);
        }return false;

        /**
         * execute：可以执行任何crud语句，返回结果为true或false 代表是否有结果集。
         * executeQuery：只能执行select语句 ，返回结果为查询的结果集。
         * executeUpdate：能执行cud语句，返回结果为受影响的行数。
         */
    }

    /*@Test
    public void addTest(){
        Category c = new Category(0,"test1");
        System.out.println(add(c));
    }*/

    /**
     * 删除信息(条件where只有一个)(删)
     * @param entity
     * @param value 删除的条件
     * @param <T>
     * @return
     */
    public static <T> boolean delete(T entity,String value){
        //获取运行时类
        Class clazz = entity.getClass();

        //获取简单类名
        String className = clazz.getSimpleName();

        //设置符号集
        String sql = "";

        String left = "DELETE FROM " + className + " WHERE ";
        String right = value+"=?;";
        sql = left + right;

        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------

        //获取数据库连接
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //数据的传输
            //获取实体对象的方法和私有属性
            Field field = clazz.getDeclaredField(value);
            field.setAccessible(true);
            if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                ps.setInt(1, (Integer) field.get(entity));
            } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                ps.setString(1, DateUtils.utilDateToString((java.util.Date) field.get(entity)));
            }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                ps.setBigDecimal(1, (BigDecimal) field.get(entity));
            } else {
                ps.setString(1, String.valueOf(field.get(entity)));
            }
            //执行语句
            int line = ps.executeUpdate();
            if(line==1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps);
        }return false;
    }

    /**
     * 删除信息(条件where中有and)(删)
     * 方法重载
     * @param entity
     * @param values 删除的条件
     * @param <T>
     * @return
     */
    public static <T> boolean delete(T entity, List<String> values){
        //获取运行时类
        Class clazz = entity.getClass();

        //获取简单类名
        String className = clazz.getSimpleName();

        //设置符号集
        String sql = "";

        String left = "DELETE FROM " + className + " WHERE ";
        StringBuilder right = new StringBuilder();
        for (String value : values) {
            right.append(value+"=? and ");
        }

        //字符串处理
        //把最后的" and "去掉
        String right0 = right.substring(0, right.length() - 5);
        sql = left + right0 +";";

        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------

        //获取数据库连接
        Connection conn = null;
        PreparedStatement ps = null;
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
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(entity)));
                }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(entity));
                } else {
                    ps.setString(i, String.valueOf(field.get(entity)));
                }
                i++;
            }
            //执行语句
            int line = ps.executeUpdate();
            if(line==1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps);
        }return false;
    }

    /*@Test
    public void deleteTest(){
        Category c = new Category(8,"test1");
        System.out.println(delete(c,"category_id"));
    }*/

    /**
     *
     * @param before 修改前的数据（where）(改)
     * @param values 要改变的值
     * @param condition where条件中只有一个
     * @param after 修改后的数据（set）
     * @param <T>
     * @return
     */
    public static <T> boolean change(T before,List<String> values,String condition,T after){
        //获取运行时类
        Class clazz1 = before.getClass();
        Class clazz2 = after.getClass();

        //获取简单类名
        String className = clazz1.getSimpleName();

        //设置符号集
        String sql = "";

        String left = "UPDATE " + className + " SET ";

        StringBuilder mid1 = new StringBuilder();
        for (String value : values) {
            mid1.append(value+"=?,");
        }

        //把最后的逗号去掉
        String mid10 = mid1.substring(0, mid1.length() - 1);

        String mid2 = " where ";

        String mid3 = condition+"=?;";

        //字符串处理
        sql = left + mid10 + mid2 + mid3;

        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------

        //获取数据库连接
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //数据的传输
            int i = 1;

            //set中的?
            for (String value : values) {//遍历list集合
                //获取实体对象的方法和私有属性
                Field field = clazz2.getDeclaredField(value);
                field.setAccessible(true);
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(after));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(after)));
                }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(after));
                } else {
                    ps.setString(i, String.valueOf(field.get(after)));
                }
                i++;
            }

            //where中的?
            //获取实体对象的方法和私有属性
            Field field = clazz1.getDeclaredField(condition);
            field.setAccessible(true);
            if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                ps.setInt(i, (Integer) field.get(before));
            } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(before)));
            }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                ps.setBigDecimal(i, (BigDecimal) field.get(before));
            } else {
                ps.setString(i, String.valueOf(field.get(before)));
            }
            //执行语句
            int line = ps.executeUpdate();
            if(line==1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps);
        }return false;
    }

    /**
     *
     * @param before 修改前的数据（where）(改)
     * @param values 要改变的值
     * @param conditions  where条件中有多个
     * @param after 修改后的数据（set）
     * @param <T>
     * @return
     */
    public static <T> boolean change(T before,List<String> values,List<String> conditions,T after){
        //获取运行时类
        Class clazz1 = before.getClass();
        Class clazz2 = after.getClass();

        //获取简单类名
        String className = clazz1.getSimpleName();

        //设置符号集
        String sql = "";

        String left = "UPDATE " + className + " SET ";

        StringBuilder mid1 = new StringBuilder();
        for (String value : values) {
            mid1.append(value+"=?,");
        }

        //把最后的逗号去掉
        String mid10 = mid1.substring(0, mid1.length() - 1);

        String mid2 = " where ";

        StringBuilder mid3 = new StringBuilder();
        for (String condition : conditions) {
            mid3.append(condition+"=? and ");
        }

        //把最后的" and "去掉
        String mid30 = mid3.substring(0, mid3.length() - 5);

        //字符串处理
        sql = left + mid10 + mid2 + mid30 +";";

        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------

        //获取数据库连接
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //数据的传输
            int i = 1;

            //set中的?
            for (String value : values) {//遍历list集合
                //获取实体对象的方法和私有属性
                Field field = clazz2.getDeclaredField(value);
                field.setAccessible(true);
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(after));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(after)));
                } else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(after));
                } else {
                    ps.setString(i, String.valueOf(field.get(after)));
                }
                i++;
            }

            //where中的?
            for (String condition : conditions) {//遍历list集合
                //获取实体对象的方法和私有属性
                Field field = clazz1.getDeclaredField(condition);
                field.setAccessible(true);
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(before));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(before)));
                } else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(before));
                } else {
                    ps.setString(i, String.valueOf(field.get(before)));
                }
                i++;
            }
            //执行语句
            int line = ps.executeUpdate();
            if(line==1){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps);
        }return false;
    }

    /*@Test
    public void changeTest(){
        Note n1 = new Note(3,2,1,"研发哈哈哈",0,"","公开","研发冲冲冲");
        Note n2 = new Note(3,2,1,"研发哈哈哈",0,"","公开","研发冲冲冲");
        List values = new ArrayList();
        values.add("title");
        values.add("note_content");
        String condition = "note_id";
        System.out.println(change(n1,values,condition,n2));
    }*/

    /**
     *(查)select * from T where value=?;
     * @param entity
     * @param value 单个条件的内容
     * @param <T>
     * @return 返回一个对象
     */
    public static <T> T queryObject(T entity, String value){

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
        String right = value+"=?";

        sql = left + mid + right +";";


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
                //获取实体对象的方法和私有属性
                Field field = clazz.getDeclaredField(value);
                field.setAccessible(true);
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(entity));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(entity)));
                }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(entity));
                } else {
                    ps.setString(i, String.valueOf(field.get(entity)));
                }
            rs = ps.executeQuery();
            //DatabaseMetaData 有关整个数据库的信息：表名、表的索引、数据库产品的名称和版本、数据库支持的操作。
            //ResultSet 关于某个表的信息或一个查询的结果。您必须逐行访问数据行，但是您可以任何顺序访问列。
            ResultSetMetaData rsmd = rs.getMetaData();
            //记录一共有多少列
            int ccount = rsmd.getColumnCount();
            if (rs.next()) {
                T t = entity;
                for (int j = 0; j < ccount; j++) {
                    //同一行中（一个i代表一列）
                    //每一列的标题
                    String columnLabel = rsmd.getColumnLabel(j + 1);
                    //每一列的值
                    Object columnValue = rs.getObject(j + 1);
                    if("issueTime".equals(columnLabel)){
                        columnValue = DateUtils.stringToUtilDate((String) columnValue);
                    }
                    //使用反射获取每一列的值
                    //获取类的属性，包括private和public
                    Field field1 = clazz.getDeclaredField(columnLabel);
                    //设置true可对private进行操作
                    field1.setAccessible(true);
                    //给t对象的field属性赋值
                    field1.set(t, columnValue);
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
     *(查)select * from T where value=?;
     * @param entity
     * @param values 条件的内容
     * @param <T>
     * @return 返回一个对象
     */
    public static <T> T queryObjectAnd(T entity, List<String> values){
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
            right.append(value+"=? and ");
        }

        //字符串处理
        //把最后的" and "去掉
        String right0 = right.substring(0, right.length() - 5);

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
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(entity)));
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
                    if("issueTime".equals(columnLabel)){
                        columnValue = DateUtils.stringToUtilDate((String) columnValue);
                    }
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
     *(查)select * from T where value=?;
     * @param entity
     * @param value
     * @param <T>
     * @return 返回对象集合
     */
    public static <T> List<T> queryList(T entity, String value){
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
        String right= value+"=?";

        sql = left + mid + right +";";


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
                //获取实体对象的方法和私有属性
                Field field = clazz.getDeclaredField(value);
                field.setAccessible(true);
                if ("integer".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setInt(i, (Integer) field.get(entity));
                } else if ("date".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(entity)));
                }else if ("bigdecimal".equalsIgnoreCase(field.getType().getSimpleName())) {
                    ps.setBigDecimal(i, (BigDecimal) field.get(entity));
                } else {
                    ps.setString(i, String.valueOf(field.get(entity)));
                }
            rs = ps.executeQuery();
            //DatabaseMetaData 有关整个数据库的信息：表名、表的索引、数据库产品的名称和版本、数据库支持的操作。
            //ResultSet 关于某个表的信息或一个查询的结果。您必须逐行访问数据行，但是您可以任何顺序访问列。
            ResultSetMetaData rsmd = rs.getMetaData();
            //记录一共有多少列
            int ccount = rsmd.getColumnCount();
            list = new ArrayList<T>();
            while (rs.next()) {
                T t = entity;
                for (int j = 0; j < ccount; j++) {
                    //同一行中（一个i代表一列）
                    //每一列的标题
                    String columnLabel = rsmd.getColumnLabel(j + 1);
                    //每一列的值
                    Object columnValue = rs.getObject(j + 1);
                    if("issueTime".equals(columnLabel)){
                        columnValue = DateUtils.stringToUtilDate((String) columnValue);
                    }
                    //使用反射获取每一列的值
                    //获取类的属性，包括private和public
                    Field field1 = clazz.getDeclaredField(columnLabel);
                    //设置true可对private进行操作
                    field1.setAccessible(true);
                    //给t对象的field属性赋值
                    field1.set(t, columnValue);
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return list;
    }

    /**
     *(查)select * from T where value=? and;
     * @param entity
     * @param values
     * @param <T>
     * @return 返回对象集合
     */
    public static <T> List<T> queryListAnd(T entity, List<String> values){
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
            right.append(value+"=? and ");
        }

        //字符串处理
        //把最后的" and "去掉
        String right0 = right.substring(0, right.length() - 5);

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
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(entity)));
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
            while (rs.next()) {
                T t = entity;
                for (int j = 0; j < ccount; j++) {
                    //同一行中（一个i代表一列）
                    //每一列的标题
                    String columnLabel = rsmd.getColumnLabel(j + 1);
                    //每一列的值
                    Object columnValue = rs.getObject(j + 1);
                    if("issueTime".equals(columnLabel)){
                        columnValue = DateUtils.stringToUtilDate((String) columnValue);
                    }
                    //使用反射获取每一列的值
                    //获取类的属性，包括private和public
                    Field field = clazz.getDeclaredField(columnLabel);
                    //设置true可对private进行操作
                    field.setAccessible(true);
                    //给t对象的field属性赋值
                    field.set(t, columnValue);
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return list;
    }

    /**
     *(查)select * from T where value=? or;
     * @param entity
     * @param values 条件的内容
     * @param <T>
     * @return 返回一个对象
     */
    public static <T> T queryObjectOr(T entity, List<String> values){
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
            right.append(value+"=? or ");
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
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(entity)));
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
                    if("issueTime".equals(columnLabel)){
                        columnValue = DateUtils.stringToUtilDate((String) columnValue);
                    }
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
     *(查)select * from T where value=? or;
     * @param entity
     * @param values
     * @param <T>
     * @return 返回对象集合
     */
    public static <T> List<T> queryListOr(T entity, List<String> values){
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
            right.append(value+"=? or ");
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
                    ps.setString(i, DateUtils.utilDateToString((java.util.Date) field.get(entity)));
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
            while (rs.next()) {
                T t = entity;
                for (int j = 0; j < ccount; j++) {
                    //同一行中（一个i代表一列）
                    //每一列的标题
                    String columnLabel = rsmd.getColumnLabel(j + 1);
                    //每一列的值
                    Object columnValue = rs.getObject(j + 1);
                    if("issueTime".equals(columnLabel)){
                        columnValue = DateUtils.stringToUtilDate((String) columnValue);
                    }
                    //使用反射获取每一列的值
                    //获取类的属性，包括private和public
                    Field field = clazz.getDeclaredField(columnLabel);
                    //设置true可对private进行操作
                    field.setAccessible(true);
                    //给t对象的field属性赋值
                    field.set(t, columnValue);
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return list;
    }

    /**
     * (查)查询整张表 select * from T;
     * @param entity
     * @param <T>
     * @return 返回对象集合
     */
    public static <T> List<T> query(T entity){
        //获取运行时类
        Class clazz = entity.getClass();

        //获取简单类名
        String className = clazz.getSimpleName();

        //设置符号集
        String sql = "select * from " + className + ";";

        System.out.println(sql);
        //----------------------------以上完成sql语句的拼接------------------------
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //DatabaseMetaData 有关整个数据库的信息：表名、表的索引、数据库产品的名称和版本、数据库支持的操作。
            //ResultSet 关于某个表的信息或一个查询的结果。您必须逐行访问数据行，但是您可以任何顺序访问列。
            ResultSetMetaData rsmd = rs.getMetaData();
            //记录一共有多少列
            int ccount = rsmd.getColumnCount();
            list = new ArrayList<T>();
            while (rs.next()) {
                T t = entity;
                for (int i = 0; i < ccount; i++) {
                    //同一行中（一个i代表一列）
                    //每一列的标题
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //每一列的值
                    Object columnValue = rs.getObject(i + 1);
                    if("issueTime".equals(columnLabel)){
                        columnValue = DateUtils.stringToUtilDate((String) columnValue);
                    }
                    //使用反射获取每一列的值
                    //获取类的属性，包括private和public
                    Field field = clazz.getDeclaredField(columnLabel);
                    //设置true可对private进行操作
                    field.setAccessible(true);
                    //给t对象的field属性赋值
                    field.set(t, columnValue);
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
        return list;
    }

    public static String getType(Object o){
        return o.getClass().toString();
    }
}
