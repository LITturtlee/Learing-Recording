package com.company;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//    solve sql injection

public class JDBCTest03 {
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
        PreparedStatement ps = null;    //use PreparedStatement
        ResultSet rs = null;
        boolean flag = false;
        try {
            //1 register driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2 connect
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company","root","970216");
            //3 create statement
            String sql = "select * from t_user where loginName = ?  and loginPwd = ?";
            //this code will send sql frame to DBMS,then DBMS will pre compile
            ps = conn.prepareStatement(sql);
            //now send value to ?
            ps.setString(1,loginName);
            ps.setString(2,loginPwd);
            //ps.setInt(index,value);

            //4 sql
//            String sql = "insert into t_user (loginName, loginPwd, realName) values ('huhu','970621','huhu')";
//            stmt.executeLargeUpdate(sql);
//            String sql = "select * from t_user where loginName='"+loginName+"' and loginPwd='"+loginPwd+"'";
            rs = ps.executeQuery();     //this parameter is empty
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
