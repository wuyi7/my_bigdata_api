package com.wuyi.hive;

/**
 * @Author: WuYi at 2022-11-24 10:17
 * @Description:
 * @Version:1.0
 */



import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HiveClient {


    // 驱动，固定的
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    // 默认就是10000端口，ip地址使用hive服务器的
    private static String url = "jdbc:hive2://hadoop102:10000/sparktuning";
    // hive连接的用户名和密码，默认就算是下面这两个
    private static String user = "root";
    private static String password = "doremi";
    public static String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


    // 公共使用的变量
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public HiveClient() throws IOException {
    }

    // 加载驱动、创建连接
    private static void init() throws Exception {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url, user, password);
        stmt = conn.createStatement();
    }

    // 释放资源
    public static void destory() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    // 主函数
    public static void main(String[] args) throws Exception {
        //建立连接
        init();
        //表名称
        String Tablename = "Test";
        //文件路径

        String localFilePath = "E:\\Data.txt";
        String hdfsFilePath = "/Test" + today.substring(0, 7) + "/upload_date=" + today + "/";
        File localfilepath = new File(localFilePath);

        /**
         * 1.查看hdfs中所有目录
         * 2.创建文件夹
         * 3.将本地文件上传到hdfs中
         * 4.将hdfs的文件上传到hive表中
         */
//        HdfsTest.getDiretoryFromHdfs("/");
//        HdfsTest.mkdir(hdfsFilePath);
//        HdfsTest.uploadFile(localFilePath,hdfsFilePath);
//        HdfsTest.getDiretoryFromHdfs(hdfsFilePath);

        createTable(Tablename);
        showTables();
//    	loadData(hdfsFilePath+localfilepath.getName(),Tablename);
        descTable("course_pay");

//    	selectData();
//    	countData();
//    	dropTable();
        destory();


    }

    // 创建表
    private static void createTable(String Tablename) throws Exception {
        stmt.execute("drop table if exists " + Tablename);
        String sql = "create table " + Tablename + " (id int, name string)";
        stmt.execute(sql);
    }

    // 查询所有表
    private static void showTables() throws Exception {
        String sql = "show tables";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 查看表结构
    public static void descTable(String Tablename) throws Exception {
        String sql = "desc " + Tablename + "";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2));
        }
    }

    // 加载数据
    public static void loadData(String filePath, String Tablename) throws Exception {

        String sql = "load data inpath '" + filePath + "' into table " + Tablename;
        stmt.execute(sql);
    }

    // 查询数据
    public static void selectData(String Tablename) throws Exception {
        String sql = "select * from " + Tablename + "";
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("foo") + "\t\t" + rs.getString("bar"));
        }
    }


    // 删除数据库表
    public static void dropTable(String Tablename) throws Exception {
        String sql = "drop table if exists " + Tablename;
        stmt.execute(sql);
    }

}
