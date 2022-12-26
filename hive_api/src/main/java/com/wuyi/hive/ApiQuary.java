package com.wuyi.hive;

/**
 * @Author: WuYi at 2022-11-23 17:47
 * @Description:
 * @Version:1.0
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ApiQuary {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) {
        try {
            Class.forName(driverName);
            Connection con = null;
            con = DriverManager.getConnection
                    ("jdbc:hive2://hadoop102:10000/sparktuning", "root", "doremi");
            Statement stmt = con.createStatement();
            ResultSet res = null;
            String sql = "show tables";
            System.out.println("Running: " + sql);
            res = stmt.executeQuery(sql);
            System.out.println("ok");
            while (res.next()) {
                System.out.println(res.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

