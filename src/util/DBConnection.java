/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Chad Romney
 */

public class DBConnection {
//    private static final String databaseName = "vBqRgD9HpB";
//    private static final String DB_URL = "jdbc:mysql://remotemysql.com:3306/" + databaseName;
//    private static final String DB_Username = "vBqRgD9HpB";
//    private static final String DB_Password = "YOwP8xonCW";
    
    private static final String databaseName = "sql3325810";
    private static final String DB_URL = "jdbc:mysql://sql3.freemysqlhosting.net:3306/" + databaseName;
    private static final String DB_Username = "sql3325810";
    private static final String DB_Password = "MjDsrqJAzH";


    private static final String driver = "com.mysql.cj.jdbc.Driver";
    static Connection conn;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        Class.forName(driver);
    //    conn = (Connection) DriverManager.getConnection(DB_URL, DB_Username, DB_Password);
        System.out.println("Connection Sucessful");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection Closed");
    }
    
    public static Connection getConnection() {
        return conn;
    }
    
}
