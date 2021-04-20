package com.company;

import java.sql.*;
import java.util.*;

public class JDBCTest02 {
    public static void main(String arg[]){
        //init a ui
        Map<String,String> userLoginInfo = initUI();
        //validate user name and password
        boolean loginSuccess = login(userLoginInfo);
        //last
        System.out.println(loginSuccess?"success":"failed");
    }

    /**
     * user login
     * @param userLoginInfo user name and password
     * @return boolean
     */
    private static boolean login(Map<String, String> userLoginInfo) {
        //JDBC code
        String loginName = userLoginInfo.get("loginName");
        String loginPwd = userLoginInfo.get("loginPwd");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean flag = false;
        try {
            //1 register driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2 connect
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company","root","970216");
            //3 create statement
            stmt = conn.createStatement();
            //4 sql
//            String sql = "insert into t_user (loginName, loginPwd, realName) values ('huhu','970621','huhu')";
//            stmt.executeLargeUpdate(sql);
            String sql = "select * from t_user where loginName='"+loginName+"' and loginPwd='"+loginPwd+"'";
            rs = stmt.executeQuery(sql);
            //5 handle resultSet
            if(rs.next()){
                flag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //release resource
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if(stmt!=null){
                try {
                    stmt.close();
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
        return flag;
    }

    /**
     * init user ui
     * @return user's name and password
     */
    private static Map<String, String> initUI() {
        Scanner s = new Scanner(System.in);
        System.out.print("username: ");
        String username = s.nextLine();
        System.out.print("password: ");
        String password = s.nextLine();
        Map<String,String> userLoginInfo = new HashMap<String,String>();
        userLoginInfo.put("loginName",username);
        userLoginInfo.put("loginPwd",password);
        return  userLoginInfo;

    }


}
