package com.company;

import java.sql.*;
import java.util.*;

public class JDBCTest01 {
    public static void main(String[] args){
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        //            String url = "jdbc:mysql://127.0.0.1:3306/Company";
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //1 register driver
            Class.forName(driver);
            //2 get connection
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("database object = "+conn+"connection success");
            //3 get data object
            stmt = conn.createStatement();
            //4 execute sql
            String sql = "select ENAME,EMPNO,SAL from EMP";
            // int stmt.executeUpdate(insert,delete,update)
            // ResultSet executeQuery(select)
            rs = stmt.executeQuery(sql);
            //5 handle ResultSet
            while(rs.next()){
                String empno = rs.getString("EMPNO");
                String ename = rs.getString("ENAME");
//                String sal = rs.getString("SAL");
                double sal = rs.getDouble("SAL");
                System.out.println(empno+","+ename+","+(sal+100));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //6 release resource
            if(rs!=null){
                try{
                    rs.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(stmt!=null){
                try{
                    stmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
