/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author nick
 */
public class JDBCUtils {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    /**
     * Opens a connection only if it is not open yet
     */
    private static Connection getConnection() {
        if (conn == null) {
            //System.out.println("Connecting to database...");
            try {
                Class.forName("com.mysql.jdbc.Driver");
//                conn = DriverManager.getConnection(dev.dbUrl, dev.dbUsername, dev.dbPassword);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
