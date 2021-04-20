package com.company;

import java.awt.desktop.SystemEventListener;
import java.sql.*;
import java.util.*;

public class JDBCTest04 {
    public static void main(String args[]){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1 register driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2 connect
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company","root","970216");
            //close auto commit
            conn.setAutoCommit(false);
            //3 get statement
            String sql = "select ENAME from EMP where ENAME like ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,"_A%");
            rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString("ENAME");
                System.out.println(name);
            }

            sql ="update t_bank set money = ? where id = ?";
            ps = conn.prepareStatement(sql);
            //4 execute sql
            ps.setInt(1,10000);
            ps.setDouble(2,1);
            int count = ps.executeUpdate();

//            String s = null;
//            s.toString();

            ps.setInt(1,10000);
            ps.setDouble(2,2);
            count += ps.executeUpdate();

            sql = "select * from t_bank";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                double money = rs.getDouble("money");
                System.out.println("id:"+id+",  money:"+money+"");
            }

            System.out.println(count==2?"success":"failed");
            //manual commit
            conn.commit();

        }catch (Exception e){
            if(conn!=null){
                try {
                    conn.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
